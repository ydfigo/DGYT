package cn.dg.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class DBConnectionPool {
	private int checkedOut; //使用链接数目
	private Vector freeConnections = new Vector();
	private int maxConn;
	private String name;
	private String URL;
	private String User;
	private String pwd;
	private Connection con;

	/**
	 * 创建新的连接池
	 * 
	 * @param name
	 *            连接池名字
	 * @param URL
	 *            数据库的JDBC URL
	 * @param dbInfo
	 *            数据库连接信息
	 * @param maxConn
	 *            此连接池允许建立的最大连接数
	 */
	public DBConnectionPool(String name, String URL, String User,String pwd, int maxConn) {
		this.name = name.trim();
		this.URL = URL.trim();
		this.User = User.trim();
		this.pwd = pwd.trim();
		this.maxConn = maxConn;
	}

	/**
	 * 将不再使用的连接返回给连接池
	 * 
	 * @param con
	 *            客户程序释放的连接
	 */
	public synchronized void freeConnection(Connection con) {
		// 将指定连接加入到向量末尾
		freeConnections.addElement(con);
		checkedOut--;
		notifyAll();
	}

	/**
	 * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
	 * 然后递归调用自己以尝试新的可用连接.
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		System.out.println("使用链接数目:" + checkedOut);
		System.out.println("空闲链接数：:" + freeConnections.size());
		if (freeConnections.size() > 0) {
			// 获取向量中第一个可用连接
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				if (con.isClosed()) {
					System.out.println("从连接池" + name + "删除一个无效连接");
					// 递归调用自己,尝试再次获取可用连接
					con = getConnection();
				}
			} catch (SQLException e) {
				System.out.println("从连接池" + name + "删除一个无效连接");
				// 递归调用自己,尝试再次获取可用连接
				con = getConnection();
			}
		} else if (maxConn == 0 || checkedOut < maxConn) {
			con = newConnection();
		}
		if (con != null) {
			checkedOut++;
		}
		return con;
	}

	/**
	 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
	 * 
	 * @param timeout
	 *            以毫秒计的等待时间限制
	 */
	public synchronized Connection getConnection(long timeout) {
		long startTime = new Date().getTime();
		Connection con;
		while ((con = getConnection()) == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
			}
			if ((new Date().getTime() - startTime) >= timeout) {
				// wait()返回的原因是超时
				return null;
			}
		}
		return con;
	}

	/**
	 * 关闭所有连接
	 */
	public synchronized void release() {
		Enumeration allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				System.out.println("关闭连接池" + name + "中的一个连接");
			} catch (SQLException e) {
				System.out.println(e + "无法关闭连接池" + name + "中的连接");
			}
		}
		freeConnections.removeAllElements();
	}

	/**
	 * 创建新的连接
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,User,pwd);
			System.out.println("连接池" + name + "创建一个新的连接");
		} catch (SQLException e) {
			System.out.println(e + "无法创建下列URL的连接: " + URL);
			return null;
		}
		return con;
	}
	
	


}
