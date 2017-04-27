package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;

public class ManagerLoginProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		MngrDBBean dbPro = MngrDBBean.getInstance();
		int check = dbPro.userCheck(id, passwd);
		
		request.setAttribute("check", new Integer(check));
		request.setAttribute("id", id);
		
		return "/mngr/logon/mLoginPro.jsp";
	}

}
