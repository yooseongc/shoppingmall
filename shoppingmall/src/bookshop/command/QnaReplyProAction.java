package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDataBean;

public class QnaReplyProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		//상품Qna 답변글 관련내용
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		int book_id = Integer.parseInt(request.getParameter("book_id"));
		String qna_writer = request.getParameter("qna_writer");
		String qna_title = request.getParameter("book_title");
		String qna_content = "[답변]:"+request.getParameter("qna_content");
		Byte qora = Byte.parseByte(request.getParameter("qora"));
		byte reply = 1;//답변여부- 답변함
		
		//상품Qna 답변글 저장을 위한 정보 설정
		QnaDataBean qna = new QnaDataBean();
		
		return null;
	}

}
