package com.weison.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库工具类
 */
public class JDBCUtils {
	// 声明静态数据源成员变量
	private static DataSource ds;

	/**
	 * 初始化 加载配置
	 */
	static {
		// 加载配置文件中的数据
		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
		Properties pro = new Properties();
		try {
			pro.load(is);
			// 创建连接池，使用配置文件中的参数
			ds = DruidDataSourceFactory.createDataSource(pro);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接池对象
	 * @return 数据库连接池对象
	 */
	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * 获取数据库连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

}
