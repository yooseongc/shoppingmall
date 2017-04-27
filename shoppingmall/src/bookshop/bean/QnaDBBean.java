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
	 * QnA ���̺� ���� �߰��ϴ� �޼ҵ�. ����ڰ� �ۼ��ϴ� �� �Է�. 
	 * @param article
	 * @return ���ڵ� �߰� �����ϸ� 1, �����ϸ� 0�� ��ȯ.
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
	 * QnA ���̺� ���� �߰��ϴ� �޼ҵ�. �����ڰ� �ۼ��ϴ� �亯 �Է�. 
	 * ������ �亯�� �޸��� �ش� �����ۿ� ���Ͽ� reply�� 0���� 1�� �����ȴ�. 
	 * @param article
	 * @return ���ڵ� �߰� �����ϸ� 1, �����ϸ� 0�� ��ȯ.
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
	 * QnA ���̺� ��ϵ� ��ü �� ���� ���� �޼ҵ�. 
	 * @return QnA ���̺��� �� ���ڵ� ��.
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
	 * Ư�� å�� ���� �ۼ��� QnA ���� ���� ���� �޼ҵ�.
	 * @param book_id
	 * @return Ư�� å�� ���� QnA ���̺� ���ڵ� ��.
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
	 * ������ ���� �ش��ϴ� QnA ���� ���� ���� �޼ҵ�. �� ����
	 * getArticleCount �޼ҵ带 ����� ������� �־ �ᱹ ��ü �� ��ü �迭�� 
	 * ��ȯ�ϴ� �޼ҵ�.
	 * @param count
	 * @return QnA ���̺� ���ڵ� ������ ����ִ� QnaDataBeen ��ü���� ����ִ�
	 * List ��ü ��ȯ.
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
	 * ������ ���� �ش��ϴ� Ư�� å�� ���� QnA ���� ���� ���� �޼ҵ�. �� ����
	 * getArticleCount �޼ҵ带 ����� ������� �־ �ᱹ Ư�� å�� ���� qna ��ü �� ��ü �迭�� 
	 * ��ȯ�ϴ� �޼ҵ�.
	 * @param count
	 * @param book_id
	 * @return QnA ���̺� ���ڵ� ������ Ư�� å ������ ����ִ� QnaDataBeen ��ü���� ����ִ�
	 * List ��ü ��ȯ.
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
	 * qna �Խ��� ���� �����ϱ� ���� �ش� �� ������ �������� �޼ҵ�.
	 * @param qna_id
	 * @return �����ϱ� ���� ���� ���� QnaDataBean ��ü ����.
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
	 * QnA ���� ������ �����ϱ� ���� �޼ҵ�.
	 * @param article
	 * @return ������ ���������� ó���Ǹ� 1, �׷��� ������ -1�� ��ȯ�ȴ�.
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
	 * QnA ���� �����ϱ� ���� �޼ҵ�.
	 * @param qna_id
	 * @return ������ ���������� ó���Ǹ� 1, �׷��� ������ -1�� ��ȯ�ȴ�.
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
