package cn.dg.interfaces;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface T_ACL_User_interface {
	//验证用户帐号密码
	public boolean checklogin(@WebParam(name="username")String username,@WebParam(name="pwd")String pwd);
}
