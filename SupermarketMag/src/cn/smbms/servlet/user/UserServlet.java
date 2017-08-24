package cn.smbms.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
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

import org.apache.log4j.Logger;

import cn.smbms.bean.User;
import cn.smbms.service.ServiceFactory;
import cn.smbms.service.user.UserService;
import cn.smbms.util.Constants;
import cn.smbms.util.MD5Encrypt;
import cn.smbms.util.PageUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

/**
 * 操作用户的控制层
 */
@WebServlet("/user.html")
public class UserServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(UserServlet.class);

	private UserService service = (UserService) ServiceFactory
			.getServiceImpl("UserService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 通过前台得到用户传过来的方法method
		String method = request.getParameter("method");
		if (method != null && method.equals("add")) {// 增加操作
			this.add(request, response);
		} else if (method != null && method.equals("query")) {// 查询用户列表
			this.query(request, response);
		} else if (method != null && method.equals("ucexist")) {// 查询用户列表
			this.userCodeExist(request, response);
		} else if (method != null && method.equals("delUser")) {// 删除用户
			this.delUser(request, response);
		} else if (method != null && method.equals("view")) {// 跳转到查看用户的页面
			this.getUserById(request, response, "jsp/userview.jsp");
		} else if (method != null && method.equals("modify")) {// 跳转到修改用户的页面
			this.getUserById(request, response, "jsp/userupdate.jsp");
		} else if (method != null && method.equals("modifyexe")) {// 修改用户
			this.modifyUser(request, response);
		} else if (method != null && method.equals("pwdmodify")) {// 找到旧密码
			this.getPwdByUserId(request, response);
		} else if (method != null && method.equals("savepwd")) {// 修改密码
			this.updatePwd(request, response);
		} else {
			this.query(request, response);
		}
	}

	// 修改密码
	private void updatePwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		String newpassword = request.getParameter("newpassword");
		boolean flag = false;
		if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
			try {
				flag = service.updatePwd(((User) o).getId(),
						MD5Encrypt.getEncryptedPwd(newpassword));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (flag) {
				request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功！");
				// 密码修改成功后跳转到登录页面，重新登录！！
				request.getRequestDispatcher("exit.html").forward(request,
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

	// 通过当前用户id得到当前用户密码
	private void getPwdByUserId(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 先判断session是否失效！！！！！
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		String oldpassword = request.getParameter("oldpassword");
		HashMap<String, String> resuleMap = new HashMap<String, String>();
		if (null != o && !StringUtils.isNullOrEmpty(oldpassword)) {
			String sessionPwd = ((User) o).getUserPassword();
			try {
				if (MD5Encrypt.validatePassword(oldpassword, sessionPwd)) {
					resuleMap.put("result", "true");
				} else {
					resuleMap.put("result", "false");
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	// 修改用户
	private void modifyUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("uid");
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String userType = request.getParameter("userType");

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

		if (service.update(user)) {
			request.getRequestDispatcher("jsp/userlist.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("jsp/userupdate.jsp").forward(request,
					response);
		}

	}

	// 通过id得到当前用户
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

		logger.debug(delId);
		if (service.deleteUserById(delId)) {
			request.getRequestDispatcher("jsp/userlist.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("jsp/error.jsp").forward(request,
					response);
		}
	}

	// 判断用户账号是否可用
	private void userCodeExist(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("userCode");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(userCode)) {
			// userCode == null || userCode.equals("")
			resultMap.put("userCode", "exist");
		} else {
			User user = service.selectUserCodeExist(userCode);
			if (null != user) {
				resultMap.put("userCode", "exist");
			} else {
				resultMap.put("userCode", "notexist");
			}
		}

		// 把resultMap转为json字符串以json的形式输出
		response.setContentType("appliaction/json");// 配置上下文的输出类型
		// 从response对象中获取往外输出的writer对象
		PrintWriter outPrintWriter = response.getWriter();
		// 把resultMap转为json字符串输出
		outPrintWriter.write(JSONArray.toJSONString(resultMap));
		outPrintWriter.flush();// 刷新
		outPrintWriter.close();// 关闭流
	}

	// 增加操作
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		try {
			user.setUserPassword(MD5Encrypt.getEncryptedPwd(userPassword));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
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

		if (service.add(user)) {// 增加成功
			request.getRequestDispatcher("jsp/userlist.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("jsp/useradd.jsp").forward(request,
					response);
		}

	}

	// 查询用户列表
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String queryUserName = request.getParameter("queryUserName");// 用户输入的用户名
		String num = request.getParameter("pageIndex");
		request.setAttribute("queryUserName", queryUserName);
		// 实例化分页的工具类
		PageUtil pageUtil = new PageUtil();
		if (num != null && !num.equals("")) { // 给当前页赋值
			pageUtil.setPageIndex(Integer.parseInt(num));
		} else { // 第一次没有值
			pageUtil.setPageIndex(1);
		}
		// 给总记录数赋值 的同时 也给 总页数 赋值了
		Integer totalCount = service.getTotalCount(queryUserName);
		pageUtil.setTotalCount(totalCount);// 总记录数赋值

		// 分页显示新闻信息
		List<User> userList = service.getPageList(queryUserName, pageUtil);
		if (userList != null && userList.size() > 0) {
			userList.get(0).setPageUtil(pageUtil); // 给分页的属性赋值
		}
		// 需要把list整体转换成json格式的数据 传递给前台
		Gson gson = new Gson();
		String json = gson.toJson(userList);
		System.out.println(json);
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.close();
		writer.flush();

		logger.debug(JSON.toJSON(userList));
	}
}
