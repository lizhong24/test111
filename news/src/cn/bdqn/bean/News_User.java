package cn.bdqn.bean;

public class News_User {

	private int id;// 用户ID
	private String userName;// 用户名
	private String password;// 密码
	private String email;// email
	private int userType;// 用户类型 0：管理员 1：普通用户

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public News_User(int id, String userName, String password, String email,
			int userType) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userType = userType;
	}

	public News_User() {
		super();
	}

	@Override
	public String toString() {
		return "News_User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", email=" + email + ", userType=" + userType
				+ "]";
	}

}
