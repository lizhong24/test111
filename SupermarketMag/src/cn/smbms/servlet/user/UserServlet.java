package cn.smbms.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;
import cn.smbms.util.PageUtil;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

@WebServlet("/user.do")
public class UserServlet extends HttpServlet {

	UserService service = (UserService) ServiceFactory
			.getServiceImpl("UserService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");

		System.out.println("method----->" + method);

		if (method != null && method.equals("add")) {// 增加操作
			this.add(request, response);
		} else if (method != null && method.equals("query")) {// 查询用户列表
			this.query(request, response);
		} else if (method != null && method.equals("ucexist")) {// 查询用户列表
			this.userCodeExist(request, response);
		} else if (method != null && method.equals("delUser")) {// 删除用户
			this.delUser(request, response);
		} else if (method != null && method.equals("view")) {// 删除用户
			this.getUserById(request, response, "jsp/userview.jsp");
		} else if (method != null && method.equals("modify")) {// 删除用户
			this.getUserById(request, response, "jsp/usermodify.jsp");
		} else if (method != null && method.equals("modifyexe")) {// 删除用户
			this.modifyUser(request, response);
		} else if (method != null && method.equals("pwdmodify")) {
			this.getPwdByUserId(request, response);
		} else if (method != null && method.equals("savepwd")) {
			this.updatePwd(request, response);
		}

	}

	private void updatePwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		String newpassword = request.getParameter("newpassword");
		boolean flag = false;
		if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
			flag = service.updatePwd(((User) o).getId(), newpassword);
			if (flag) {
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功！");
				// 密码修改成功后跳转到登录页面，重新登录！！
				request.getRequestDispatcher("logout.do").forward(request,
						response);
			} else {
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
				request.getRequestDispatcher("jsp/pwdmodify.jsp").forward(
						request, response);
			}
		} else {
			request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
			request.getRequestDispatcher("jsp/pwdmodify.jsp").forward(request,
					response);
		}

	}

	private void getPwdByUserId(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 先判断session是否失效！！！！！
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		String oldpassword = request.getParameter("oldpassword");
		HashMap<String, String> resuleMap = new HashMap<String, String>();
		if (null != o && !StringUtils.isNullOrEmpty(oldpassword)) {
			String sessionPwd = ((User) o).getUserPassword();
			if (oldpassword.equals(sessionPwd)) {
				resuleMap.put("result", "true");
			} else {
				resuleMap.put("result", "false");
			}
		} else {
			resuleMap.put("result", "error");
		}

		response.setContentType("application/json");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.write(JSONArray.toJSONString(resuleMap));
		outPrintWriter.flush();
		outPrintWriter.close();
	}

	private void modifyUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("uid");
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String userType = request.getParameter("userType");

		// System.out.println(id);
		// System.out.println(userName);

		User user = new User();
		user.setId(Integer.valueOf(id));
		user.setUserName(userName);
		user.setGender(Integer.valueOf(gender));
		try {
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setPhone(phone);
		user.setAddress(address);
		user.setUserType(Integer.valueOf(userType));
		user.setModifyBy(((User) request.getSession().getAttribute(
				Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());

		if (service.modifyUser(user)) {
			request.getRequestDispatcher("user.do?method=query").forward(
					request, response);
		} else {
			request.getRequestDispatcher("jsp/usermodify.jsp").forward(request,
					response);
		}

	}

	private void getUserById(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
		String id = request.getParameter("uid");
		if (!StringUtils.isNullOrEmpty(id)) {
			// 调用后台方法得到user对象
			User user = service.getById(id);
			request.setAttribute("user", user);
			request.getRequestDispatcher(url).forward(request, response);
		}

	}

	// 删除用户
	private void delUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("uid");
		Integer delId = 0;
		try {
			delId = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			delId = 0;
		}
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (delId <= 0) {
			resultMap.put("delResult", "notexist");
		} else {
			if (service.deleteUserById(delId)) {
				resultMap.put("delResult", "true");
				// request.getRequestDispatcher("user.do?method=query").forward(
				// request, response);
			} else {
				resultMap.put("delResult", "false");
				// request.getRequestDispatcher("jsp/userlist.jsp").forward(
				// request, response);
			}
		}

		// 把resultMap转换成json对象输出
		response.setContentType("application/json");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.write(JSONArray.toJSONString(resultMap));
		outPrintWriter.flush();
		outPrintWriter.close();
	}

	// 判断用户账号是否可用
	private void userCodeExist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("userCode");

		HashMap<String, String> resuleMap = new HashMap<String, String>();

		if (StringUtils.isNullOrEmpty(userCode)) {
			// userCode == null || userCode.equals("")
			resuleMap.put("userCode", "exist");
		} else {
			User user = service.selectUserCodeExist(userCode);
			if (null != user) {
				resuleMap.put("userCode", "exist");
			} else {
				resuleMap.put("userCode", "notexist");
			}
		}

		// 把resultMap转为json字符串以json的形式输出
		// 配置上下文的输出类型
		response.setContentType("appliaction/json");// 配置上下文的输出类型
		// 从response对象中获取往外输出的writer对象
		PrintWriter outPrintWriter = response.getWriter();
		// 把resultMap转为json字符串输出
		outPrintWriter.write(JSONArray.toJSONString(resuleMap));
		outPrintWriter.flush();// 刷新
		outPrintWriter.close();// 关闭流
	}

	// 增加操作
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("add()=============");
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String userType = request.getParameter("userType");

		User user = new User();
		user.setUserCode(userCode);
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setAddress(address);
		try {
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setGender(Integer.valueOf(gender));
		user.setPhone(phone);
		user.setUserType(Integer.valueOf(userType));
		user.setCreationDate(new Date());
		user.setCreateBy(((User) request.getSession().getAttribute(
				Constants.USER_SESSION)).getId());

		if (service.add(user)) {
			request.getRequestDispatcher("user.do?method=query").forward(
					request, response);
		} else {
			request.getRequestDispatcher("jsp/useradd.jsp").forward(request,
					response);
		}

	}

	/** 
	 * // 查询用户列表
		private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryUserName = request.getParameter("queryUserName");
		System.out.println("queryUserName servlet---" + queryUserName);
		List<User> userList = null;
		userList = service.getUserList(queryUserName);
		request.getSession().setAttribute("userList", userList);
		request.getSession().setAttribute("queryUserName", queryUserName);
		response.sendRedirect("jsp/userlist.jsp");
	 */

	// 查询用户列表
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryUserName = request.getParameter("queryUserName");

		String num = request.getParameter("pageIndex");
		// 实例化分页的工具类
		PageUtil pageUtil = new PageUtil();
		if (num != null && !num.equals("")) { // 给当前页赋值
			pageUtil.setPageIndex(Integer.parseInt(num));
		} else { // 第一次没有值
			pageUtil.setPageIndex(1);
		}
		// 给总记录数赋值 的同时 也给 总页数 赋值了

		try {
			int totalCount = service.getTotalCount(queryUserName);
			pageUtil.setTotalCount(totalCount);// 总记录数赋值
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 分页显示 新闻信息
		try {
			List<User> userList = service.getPageList(queryUserName, pageUtil);
			if (userList != null) {
				// 还是要把集合放进 作用域中 便于前台获取
				request.getSession().setAttribute("userList", userList);
				request.getSession().setAttribute("queryUserName",
						queryUserName);
				// 把分页的工具类对象页得放进作用域中
				request.getSession().setAttribute("pageUtil", pageUtil);
				response.sendRedirect("jsp/userlist.jsp");
			} else {
				System.out.println("出现异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
