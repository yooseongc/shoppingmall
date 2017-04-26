package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertCartAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		//장바구니에 추가할 정보를 파라미터에서 받아냄
		byte buy_count = Byte.parseByte(request.getParameter("buy_count"));
		int book_id = Integer.parseInt(request.getParameter("book_id"));
		String book_title = request.getParameter("bookt_title");
		String book_image = request.getParameter("book_image");
		int buy_price = (int)Float.parseFloat(request.getParameter("buy_price"));
		String buyer = request.getParameter("buyer");
		
		//장바구니에 추가하기 위한 정보 구성
		return null;
	}

}
