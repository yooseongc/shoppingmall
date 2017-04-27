package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDataBean;

public class BuyFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String buyer = request.getParameter("buyer");
		
		List<CartDataBean> cartList = null;
		List<String> accountLists = null;
		return null;
	}

}
