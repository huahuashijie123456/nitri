package com.fuhuitong.nitri.common.utils.sms;


import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SendSms {



    public static void main(String []args){
       //String str=juhesendMsg("1322774739","179997");
        //System.out.println(str);

    }

    public static String sendMsg(String mobile){
        String number = String
                .valueOf(new Random().nextInt(899999) + 100000);
        String url="https://sms.yunpian.com/v2/sms/single_send.json";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("apikey", "8501223859171618bb99f1b3077cdbe3");
        params.put("text", "您的验证码是"+number+"。有效期为10分钟，请尽快验证！");
        params.put("mobile", mobile);
        HttpUtil.post(url,params);
        return number;
    }

        //金融云短信验证码发送
    public static String juhesendMsg(String mobile,String tplid){
        //调用生成随机数方法
        String yzm = getFourRandom();
        String url="http://v.juhe.cn/sms/send";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("key", "81f6c2839dd5e3ca8594707b262ecdec");
        //设置验证码 以及 验证码时间
        params.put("tpl_value", "%23code%23%3d"+yzm+"&%23m%23%3d"+10);
        params.put("mobile", mobile);
        params.put("tpl_id",tplid);
        HttpUtil.post(url,params);
        return yzm;
    }

    //生成随机码一个4位纯数字随机验证码
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(1000000) + "";
        int randLength = fourRandom.length();
        if(randLength<6){
            for(int i=1; i<=6-randLength; i++)
                fourRandom = "0" + fourRandom ;
        }
        return fourRandom;
    }

}
