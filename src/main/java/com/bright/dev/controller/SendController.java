package com.bright.dev.controller;

import com.bright.dev.entity.SendList;
import com.bright.dev.util.SmsDemo;
import io.swagger.models.Model;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Bright
 * @Date: 2018/11/30 12:25
 */
@Component
@Controller
@RequestMapping(value = "/")
public class SendController {


    /**
     * @return
     */
    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView send(@ModelAttribute("sendlist") SendList sendlist, HttpSession session) {
        ModelAndView mode = new ModelAndView();

        int error = -1;
        int templateId = 238156;
        String smsSign = "素颜三部曲一麦吉丽";
        if (sendlist.getSendTo() != null && !"".equals(sendlist.getSendTo()) && sendlist.getCourierName() != null && !"".equals(sendlist.getCourierName())
                && sendlist.getAddress() != null && !"".equals(sendlist.getAddress()) && sendlist.getAuthCode() != null && !"".equals(sendlist.getAuthCode())) {
            String[] param = new String[]{sendlist.getCourierName(), sendlist.getAddress(), sendlist.getAuthCode()};
            //error = SmsDemo.sendUtil(sendlist.getSendTo(), templateId, smsSign, param);
            error = 0;
        }
        if (error != 0 || error == -1) {
            session.setAttribute("error","发送失败，错误代码是："+error);
            mode.addObject("error","发送失败，错误代码是："+error);
            mode.setViewName("register");
            return mode;
        } else{
            session.setAttribute("error","发送成功！！");
            mode.addObject("error","发送成功！！");
            mode.setViewName("register");
            return mode;
        }
    }
}
