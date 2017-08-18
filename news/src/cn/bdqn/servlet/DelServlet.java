package cn.bdqn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bdqn.service.ServiceFactory;
import cn.bdqn.service.newsdetail.NewsDetailService;

@WebServlet("/delServlet")
public class DelServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取请求中的id
		String id = request.getParameter("id");

		NewsDetailService service = (NewsDetailService) ServiceFactory
				.getServiceImpl("NewsDetailService");

		int num = Integer.parseInt(id);
		num = service.deleteById(id);
		if (num > 0) {
			// 重定向到main.jsp????listServlet可以获取新闻信息
			response.sendRedirect("listServlet");
		} else {
			System.out.print("删除失败！");
		}
	}

}
