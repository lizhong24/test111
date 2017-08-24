package cn.smbms.servlet.bill;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.smbms.bean.Bill;
import cn.smbms.bean.Provider;
import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

/**
 * 操作账单的控制层
 */
@WebServlet("/bill.html")
public class BillServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(BillServlet.class);

	private BillService billService = (BillService) ServiceFactory
			.getServiceImpl("BillService");

	private ProviderService providerService = (ProviderService) ServiceFactory
			.getServiceImpl("ProviderService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method != null) {
			if (method.equals("query")) {// 分页查询账单列表
				this.query(request, response);
			} else if (method.equals("view")) {// 跳转到查看账单的页面
				this.getBillById(request, response, "jsp/billview.jsp");
			} else if (method.equals("modify")) {// 跳转到修改账单的页面
				this.getBillById(request, response, "jsp/billupdate.jsp");
			} else if (method.equals("modifysave")) {// 修改账单
				this.modifyBill(request, response);
			} else if (method.equals("delete")) {// 删除账单
				this.delBill(request, response);
			} else if (method.equals("add")) {// 增加账单
				this.addBill(request, response);
			} else if (method.equals("getproviderlist")) {// 得到去重的供应商列表
				this.getProviderList(request, response);
			}
		} else {
			this.query(request, response);
		}
	}

	// 分页查询账单列表
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Provider> providerList = new ArrayList<Provider>();

		providerList = providerService.getProviderList("");
		request.setAttribute("providerList", providerList);
		String queryProductName = request.getParameter("queryProductName");
		String queryProviderId = request.getParameter("queryProviderId");
		String queryIsPayment = request.getParameter("queryIsPayment");

		Bill bill = new Bill();
		bill.setProductName(queryProductName);
		if (!StringUtils.isNullOrEmpty(queryProviderId)) {
			if (Integer.valueOf(queryProviderId) > 0) {
				bill.setProviderId(Integer.valueOf(queryProviderId));
			}
		}
		if (!StringUtils.isNullOrEmpty(queryIsPayment)) {
			if (Integer.valueOf(queryIsPayment) > 0) {
				bill.setIsPayment(Integer.valueOf(queryIsPayment));
			}
		}

		String pageIndex = request.getParameter("pageIndex");
		// 实例化分页的工具类
		PageUtil pageUtil = new PageUtil();
		if (pageIndex != null && !pageIndex.equals("")) { // 给当前页赋值
			pageUtil.setPageIndex(Integer.parseInt(pageIndex));
		} else { // 第一次没有值
			pageUtil.setPageIndex(1);
		}

		int totalCount = billService.getTotalCount(bill);
		pageUtil.setTotalCount(totalCount);// 总记录数赋值

		// 分页显示新闻信息
		List<Bill> billList = billService.getPageBillList(bill, pageUtil);
		if (billList != null && billList.size() > 0) {
			billList.get(0).setPageUtil(pageUtil); // 给分页的属性赋值
		}
		// 需要把list整体转换成json格式的数据 传递给前台
		Gson gson = new Gson();
		String json = gson.toJson(billList);
		System.out.println(json);
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.close();
		writer.flush();

		logger.debug(JSON.toJSON(billList));
	}

	// 通过id得到一个账单对象
	public void getBillById(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
		String billId = request.getParameter("billid");
		if (!StringUtils.isNullOrEmpty(billId)) {
			Bill bill = billService.getById(billId);
			if (bill.getId() > 0) {
				request.setAttribute("bill", bill);
				request.getRequestDispatcher(url).forward(request, response);
			} else {
				response.setContentType("text/html;charset=UTF-8 ");
				PrintWriter out = response.getWriter();
				String path = request.getContextPath();
				out.print("<script>alert('对不起，该数据已不存在');window.location.href='"
						+ path + "/bill.html?method=query';</script>");
				out.close();
			}
		}
	}

	// 修改账单
	public void modifyBill(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String billId = request.getParameter("billId");
		String productName = request.getParameter("productName");
		String productUnit = request.getParameter("productUnit");
		String productCount = request.getParameter("productCount");
		String totalPrice = request.getParameter("totalPrice");
		String providerId = request.getParameter("providerId");
		String isPayment = request.getParameter("isPayment");
		String productDesc = request.getParameter("productDesc");
		Bill bill = new Bill();
		bill.setId(Integer.valueOf(billId));
		bill.setProductName(productName);
		bill.setProductUnit(productUnit);
		bill.setProductCount(new BigDecimal(productCount).setScale(2,
				BigDecimal.ROUND_DOWN));
		bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,
				BigDecimal.ROUND_DOWN));
		bill.setProviderId(Integer.valueOf(providerId));
		bill.setIsPayment(Integer.valueOf(isPayment));
		bill.setProductDesc(productDesc);
		bill.setModifyBy(((User) (request.getSession()
				.getAttribute(Constants.USER_SESSION))).getId());
		bill.setModifyDate(new Date());

		if (billService.update(bill)) {
			response.sendRedirect("jsp/billlist.jsp");
		} else {
			request.getRequestDispatcher("jsp/billupdate.jsp").forward(request,
					response);
		}
	}

	// 增加账单
	public void addBill(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Provider> providerList = new ArrayList<Provider>();
		providerList = providerService.getProviderList("");
		request.setAttribute("providerList", providerList);

		String queryProviderId = request.getParameter("queryProviderId");
		String billCode = request.getParameter("billCode");
		String productName = request.getParameter("productName");
		String productUnit = request.getParameter("productUnit");
		String productCount = request.getParameter("productCount");
		String totalPrice = request.getParameter("totalPrice");
		String providerId = request.getParameter("providerId");
		String isPayment = request.getParameter("isPayment");

		Bill bill = new Bill();
		bill.setBillCode(billCode);
		bill.setProductName(productName);

		bill.setProductUnit(productUnit);
		bill.setProductCount(new BigDecimal(productCount).setScale(2,
				BigDecimal.ROUND_DOWN));
		bill.setIsPayment(Integer.parseInt(isPayment));
		bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,
				BigDecimal.ROUND_DOWN));
		bill.setProviderId(Integer.parseInt(providerId));
		bill.setCreatedBy(((User) request.getSession().getAttribute(
				Constants.USER_SESSION)).getId());
		bill.setCreationDate(new Date());

		if (billService.add(bill)) {
			request.setAttribute("providerList", providerList);
			request.setAttribute("queryProviderId", queryProviderId);
			response.sendRedirect("jsp/billlist.jsp");
		} else {
			request.getRequestDispatcher("jsp/billadd.jsp").forward(request,
					response);
		}
	}

	// 删除账单
	public void delBill(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String billId = request.getParameter("billid");
		Integer delId = 0;
		try {
			delId = Integer.parseInt(billId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			delId = 0;
		}
		logger.debug(delId);
		if (billService.delBill(billId)) {
			request.getRequestDispatcher("jsp/billlist.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("jsp/error.jsp").forward(request,
					response);
		}
	}

	// 得到去重的供应商列表
	public void getProviderList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Provider> providerList = new ArrayList<Provider>();

		providerList = providerService.getProviderList("");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(JSONArray.toJSONString(providerList));
		out.flush();
		out.close();
	}

}
