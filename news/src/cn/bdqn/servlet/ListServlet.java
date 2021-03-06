package cn.bdqn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bdqn.bean.News_Detail;
import cn.bdqn.service.ServiceFactory;
import cn.bdqn.service.newsdetail.NewsDetailService;
import cn.bdqn.util.PageUtil;

import com.google.gson.Gson;

@WebServlet("/listServlet")
public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		// 调用service层获取所有新闻列表的方法
		NewsDetailService service = (NewsDetailService) ServiceFactory
				.getServiceImpl("NewsDetailService");
		// List<News_Detail> details = s.findAllNewsDetail();//显示所有新闻

		/**
		 * 既然要分页，必须得获取用户给我们的pageIndex
		 * 只有拿到pageIndex 我们才能书写sql语句！
		 * limit (pageIndex-1)*pageSize,3
		 * 
		 * 第一次 用户登录  成功之后进入我们ListServlet
		 * pageIndex有值吗？
		 * 没有值，我们给他赋予初始值
		 */
		String num = request.getParameter("pageIndex");
		// 实例化分页的工具类
		PageUtil util = new PageUtil();
		if (num != null && !num.equals("")) {// 给当前页赋值
			util.setPageIndex(Integer.parseInt(num));
		} else {// 第一次没有值
			util.setPageIndex(1);
		}
		// 给总记录数赋值的同时也给总页数赋值了
		int totalCount = service.findPageCounts();// 总记录数赋值
		util.setTotalCount(totalCount);

		// 分页显示新闻信息
		List<News_Detail> details = service.findPageList(util);
		if (details != null) {
			// 创建Gson
			Gson gson = new Gson();
			// 需要把pageUtil放进集合第一个对象中
			details.get(0).setPageUtil(util);
			String json = gson.toJson(details);
			PrintWriter writer = response.getWriter();
			response.setHeader("Content-type", "text/html;charset=utf-8");
			writer.print(json);
			writer.close();
		} else {
			System.out.println("出现异常！");
		}

	}

}
