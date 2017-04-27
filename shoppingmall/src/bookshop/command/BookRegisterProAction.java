package bookshop.command;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bookshop.bean.MngrDataBean;
import bookshop.bean.MngrDBBean;

public class BookRegisterProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		
		String filename = "";
		String realFolder = "";
		String saveFolder = "/bookImage";
		String encType = "UTF-8";
		int maxSize = 10*1024*1024;
		
		MultipartRequest imageUp = null;
		
		ServletContext context = request.getSession().getServletContext();
		realFolder = context.getRealPath(saveFolder);
		
		try {
			imageUp = new MultipartRequest(request, realFolder, maxSize, encType, 
					new DefaultFileRenamePolicy());
			Enumeration<?> files = imageUp.getFileNames();
			
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = imageUp.getFilesystemName(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MngrDataBean book = new MngrDataBean();
		String book_kind = imageUp.getParameter("book_kind");
		String book_title = imageUp.getParameter("book_title");
		String book_price = imageUp.getParameter("book_price");
		String book_count = imageUp.getParameter("book_count");
		String author = imageUp.getParameter("author");
		String publishing_com = imageUp.getParameter("publishing_com");
		String book_content = imageUp.getParameter("book_content");
		String discount_rate = imageUp.getParameter("discount_rate");
		
		String year = imageUp.getParameter("publishing_year");
		String month = 
				(imageUp.getParameter("publishing_month").length() == 1) ?
						"0" + imageUp.getParameter("publishing_month") :
							imageUp.getParameter("publishing_month");
		String day = 
				(imageUp.getParameter("publishing_day").length() == 1) ?
						"0" + imageUp.getParameter("publishing_day") :
							imageUp.getParameter("publishing_day");
						
		book.setBook_kind(book_kind);
		book.setBook_title(book_title);
		book.setBook_price(Integer.parseInt(book_price));
		book.setBook_count(Short.parseShort(book_count));
		book.setAuthor(author);
		book.setPublishing_com(publishing_com);
		book.setPublishing_date(year + "-" + month + "-" + day);
		book.setBook_image(filename);
		book.setBook_content(book_content);
		book.setDiscount_rate(Byte.parseByte(discount_rate));
		book.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		bookProcess.insertBook(book);
		
		request.setAttribute("book_kind", book_kind);
		return "/mngr/productProcess/bookRegisterPro.jsp";
	}

}
