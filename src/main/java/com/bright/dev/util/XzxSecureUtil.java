package com.bright.dev.util;

import com.xiaoleilu.hutool.crypto.SecureUtil;


/**
 * 
 * @Description: TODO(加盐加密) 
 * @author 蒋秉笙
 * @version V1.0   
 * @date 2017年12月28日
 *
 */
public class XzxSecureUtil {

	public static void main(String[] args) {
        String salt = salt();
        System.out.println("salt:"+salt);
        String pwd = encryptPassword("123",salt);
        System.out.println("pwd:"+pwd);
    }
    /**
     * 加盐
     * @return
     */
    public static String salt() {
        return SecureUtil.simpleUUID();
    }


    /**
     * 加密
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        return SecureUtil.sha1(password + salt);
    }

}
