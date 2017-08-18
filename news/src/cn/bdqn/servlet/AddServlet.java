package cn.bdqn.servlet;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.bdqn.bean.News_Detail;
import cn.bdqn.service.ServiceFactory;
import cn.bdqn.service.newsdetail.NewsDetailService;

/**
 *文件新增===》上传
 *@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 解决post乱码问题
		request.setCharacterEncoding("utf-8");

		// 创建一个新闻对象
		News_Detail detail = new News_Detail();
		// 获取表单中 的新闻信息

		String categoryId = request.getParameter("categoryId");
		int categoryId1 = 0;// 传入数据库的值是int类型,这里需要把用户选择的类型进行转换
		if (categoryId.equals("国内")) {
			categoryId = "1";
			categoryId1 = Integer.parseInt(categoryId);
		} else if (categoryId.equals("国际")) {
			categoryId = "2";
			categoryId1 = Integer.parseInt(categoryId);
		} else if (categoryId.equals("娱乐")) {
			categoryId = "3";
			categoryId1 = Integer.parseInt(categoryId);
		} else if (categoryId.equals("军事")) {
			categoryId = "4";
			categoryId1 = Integer.parseInt(categoryId);
		}

		detail.setTitle(request.getParameter("title"));
		detail.setSummary(request.getParameter("summary"));
		detail.setContent(request.getParameter("content"));
		detail.setAuthor(request.getParameter("author"));
		// 获取修改时间
		String createDate = request.getParameter("createDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date time = null;
		// 将用户修改的日期转换为date类型
		try {
			time = sdf.parse(createDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		detail.setCreateDate(time);
		detail.setCategoryId(categoryId1);
		NewsDetailService service = new NewsDetailServiceImpl();
		boolean flag = service.addNews(detail);
		if (flag) {// 增加成功
			response.sendRedirect("listServlet");
		} else {
			response.sendRedirect("add.jsp");
		}

	}
 */

@WebServlet("/addServlet")
public class AddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("临时文件的位置：" + System.getProperty("java.io.temdir"));
		// 因为新增新闻 需要一个新闻对象
		News_Detail detail = new News_Detail();

		// 01.创建DiskFileItemFactory对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 02.通过DiskFileItemFactory对象 创建出 ServletFileUpload
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 判断是不是 文件上传请求
		boolean flag = upload.isMultipartContent(request);
		System.out.println("flag======>" + flag);
		if (flag) {
			// 03.通过ServletFileUpload对象的parseRequest方法 获取List<FileItem>
			try {
				List<FileItem> items = upload.parseRequest(request);
				// 04.对list集合进行遍历，每遍历一次都要使用FileItem类中的isFormField（）来判断是否为文件
				Iterator<FileItem> its = items.iterator();
				while (its.hasNext()) {
					// 获取每一项
					FileItem item = its.next();
					if (item.isFormField()) {// 普通字段
						// 001.普通字段 使用getFieldName() 和getString() 来获取字段名和字段值
						String fieldName = item.getFieldName();
						if (fieldName.equals("title")) {
							detail.setTitle(item.getString("utf-8"));
						} else if (fieldName.equals("author")) {
							detail.setAuthor(item.getString("utf-8"));
						} else if (fieldName.equals("summary")) {
							detail.setSummary(item.getString("utf-8"));
						} else if (fieldName.equals("content")) {
							detail.setContent(item.getString("utf-8"));
						} else if (fieldName.equals("createDate")) {
							detail.setCreateDate(new SimpleDateFormat(
									"MM/dd/yyyy").parse(item.getString("utf-8")));
						} else if (fieldName.equals("categoryId")) {
							detail.setCategoryId(Integer.parseInt(item
									.getString("utf-8")));
						}
					} else {// 文件上传
						// 指定文件上传的位置getServletContext() 获取的是项目在服务器的位置
						// E:\apache-tomcat-7.0.75\webapps\news
						String uploadPath = request.getSession()
								.getServletContext().getRealPath("upload/");
						File file = new File(uploadPath);
						if (!file.exists()) {// 不存在 我们创建
							file.mkdirs();
						}
						String fileName = item.getName(); // 文件名称
						if (!fileName.equals("") && fileName != null) { // 判断用户是否选择了文件
							File uploadFile = new File(fileName); // 上传的文件
							File saveFile = new File(uploadPath,
									uploadFile.getName());
							// 真正的输出
							item.write(saveFile);
							detail.setPicPath(uploadFile.getName());
						}
					}
				}

				NewsDetailService service = (NewsDetailService) ServiceFactory
						.getServiceImpl("NewsDetailService");
				int num = service.add(detail);
				if (num > 0) { // 新增成功
					response.sendRedirect("listServlet");
				} else {
					response.sendRedirect("FindCategoryServlet");
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
