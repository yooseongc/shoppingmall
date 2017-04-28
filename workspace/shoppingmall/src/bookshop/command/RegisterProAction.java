package bookshop.command;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.LogonDBBean;
import bookshop.bean.LogonDataBean;

public class RegisterProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		// 회원가입 정보
		LogonDataBean member = new LogonDataBean();
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		member.setName(request.getParameter("name"));
		member.setReg_date(new Timestamp(System.currentTimeMillis()));
		member.setAddress(request.getParameter("address"));
		member.setTel(request.getParameter("tel"));

		// 회원가입 처리
		LogonDBBean dbPro = LogonDBBean.getInstance();
		return "/member/registerPro.jsp";
	}

}
