package cn.smbms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.smbms.bean.User;
import cn.smbms.util.Constants;

@WebFilter("/*")
public class LoginFilter implements Filter {
	private Logger log = Logger.getLogger(LoginFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 解决post请求乱码问题
		request.setCharacterEncoding("utf-8");
		// 向下转型
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 获取用户前台请求的路径
		String path = req.getRequestURI();
		// 从session获取是否有用户
		User user = (User) req.getSession()
				.getAttribute(Constants.USER_SESSION);
		log.debug(path);
		if (path.indexOf("login") > -1
				|| user != null
				|| (path.contains(".css") || path.contains(".js")
						&& (!path.contains(".jsp")))) {
			// 放行
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect("login.jsp");
		}
	}

	@Override
	public void destroy() {
	}

}
