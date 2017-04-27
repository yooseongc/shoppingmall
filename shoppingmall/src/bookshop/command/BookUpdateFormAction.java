package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDataBean;
import bookshop.bean.MngrDBBean;

public class BookUpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int book_id = Integer.parseInt(request.getParameter("book_id"));
		String book_kind = request.getParameter("book_kind");
		
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		MngrDataBean book = bookProcess.getBook(book_id);
		
		request.setAttribute("book_id", book_id);
		request.setAttribute("book_kind", book_kind);
		request.setAttribute("book", book);
		request.setAttribute("type", new Integer(0));
		
		return "/mngr/productProcess/bookUpdateForm.jsp";
	}

}
