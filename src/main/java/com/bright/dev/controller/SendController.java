package com.bright.dev.controller;

import com.alibaba.fastjson.JSONObject;
import com.bright.dev.entity.Consumer;
import com.bright.dev.entity.SendList;
import com.bright.dev.service.ConsumerService;
import com.bright.dev.service.SendListService;
import com.bright.dev.util.IdGenerator;
import com.bright.dev.util.SmsDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Bright
 * @Date: 2018/11/30 12:25
 */
@Component
@Controller
@RequestMapping(value = "/")
public class SendController {

    @Autowired
    private SendListService sendListService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private IdGenerator id;

    /**
     * @return
     */
    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    @ResponseBody
    public String send(@RequestParam("sendTo") String sendTo, @RequestParam("courierName") String courierName,
                       @RequestParam("authCode") String authCode, HttpSession session) {
        int error = -1;
        try {
            if (session.getAttribute("loginFlag") != null) {
                int templateId = 242825;
                String smsSign = "素颜三部曲一麦吉丽";
                if (sendTo != null && !"".equals(sendTo) && courierName != null && !"".equals(courierName)
                        && authCode != null && !"".equals(authCode)) {
                    String[] param = new String[]{courierName, authCode};
                    error = SmsDemo.sendUtil(sendTo, templateId, smsSign, param);
                    //error = 0;
                    String smsContent = "【素颜三部曲一麦吉丽】您的 " + courierName + " 包裹已到曹集东头张敏超市斜对面王杰装饰，请尽快来取，取件码是" + authCode + "，请联系电话18800394093.";
                    SendList sendList = new SendList();
                    sendList.setListId(String.valueOf(id.nextId()));
                    sendList.setSendStatus(String.valueOf(error));

                    String format = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat df = new SimpleDateFormat(format);
                    Consumer consumer = (Consumer) session.getAttribute("loginFlag");

                    sendList.setSendTime(df.format(new Date()));
                    sendList.setSendTo(sendTo);
                    sendList.setUserId(consumer.getUserName());
                    sendList.setSmsContent(smsContent);
                    int i = sendListService.insert(sendList);
                    if (i < 0)
                        error = -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (error != 0 || error == -1) {
            //session.setAttribute("error","发送失败，错误代码是："+error);
            return "fail";
        } else {
            //session.setAttribute("error","发送成功！！");
            return "success";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(value = "username") String userName, @RequestParam(value = "password") String password, HttpSession session) {
        int error = -1;
        try {
            Consumer consumer = consumerService.selectUser(userName, password);
            if (consumer != null) {
                session.setAttribute("loginFlag", consumer);
                error = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (error != 0 || error == -1) {
            //session.setAttribute("error","发送失败，错误代码是："+error);
            return "fail";
        } else {
            //session.setAttribute("error","发送成功！！");
            return "success";
        }
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.POST)
    @ResponseBody
    public String getList(HttpSession session) {
        String data = "";
        List<SendList> sendList = null;
        try {
            Consumer consumer = (Consumer) session.getAttribute("loginFlag");
            sendList = sendListService.selectByUserId(consumer.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sendList.size() > 0){
            data = JSONObject.toJSONString(sendList);
        }
        return data;
    }

    @RequestMapping(value = "/getsinglelist", method = RequestMethod.POST)
    @ResponseBody
    public String getSingleList(@RequestParam(value = "varue") String phone, HttpSession session) {
        String data = "";
        List<SendList> sendList = null;
        try {
            Consumer consumer = (Consumer) session.getAttribute("loginFlag");
            if (phone!=null && !"".equals(phone))
            sendList = sendListService.getSinglList(consumer.getUserName(),phone+"%");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sendList.size() > 0){
            data = JSONObject.toJSONString(sendList);
        }
        return data;
    }
}
