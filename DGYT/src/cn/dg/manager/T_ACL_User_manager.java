package cn.dg.manager;

import java.sql.ResultSet;

import cn.dg.connect.DBConnectionManager;
import cn.dg.connect.poolmanager;
import cn.dg.helper.StringHelper;

public class T_ACL_User_manager {
	

	public boolean isExists(String user, String pwd) {
		poolmanager dbcon = new poolmanager();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from t_acl_user where UserID ='");
		sb.append(StringHelper.convertStringNull(user));
		sb.append("' and pwd='" + pwd + "'");
		try {
			ResultSet rs =  dbcon.executeQuery(sb.toString());
			
			 int rowCount = 0;
        	rs.last();
            rowCount = rs.getRow();
            dbcon.freeConnection();
			if(rowCount == 0)
				return false;
			else
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			 dbcon.freeConnection();
			return false;
		}
		}
		

	public static boolean isExiststest(String user, String pwd) {
		System.out.println(user + "++++++" + "pwd");
		if ("111".equals(user) && "111".equals(pwd))
			return true;
		else
			return false;
	}
}
