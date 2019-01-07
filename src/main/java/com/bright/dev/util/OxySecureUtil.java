package com.bright.dev.util;

import com.xiaoleilu.hutool.crypto.SecureUtil;


/**
 * 
 * @Description: TODO(加盐 md5) 
 * @author 蒋秉笙
 * @version V1.0   
 * @date 2017年12月29日
 *
 */
public class OxySecureUtil {

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
