package com.bright.dev.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

/**
 * 发送模板短信请求
 *
 * @author liuxuanlin
 */
public class SmsDemo {

    // 短信应用SDK AppID  不要修改
    private static int appid = 1400163534; // 1400开头

    // 短信应用SDK AppKey  不要修改
    private static String appkey = "73acf04f34f8d7f583d6f044beab1ff1";


    public static Integer sendUtil(String phoneNumber,int templateid,String smsSigns,String[] param){
        int code = 0;
        String[] phoneNumbers = {phoneNumber}; // 需要发送短信的手机号码
        int templateId = templateid; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请,短信模板ID，需要在短信应用中申请
        String smsSign = smsSigns; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

        try {
            //String[] params = {"中通快递","曹集王杰装饰店内","3521456，测试短信"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            String[] params = param;//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            code = result.result;
        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }
        return code;
    }


    public static void main(String[] args) {
        // 需要发送短信的手机号码
        String[] phoneNumbers = {"15290002375"};
        // 短信模板ID，需要在短信应用中申请
        int templateId = 237517; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
        // 签名
        String smsSign = "素颜三部曲一麦吉丽"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

        try {
            String[] params = {"中通快递","曹集王杰装饰店内","3521456，测试短信"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "asdasd", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            /*
            * {"result":0,"errmsg":"OK","ext":"","sid":"8:xaGXuzdQD2xj4iYfuyW20181130","fee":2}  正常
            * {"result":1006,"errmsg":"\u8BF7\u6C42\u6CA1\u6709\u6743\u9650","ext":""} 错误的
            * */

        } catch (Exception e) {
            // HTTP响应码错误
            e.printStackTrace();
        }
    }

}