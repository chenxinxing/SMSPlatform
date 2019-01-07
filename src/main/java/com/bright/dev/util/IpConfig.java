package com.bright.dev.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
/**
 * 
 * @author 秉笙
 *
 * @date 2018年5月24日 上午10:16:34
 */
public class IpConfig {
	public static String getHostIp(){  
	    try{  
	        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();  
	        while (allNetInterfaces.hasMoreElements()){  
	            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();  
	            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();  
	            while (addresses.hasMoreElements()){  
	                InetAddress ip = (InetAddress) addresses.nextElement();  
	                if (ip != null   
	                        && ip instanceof Inet4Address  
	                        && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255  
	                        && ip.getHostAddress().indexOf(":")==-1){  
	                    System.out.println("本机的IP = " + ip.getHostAddress());  
	                    return ip.getHostAddress();  
	                }   
	            }  
	        }  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    }  
	    return "127.0.0.1";  
	}  
}
