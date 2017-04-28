package bookshop.bean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.command.CommandAction;

public class BuyProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		//구입 처리에 필요한 정보를 파라미터에서 얻어냄
		String account = request.getParameter("account");
		String deliveryName = request.getParameter("deliveryName");
		String deliveryTel = request.getParameter("deliveryTel");
		String deliveryAddress = request.getParameter("deliveryAddress");
		String buyer = request.getParameter("buyer");
		int count = 0;
		
		//구매 처리를 위해 장바구니의 목록을 얻어냄
		CartDBBean cartProcess = CartDBBean.getInstance();
		count = cartProcess.getListCount(buyer);
		List<CartDataBean> cartLists = cartProcess.getCart(buyer, count);
		
		//장바구니의 목록, 구매자, 결제계좌, 배송지 정보를 buy 테이블에 추가
		BuyDBBean buyProcess = BuyDBBean.getInstance();
		buyProcess.insertBuy(cartLists, buyer, account, deliveryName, deliveryTel, deliveryAddress);
		
		request.setAttribute("orderStus", "주문완료");
		request.setAttribute("type", new Integer(1));
		return "/buy/buyPro.jsp";
	}

}
