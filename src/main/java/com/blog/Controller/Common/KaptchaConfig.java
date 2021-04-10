package com.blog.Controller.Common;



import com.google.code.kaptcha.impl.DefaultKaptcha;

import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha (){
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        //  边框设置
//        properties.put("Kaptcha.border","no");
//        //  字体颜色
//        properties.put("Kaptcha.textproducer.font.color","red");
//        //  width
//        properties.put("Kaptcha.image.width","150");
//        // height
//        properties.put("Kaptcha.image.height","40");
//        //font size
//        properties.put("Kaptcha.textproducer.font.size","30");
//        // kaptcha length
//        properties.put("Kaptcha.textproducer.char.space","6");
//        // font
////        properties.setProperty("Kaptcha.textproducer.font.names","宋体,楷体微软,雅黑");
//        // set verifyCode
//        properties.put("kaptcha.session.key", "verifyCode");
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.image.width", "150");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.session.key", "verifyCode");
        properties.put("kaptcha.textproducer.char.space", "5");
        Config config =new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
