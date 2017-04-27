package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import work.crypt.BCrypt;
import work.crypt.SHA256;

/**
 * 관리자 인증, 상품 관리, 구매 관리 부분에서 사용하는 DB 처리빈
 * @author 유성
 */
public class MngrDBBean {
	
	// MngrDBBean 전역 객체 생성 --> Singleton Pattern
	private static MngrDBBean instance = new MngrDBBean();
	
	public static MngrDBBean getInstance() {
		return instance;
	}
	
	private MngrDBBean() { }
	
	/**
	 * 커넥션풀에서 커낵션 객체를 얻어내는 메소드.
	 * @return java.sql.Connection
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/shoppingmall");
		return ds.getConnection();
	}
	
	/**
	 * 입력한 관리자 아이디와 비밀번호를 검사하는 메소드.
	 * @param id
	 * @param passwd
	 * @return 
	 * (int) 
	 * [-1] : 아이디 없음,
	 * [0] : 비밀번호 틀림,
	 * [1] : 인증 성공
	 */
	public int userCheck(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		SHA256 sha = SHA256.getInsatnce();
		try {
			conn = getConnection();
			
			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());
			
			pstmt = conn.prepareStatement("select managerPasswd from manager where managerId = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String dbPasswd = rs.getString("managerPasswd");
				if (BCrypt.checkpw(shaPass, dbPasswd)) {
					x = 1;
				} else {
					x = 0;
				}
			} else {
				x = -1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	/**
	 * 책 등록 메소드
	 * @param book
	 * @throws Exception
	 */
	public void insertBook(MngrDataBean book) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into book(book_id, book_kind, book_title, book_price, ";
			sql += "book_count, author, publishing_com, publishing_date, book_image, ";
			sql += "book_content, discount_rate, reg_date) values (book_seq.nextval, ";
			sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setShort(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setByte(10, book.getDiscount_rate());
			pstmt.setTimestamp(11, book.getReg_date());
			
			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
	}
	
	/**
	 * 이미 등록된 책인지 검증하는 메소드.
	 * @param kind
	 * @param bookName
	 * @param author
	 * @return (int) [1]: 해당 책이 이미 등록되어 있음, [-1]: 해당 책이 등록되어있지 않음.
	 * @throws Exception
	 */
	public int registedBookconfirm(String kind, String bookName, String author) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		try {
			conn = getConnection();
			
			String sql = "select book_name from book ";
			sql += "where book_kind = ? and book_name = ? and author = ?";
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, bookName);
			pstmt.setString(3, author);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = 1;
			} else {
				x = -1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	
	/**
	 * book 테이블에 저장된 총 책의 수를 반환하는 메소드.
	 * @return (int) 총 책의 수
	 * @throws Exception
	 */
	public int getBookCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from book");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	/**
	 * 해당 분류의 책의 수를 얻어내는 메소드.
	 * @param book_kind
	 * @return (int) 해당 분류의 책의 수
	 * @throws Exception
	 */
	public int getBookCount(String book_kind) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		int kind = Integer.parseInt(book_kind);
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from book where book_kind =" + kind);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	/**
	 * 책의 일련번호로 책의 제목을 얻어내는 메소드
	 * @param book_id
	 * @return (String) 해당 책의 제목
	 */
	public String getBookTitle(int book_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String x = "";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select book_title from book where book_id =" + book_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getString(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	/**
	 * 분류별 또는 전체 등록된 책의 DTO 객체 리스트를 얻어내는 메소드
	 * @param book_kind
	 * @return List<MngrDataBean>
	 * @throws Exception
	 */
	public List<MngrDataBean> getBooks(String book_kind) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MngrDataBean> bookList = null;
		try {
			conn = getConnection();
			
			String sql1 = "select * from book";
			String sql2 = "select * from book where book_kind = ? order by reg_date desc";
			
			if (book_kind.equals("all") || book_kind.equals("")) {
				pstmt = conn.prepareStatement(sql1);
			} else {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, book_kind);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bookList = new ArrayList<MngrDataBean>();
				do {
					MngrDataBean book = new MngrDataBean();
					
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));
					
					bookList.add(book);
				} while (rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return bookList;
	}
	
	/**
	 * 분류별 신간책 목록을 얻어내는 메소드. count 개수만큼의 최신 등록 책을 표시한다.
	 * @param book_kind
	 * @param count
	 * @return MngrDataBean[]
	 * @throws Exception
	 */
	public MngrDataBean[] getBooks(String book_kind, int count) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MngrDataBean bookList[] = null;
		int i = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select * from (select book.* from book where book_kind = ? ";
			sql += "order by reg_date desc) where rownum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book_kind);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookList = new MngrDataBean[count];
				do {
					MngrDataBean book = new MngrDataBean();
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));
					
					bookList[i] = book;
					i++;
				} while(rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return bookList;
	}
	
	/**
	 * bookId에 해당하는 책의 정보를 얻어내는 메소드.
	 * 등록된 책을 수정하기 위해 수정 폼으로 읽어들이기 위한 메소드.
	 * @param bookId
	 * @return MngrDataBean
	 * @throws Exception
	 */
	public MngrDataBean getBook(int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MngrDataBean book = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from book where book_id = ?");
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				book = new MngrDataBean();
				book.setBook_kind(rs.getString("book_kind"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_count(rs.getShort("book_count"));
				book.setAuthor(rs.getString("author"));
				book.setPublishing_com(rs.getString("publishing_com"));
				book.setPublishing_date(rs.getString("publishing_date"));
				book.setBook_image(rs.getString("book_image"));
				book.setDiscount_rate(rs.getByte("discount_rate"));
				book.setBook_content(rs.getString("book_content"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return book;
	}
	
	/**
	 * bookId에 해당하는 등록된 책의 정보를 수정 시 사용하는 메소드.
	 * @param book
	 * @param bookId
	 * @throws Exception
	 */
	public void updateBook(MngrDataBean book, int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn = getConnection();
			
			sql = "update book set book_kind = ?, book_title = ?, book_price = ?, ";
			sql += "book_count = ?, author = ?, publishing_com = ?, publishing_date = ?, ";
			sql += "book_image = ?, book_content = ?, discount_rate = ? ";
			sql += "where book_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setShort(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setByte(10, book.getDiscount_rate());
			pstmt.setInt(11, bookId);
			
			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
	}
	
	/**
	 * bookId에 해당하는 책의 정보를 삭제시 사용하는 메소드.
	 * @param bookId
	 * @throws Exception
	 */
	public void deleteBook(int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from book where book_id = ?");
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
	}
}
