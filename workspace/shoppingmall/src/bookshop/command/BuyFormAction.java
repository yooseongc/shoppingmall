package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.BuyDBBean;
import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;
import bookshop.bean.LogonDBBean;
import bookshop.bean.LogonDataBean;

public class BuyFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");

		String buyer = request.getParameter("buyer");

		List<CartDataBean> cartLists = null;
		List<String> accountLists = null;
		LogonDataBean member = null;
		int count = 0;

		// 해당 buyer의 장바구니 목록의 수를 얻어냄
		CartDBBean bookProcess = CartDBBean.getInstance();
		count = bookProcess.getListCount(buyer);

		if (count > 0) { // 장바구니 목록이 있으면 수행
			cartLists = bookProcess.getCart(buyer, count);
			request.setAttribute("cartLists", cartLists);
		}

		// 구매에 필요한 buyer의 정보를 얻어냄
		LogonDBBean memberProcess = LogonDBBean.getInstance();
		member = memberProcess.getMember(buyer);

		// 구매에 필요한 결제 계좌를 얻어냄
		BuyDBBean buyProcess = BuyDBBean.getInstance();
		accountLists = buyProcess.getAccount();

		request.setAttribute("member", member);
		request.setAttribute("accountLists", accountLists);
		request.setAttribute("type", new Integer(1));
		return "/buy/buyForm.jsp";
	}

}
