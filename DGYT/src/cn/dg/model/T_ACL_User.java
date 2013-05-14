package cn.dg.model;

public class T_ACL_User {
	int userid;
	String username;
	String pwd;
	boolean isexpire;
	public T_ACL_User() {
		super();
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public T_ACL_User(int userid, String username, String pwd, boolean isexpire) {
		super();
		this.userid = userid;
		this.username = username;
		this.pwd = pwd;
		this.isexpire = isexpire;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean isIsexpire() {
		return isexpire;
	}
	public void setIsexpire(boolean isexpire) {
		this.isexpire = isexpire;
	}
}

