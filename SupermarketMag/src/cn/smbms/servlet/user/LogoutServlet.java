package cn.smbms.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.util.Constants;

/**
 * 退出系统
 */
@WebServlet("/exit.html")
public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 清除session
		request.getSession().removeAttribute(Constants.USER_SESSION);
		// 返回到登录页面
		response.sendRedirect("login.jsp");
	}

}
