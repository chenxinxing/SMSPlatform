package com.bright.dev.util;

import java.io.File;
import java.util.LinkedList;
/**
 * 
 * @author 秉笙
 *
 * @date 2018年5月24日 上午10:15:51
 */
public class RefreshFileList {
	public int shul(String path){
    	int fileNum = 0;
    	int folderNum = 0;
    	File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                   // System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                   // System.out.println("文件:" + file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                	
                    if (file2.isDirectory()) {
                        /*System.out.println("文件夹:" + file2.getAbsolutePath());
                        System.out.println("文件夹:" + file2.getName());*/
                        list.add(file2);
                        folderNum++;
                    } else {
                       // System.out.println("文件:" + file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
		return fileNum;

    }
	}
