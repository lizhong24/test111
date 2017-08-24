package cn.smbms.servlet.provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.smbms.bean.Provider;
import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageUtil;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

/**
 * 操作供应商的控制层
 */
@WebServlet("/provider.html")
public class ProviderServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(ProviderServlet.class);

	private ProviderService service = (ProviderService) ServiceFactory
			.getServiceImpl("ProviderService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method != null && method.equals("query")) {// 查询供应商
			this.query(request, response);
		} else if (method != null && method.equals("add")) {// 增加供应商
			this.add(request, response);
		} else if (method != null && method.equals("delprovider")) {// 删除供应商
			this.delProvider(request, response);
		} else if (method != null && method.equals("view")) {// 跳转到查看供应商的页面
			this.getProviderById(request, response, "jsp/providerview.jsp");
		} else if (method != null && method.equals("modify")) {// 跳转到修改供应商的页面
			this.getProviderById(request, response, "jsp/providerupdate.jsp");
		} else if (method != null && method.equals("modifyexe")) {// 修改供应商
			this.modifyProvider(request, response);
		} else {
			this.query(request, response);
		}
	}

	// 修改供应商
	public void modifyProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Provider provider = new Provider();
		provider.setId(Integer.valueOf(request.getParameter("pid")));
		provider.setProCode(request.getParameter("proCode"));
		provider.setProName(request.getParameter("proName"));
		provider.setProContact(request.getParameter("proContact"));
		provider.setProPhone(request.getParameter("proPhone"));
		provider.setProFax(request.getParameter("proFax"));
		provider.setProAdderss(request.getParameter("proAdderss"));
		provider.setProDesc(request.getParameter("proDesc"));
		provider.setModifyBy(((User) (request.getSession()
				.getAttribute(Constants.USER_SESSION))).getId());
		provider.setModifyDate(new Date());

		if (service.update(provider)) {
			request.getRequestDispatcher("jsp/providerlist.jsp").forward(
					request, response);
		} else {
			request.getRequestDispatcher("jsp/providerupdate.jsp").forward(
					request, response);
		}
	}

	// 通过id得到当前供应商
	private void getProviderById(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
		String id = request.getParameter("pid");
		if (!StringUtils.isNullOrEmpty(id)) {
			// 调用后台方法得到user对象
			Provider provider = service.getById(id);
			request.setAttribute("provider", provider);
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

	// 删除供应商
	private void delProvider(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("pid");
		logger.debug(id);
		Integer flag = service.deleteProviderById(id);
		if (flag == 0) {
			request.getRequestDispatcher("jsp/providerlist.jsp").forward(
					request, response);
		} else {
			request.setAttribute("flag", flag);
			request.getRequestDispatcher("jsp/error.jsp").forward(request,
					response);
		}

	}

	// 增加供应商
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String proCode = request.getParameter("proCode");
		String proName = request.getParameter("proName");
		String proContact = request.getParameter("proContact");
		String proPhone = request.getParameter("proPhone");
		String proAdderss = request.getParameter("proAdderss");
		String proDesc = request.getParameter("proDesc");
		String proFax = request.getParameter("proFax");

		Provider provider = new Provider();
		provider.setProCode(proCode);
		provider.setProName(proName);
		provider.setProPhone(proPhone);
		provider.setProContact(proContact);
		provider.setProDesc(proDesc);
		provider.setProAdderss(proAdderss);
		provider.setProFax(proFax);
		provider.setCreatedBy(((User) request.getSession().getAttribute(
				Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());

		if (service.add(provider)) {
			request.getRequestDispatcher("jsp/providerlist.jsp").forward(
					request, response);
		} else {
			request.getRequestDispatcher("jsp/error.jsp").forward(request,
					response);
		}
	}

	// 查询供应商列表
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 得到输入的供应商名称
		String queryProviderName = request.getParameter("queryProviderName");
		if (StringUtils.isNullOrEmpty(queryProviderName)) {
			queryProviderName = "";
		}
		// 获取当前页
		String num = request.getParameter("pageIndex");
		// 实例化分页的工具类
		PageUtil pageUtil = new PageUtil();
		if (num != null && !num.equals("")) { // 给当前页赋值
			pageUtil.setPageIndex(Integer.parseInt(num));
		} else { // 第一次没有值
			pageUtil.setPageIndex(1);
		}
		// 给总记录数赋值的同时也给总页数赋值
		int totalCount = service.getTotalCount(queryProviderName);
		pageUtil.setTotalCount(totalCount);// 总记录数赋值

		// 分页显示 新闻信息
		List<Provider> providerList = service.getPageList(queryProviderName,
				pageUtil);

		if (providerList != null && providerList.size() > 0) {
			providerList.get(0).setPageUtil(pageUtil); // 给分页的属性赋值
		}
		// 需要把list整体转换成json格式的数据 传递给前台
		Gson gson = new Gson();
		String json = gson.toJson(providerList);
		System.out.println(json);
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.close();
		writer.flush();

		logger.debug(JSON.toJSON(providerList));
	}
}
