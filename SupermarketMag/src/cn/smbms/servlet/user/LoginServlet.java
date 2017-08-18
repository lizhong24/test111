package cn.smbms.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

	// private UserService service = (UserService) ServiceFactory
	// .getServiceImpl("UserService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户名和密码
		String userCode = request.getParameter("userCode");
		String userPassword = request.getParameter("userPassword");

		// 调用service方法，进行用户匹配
		UserService service = (UserService) ServiceFactory
				.getServiceImpl("UserService");
		User user = service.login(userCode, userPassword);

		if (null != user) {// 登录成功
			// 放入session
			request.getSession().setAttribute(Constants.USER_SESSION, user);
			// 页面跳转（welcome.jsp）
			response.sendRedirect("jsp/welcome.jsp");
		} else {
			request.setAttribute("error", "用户名或密码不正确");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			// 页面跳转（loogin.jsp） 带出提示信息--转发
		}
	}
}