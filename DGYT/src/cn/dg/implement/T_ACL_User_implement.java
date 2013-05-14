package cn.dg.implement;

import javax.jws.WebService;

import cn.dg.interfaces.T_ACL_User_interface;
import cn.dg.manager.T_ACL_User_manager;
@WebService(endpointInterface="cn.dg.interfaces.T_ACL_User_interface",serviceName="checklogin")
public class T_ACL_User_implement implements T_ACL_User_interface {

	public boolean checklogin(String username, String pwd) {
		T_ACL_User_manager t = new T_ACL_User_manager();
		return t.isExists(username, pwd);
	}

}
