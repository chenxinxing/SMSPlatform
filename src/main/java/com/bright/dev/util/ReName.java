package com.bright.dev.util;

import java.io.File;

/**
 * 
 * @author 秉笙
 *
 * @date 2018年6月8日 上午9:42:36
 */
public class ReName {  
    public static void main(String[] args) {  
          
        //要改的文件夹路径  
        String path= "F:/2018";  
        getNew(path);  
          
    }  
    private static void getNew(String path) {  
        File file = new File(path);  
        //得到文件夹下的所有文件和文件夹  
        String[] list = file.list();  
          
        if(list!=null && list.length>0){  
            for (String oldName : list) {  
                File oldFile = new File(path,oldName);  
                //判断出文件和文件夹  
                if(!oldFile.isDirectory()){  
                    //文件则判断是不是要修改的  
                    if(oldName.contains("")){  
                        System.out.println(oldName);  
                        String newoldName = oldName.substring(0, oldName.lastIndexOf(""))+".dcm";  
                        System.out.println(newoldName);  
                        File newFile = new File(path,newoldName);  
                        boolean flag = oldFile.renameTo(newFile);  
                        System.out.println(flag);  
                    }  
                }else{  
                    //文件夹则迭代  
                    String newpath=path+"/"+oldName;  
                    getNew(newpath);  
                }  
            }  
        }  
    }  
}  