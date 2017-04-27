package bookshop.bean;

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
		return null;
	}

}
