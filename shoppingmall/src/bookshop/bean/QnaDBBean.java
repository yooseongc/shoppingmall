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

public class QnaDBBean {
	
	private static QnaDBBean instance = new QnaDBBean();
	
	public static QnaDBBean getInstance() {
		return instance;
	}
	
	private QnaDBBean() { }
	
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/shoppingmall");
		return ds.getConnection();
	}
	
	/**
	 * QnA 테이블에 글을 추가하는 메소드. 사용자가 작성하는 글 입력. 
	 * @param article
	 * @return 레코드 추가 성공하면 1, 실패하면 0이 반환.
	 */
	@SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		String sql = "";
		int group_id = 1;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select max(qna_id) from qna");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
			if (x > 0) {
				group_id = rs.getInt(1) + 1;
			}
			
			sql = "insert into qna (qna_id, book_id, book_title, qna_writer, qna_content, ";
			sql += "group_id, qora, reply, reg_date) values (qna_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article.getBook_id());
			pstmt.setString(2, article.getBook_title());
			pstmt.setString(3, article.getQna_writer());
			pstmt.setString(4, article.getQna_content());
			pstmt.setInt(5, group_id);
			pstmt.setInt(6, article.getQora());
			pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
			pstmt.executeUpdate();
			
			x = 1;
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
	 * QnA 테이블에 글을 추가하는 메소드. 관리자가 작성하는 답변 입력. 
	 * 관리자 답변이 달리면 해당 질문글에 대하여 reply가 0에서 1로 수정된다. 
	 * @param article
	 * @return 레코드 추가 성공하면 1, 실패하면 0이 반환.
	 */
	@SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article, int qna_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		String sql = "";
		try {
			conn = getConnection();
			
			sql = "insert into qna (qna_id, book_id, book_title, qna_writer, qna_content, ";
			sql += "group_id, qora, reply, reg_date) values (qna_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article.getBook_id());
			pstmt.setString(2, article.getBook_title());
			pstmt.setString(3, article.getQna_writer());
			pstmt.setString(4, article.getQna_content());
			pstmt.setInt(5, article.getGroup_id());
			pstmt.setInt(6, article.getQora());
			pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
			pstmt.executeUpdate();
			
			sql = "update qna set reply = ? where qna_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, qna_id);
			pstmt.executeUpdate();
			
			x = 1;
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
	 * QnA 테이블에 등록된 전체 글 수를 얻어내는 메소드. 
	 * @return QnA 테이블의 총 레코드 수.
	 */
	public int getArticleCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from qna");
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
	 * 특정 책에 대해 작성한 QnA 글의 수를 얻어내는 메소드.
	 * @param book_id
	 * @return 특정 책에 대한 QnA 테이블 레코드 수.
	 */
	public int getArticleCount(int book_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from qna where book_id = " + book_id);
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
	 * 지정한 수에 해당하는 QnA 글의 수를 얻어내는 메소드. 는 개뿔
	 * getArticleCount 메소드를 사용한 결과값을 넣어서 결국 전체 글 객체 배열을 
	 * 반환하는 메소드.
	 * @param count
	 * @return QnA 테이블 레코드 정보가 들어있는 QnaDataBeen 객체들이 들어있는
	 * List 객체 반환.
	 */
	public List<QnaDataBean> getArticles(int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaDataBean> articleList = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from qna order by group_id desc, qora asc");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				articleList = new ArrayList<QnaDataBean>(count);
				do {
					QnaDataBean article = new QnaDataBean();
					article.setQna_id(rs.getInt("qna_id"));
					article.setBook_id(rs.getInt("book_id"));
					article.setBook_title(rs.getString("book_title"));
					article.setQna_writer(rs.getString("qna_writer"));
					article.setQna_content(rs.getString("qna_content"));
					article.setGroup_id(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					
					articleList.add(article);
				} while (rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return articleList;
	}
	
	/**
	 * 지정한 수에 해당하는 특정 책에 대한 QnA 글의 수를 얻어내는 메소드. 는 개뿔
	 * getArticleCount 메소드를 사용한 결과값을 넣어서 결국 특정 책에 대한 qna 전체 글 객체 배열을 
	 * 반환하는 메소드.
	 * @param count
	 * @param book_id
	 * @return QnA 테이블 레코드 정보중 특정 책 정보가 들어있는 QnaDataBeen 객체들이 들어있는
	 * List 객체 반환.
	 */
	public List<QnaDataBean> getArticles(int count, int book_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaDataBean> articleList = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from qna where book_id = " 
					+ book_id + " order by group_id desc, qora asc");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				articleList = new ArrayList<QnaDataBean>(count);
				do {
					QnaDataBean article = new QnaDataBean();
					article.setQna_id(rs.getInt("qna_id"));
					article.setBook_id(rs.getInt("book_id"));
					article.setBook_title(rs.getString("book_title"));
					article.setQna_writer(rs.getString("qna_writer"));
					article.setQna_content(rs.getString("qna_content"));
					article.setGroup_id(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					
					articleList.add(article);
				} while (rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return articleList;
	}
	
	/**
	 * qna 게시판 글을 수정하기 위해 해당 글 정보를 가져오는 메소드.
	 * @param qna_id
	 * @return 수정하기 위한 기존 글의 QnaDataBean 객체 정보.
	 */
	public QnaDataBean updateGetArticle(int qna_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaDataBean article = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from qna where qna_id = ?");
			pstmt.setInt(1, qna_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				article = new QnaDataBean();
				article.setQna_id(rs.getInt("qna_id"));
				article.setBook_id(rs.getInt("book_id"));
				article.setBook_title(rs.getString("book_title"));
				article.setQna_writer(rs.getString("qna_writer"));
				article.setQna_content(rs.getString("qna_content"));
				article.setGroup_id(rs.getInt("group_id"));
				article.setQora(rs.getByte("qora"));
				article.setReply(rs.getByte("reply"));
				article.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return article;
	}
	
	/**
	 * QnA 글의 내용을 수정하기 위한 메소드.
	 * @param article
	 * @return 수정이 정상적으로 처리되면 1, 그렇지 않으면 -1이 반환된다.
	 */
	public int updateArticle(QnaDataBean article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int x = -1;
		try {
			conn = getConnection();
			
			String sql = "update qna set qna_content = ? where qna_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getQna_content());
			pstmt.setInt(2, article.getQna_id());
			pstmt.executeUpdate();
			x = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
	/**
	 * QnA 글을 삭제하기 위한 메소드.
	 * @param qna_id
	 * @return 삭제가 정상적으로 처리되면 1, 그렇지 않으면 -1이 반환된다.
	 */
	public int deleteArticle(int qna_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int x = -1;
		try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement("delete from qna where qna_id = ?");
			pstmt.setInt(1, qna_id);
			pstmt.executeUpdate();
			x = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { }
		}
		return x;
	}
	
}
