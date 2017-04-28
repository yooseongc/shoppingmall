package mngr.enc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import work.crypt.BCrypt;
import work.crypt.SHA256;

public class PassCrypt {
    private static PassCrypt instance = new PassCrypt();
    
	public static PassCrypt getInstance() {
		return instance;
	}
	    
	private PassCrypt() {}
		
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/shoppingmall");
		return ds.getConnection();
	}
	
    public void cryptProcess(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        
        SHA256 sha = SHA256.getInsatnce();
        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(
                	"select managerId, managerPasswd from manager");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
            	String id = rs.getString("managerId");
            	String orgPass = rs.getString("managerPasswd");
            	
            	String shaPass = sha.getSha256(orgPass.getBytes());

            	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
            	
            	pstmt = conn.prepareStatement(
                    "update manager set managerPasswd=? where managerId=?");
                pstmt.setString(1, bcPass);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
}