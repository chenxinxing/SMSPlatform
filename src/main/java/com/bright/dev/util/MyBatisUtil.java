package com.bright.dev.util;

import java.io.InputStream;

import org.apache.ibatis.io.ResolverUtil.Test;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/**
 * 
 * @Description: TODO(MyBatisUtil) 
 * @author 蒋秉笙
 * @version V1.0   
 * @date 2017年12月29日
 *
 */
public class MyBatisUtil {
	private static SqlSessionFactory factory;
	static {
		String resource = "mybatis-config.xml";
		InputStream is = Test.class.getClassLoader().getResourceAsStream(
				resource);
		factory = new SqlSessionFactoryBuilder().build(is);
	}
	
	public static SqlSession createSession() {
		return factory.openSession();// 需要手动提交
	}
	
	public static void closeSession(SqlSession session) {
		if(session!=null) session.close();
	}
}
