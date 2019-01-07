package com.bright.dev.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */
public class OssUtils {

    //标准存储
    /*private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESSKEYID = "LTAI7ADMgI3DFXl7";
    private static final String ACCESSKEYSECRET = "59iGdquvrNJFfthcsxEBmWSJ50pZDD";
    private static final String BUCKET = "hzjpdcm4chee-test";*/

    //归档储存
    private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESSKEYID = "LTAIvNw9k2i0FlMy";
    private static final String ACCESSKEYSECRET = "8cMJjaKIR9LnkyXx2sW5kWmGHPO6QH";
    private static final String BUCKET = "hzjpdcm4chee-archive";

    private static OSSClient client = null;

    // 创建OSSClient实例
    private static OSSClient getOssClient(){
        client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        return client;
    }

    // 关闭OSSClient
    private static void close(){
        OssUtils.getOssClient().shutdown();
    }


    /**
     * 上传文件
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key	文件的唯一标识
     * @param file 要上传的文件
     */
    public static PutObjectResult uploadDcm(String bucketName, String key, File file) {
        PutObjectResult pr = new PutObjectResult();
        if (bucketName == null || "".equals(bucketName)) {
            pr = OssUtils.getOssClient().putObject(BUCKET, key, file);
        } else {
            pr = OssUtils.getOssClient().putObject(bucketName, key, file);
        }
        OssUtils.close();
        return pr;
    }

    /**
     * 上传DCM文件和创建文件夹
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key	文件的唯一标识
     * @param file 要上传的文件
     */
    public static PutObjectResult uploadDcmEx(String bucketName,String StudyInsUID,String SeriesInsUID, String key, File file) {
        PutObjectResult pr = new PutObjectResult();
        if (bucketName == null || "".equals(bucketName)) {
            pr = OssUtils.getOssClient().putObject(BUCKET, StudyInsUID+"/"+SeriesInsUID+"/"+key, file);
        } else {
            pr = OssUtils.getOssClient().putObject(bucketName, StudyInsUID+"/"+SeriesInsUID+"/"+key, file);
        }
        OssUtils.close();
        return pr;
    }
    /**
     * 下载文件
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key	文件的唯一标识
     * @param file 下载到指定的本地文件
     */
    public static void downloadDcm(String bucketName, String key, File file){
        if(bucketName == null || "".equals(bucketName)){
            OssUtils.getOssClient().getObject(new GetObjectRequest(BUCKET, key), file);
        }else{
            OssUtils.getOssClient().getObject(new GetObjectRequest(bucketName, key), file);
        }
        OssUtils.close();
    }

    /**
     * 下载文件(解冻加下载)
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key	文件的唯一标识
     * @param file 下载到指定的本地文件
     */
    public static void downloadDcmEx(String bucketName,String StudyInsUID,String SeriesInsUID, String key, File file){
        try {
            if (bucketName == null || "".equals(bucketName)) {
                OSSClient ossClient = OssUtils.getOssClient();
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(BUCKET, StudyInsUID+"/"+SeriesInsUID+"/"+key);
// check whether the object is archive class
                StorageClass storageClass = objectMetadata.getObjectStorageClass();
                if (storageClass == StorageClass.Archive) {
                    // restore object
                    ossClient.restoreObject(BUCKET,StudyInsUID+"/"+SeriesInsUID+"/"+ key);
                    // wait for restore completed
                    do {
                        Thread.sleep(1000);
                        objectMetadata = ossClient.getObjectMetadata(BUCKET,StudyInsUID+"/"+SeriesInsUID+"/"+ key);
                    } while (!objectMetadata.isRestoreCompleted());
                }
// get restored object
                OSSObject ossObject = ossClient.getObject(BUCKET, StudyInsUID+"/"+SeriesInsUID+"/"+key);
                ossObject.getObjectContent().close();
                ossClient.getObject(new GetObjectRequest(BUCKET, StudyInsUID+"/"+SeriesInsUID+"/"+key), file);
            } else {
                OSSClient ossClient = OssUtils.getOssClient();
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(bucketName, StudyInsUID+"/"+SeriesInsUID+"/"+key);
// check whether the object is archive class
                StorageClass storageClass = objectMetadata.getObjectStorageClass();
                if (storageClass == StorageClass.Archive) {
                    // restore object
                    ossClient.restoreObject(bucketName,StudyInsUID+"/"+SeriesInsUID+"/"+ key);
                    // wait for restore completed
                    do {
                        Thread.sleep(1000);
                        objectMetadata = ossClient.getObjectMetadata(bucketName,StudyInsUID+"/"+SeriesInsUID+"/"+ key);
                    } while (!objectMetadata.isRestoreCompleted());
                }
// get restored object
                OSSObject ossObject = ossClient.getObject(bucketName, StudyInsUID+"/"+SeriesInsUID+"/"+key);
                ossObject.getObjectContent().close();
                ossClient.getObject(new GetObjectRequest(bucketName,StudyInsUID+"/"+SeriesInsUID+"/"+ key), file);
            }
            OssUtils.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  static boolean GetFileState(String bucketName,String StudyInsUID,String SeriesInsUID, String key) {
        StorageClass storageClass = null;
        try {
            if (bucketName == null || "".equals(bucketName)) {
                OSSClient ossClient = OssUtils.getOssClient();
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(BUCKET, StudyInsUID + "/" + SeriesInsUID + "/" + key);
// check whether the object is archive class
                storageClass = objectMetadata.getObjectStorageClass();
                if (storageClass == StorageClass.Archive)
                    return objectMetadata.isRestoreCompleted();
                else
                    return false;

            } else {
                OSSClient ossClient = OssUtils.getOssClient();
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(bucketName, StudyInsUID + "/" + SeriesInsUID + "/" + key);
// check whether the object is archive class
                storageClass = objectMetadata.getObjectStorageClass();
                if (storageClass == StorageClass.Archive)
                    return objectMetadata.isRestoreCompleted();
                else
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除单个文件
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key	文件的唯一标识
     */
    public static void deleteDcm(String bucketName, String key){
        if(bucketName == null || "".equals(bucketName)){
            OssUtils.getOssClient().deleteObject(BUCKET, key);
        }else{
            OssUtils.getOssClient().deleteObject(bucketName, key);
        }
        OssUtils.close();
    }

    /**
     * 删除多个文件
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param keys 文件的唯一标识
     */
    public static void deleteMultiDcms(String bucketName, List<String> keys){
        DeleteObjectsResult deleteObjectsResult = null;
        if(bucketName == null || "".equals(bucketName)){
            deleteObjectsResult = OssUtils.getOssClient().deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(keys));
        }else{
            deleteObjectsResult = OssUtils.getOssClient().deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        }
        deleteObjectsResult.getDeletedObjects();
        OssUtils.close();
    }

    /**
     *  修改上传文件（先删除，然后上传）——不需要此方法，上传时会根据key值覆盖对应的文件
     * @param bucketName 存储空间名称（若为空，则会按照本类中的变量BUCKET的值）
     * @param key 文件的唯一标识
     * @param file 要重新上传的文件
     */
    @Deprecated
    public static void reuploadDcm(String bucketName, String key, File file){
        if(bucketName == null || "".equals(bucketName)){
            OssUtils.deleteDcm(BUCKET, key);
            OssUtils.uploadDcm(BUCKET, key, file);
        }else{
            OssUtils.deleteDcm(bucketName, key);
            OssUtils.uploadDcm(bucketName, key, file);
        }
    }

}