package cn.smbms.servlet.user;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;
import cn.smbms.util.MD5Encrypt;

/**
 * 处理用户登录
 */
@WebServlet("/login.html")
public class LoginServlet extends HttpServlet {

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
		User user = service.login(userCode);
		try {
			if (userCode.equals(user.getUserCode())
					&& MD5Encrypt.validatePassword(userPassword,
							user.getUserPassword())) {// 登录成功
				// 放入session
				request.getSession().setAttribute(Constants.USER_SESSION, user);
				// 页面跳转（welcome.jsp）
				response.sendRedirect("jsp/welcome.jsp");
			} else {
				request.setAttribute("error", "用户名或密码不正确");
				// 页面跳转（login.jsp） 带出提示信息--转发
				request.getRequestDispatcher("login.jsp").forward(request,
						response);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
