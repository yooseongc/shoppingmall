package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;

public class BookDeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int book_id = Integer.parseInt(request.getParameter("book_id"));
		String book_kind = request.getParameter("book_kind");
		
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		bookProcess.deleteBook(book_id);
		request.setAttribute("book_kind", book_kind);
		return "/mngr/productProcess/bookDeletePro.jsp";
	}

}
