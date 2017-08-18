package cn.bdqn.bean;

import java.util.Date;

public class News_Comment {
	private int id;// 用户ID
	private int newsId;// 评论新闻id
	private String content;// 评论内容
	private String author;// 评论者
	private String ip;// 评论ip
	private int userType;// 用户类型 0：管理员 1：普通用户
	private Date createDate;// 发表时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public News_Comment(int id, int newsId, String content, String author,
			String ip, int userType, Date createDate) {
		super();
		this.id = id;
		this.newsId = newsId;
		this.content = content;
		this.author = author;
		this.ip = ip;
		this.userType = userType;
		this.createDate = createDate;
	}

	public News_Comment() {
		super();
	}

	@Override
	public String toString() {
		return "News_Comment [id=" + id + ", newsId=" + newsId + ", content="
				+ content + ", author=" + author + ", ip=" + ip + ", userType="
				+ userType + ", createDate=" + createDate + "]";
	}

}
