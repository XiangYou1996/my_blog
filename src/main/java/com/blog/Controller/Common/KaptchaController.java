package com.blog.Controller.Common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Autowired
    DefaultKaptcha getKaptcha;

    @GetMapping("/Common/kaptcha")
    public void getDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        byte[] kaptchaOutStream = null;
        ByteArrayOutputStream imgOutStream = new ByteArrayOutputStream();
        //生成验证码存入session
        String verifyCode = getKaptcha.createText();
        httpServletRequest.getSession().setAttribute("verifyCode",verifyCode);
        BufferedImage bufferedImage = getKaptcha.createImage(verifyCode);
        ImageIO.write(bufferedImage,"jpg",imgOutStream);
        //将图片送入Session
        kaptchaOutStream =imgOutStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control","no-store");
        httpServletResponse.setHeader("Pragma","no-cache");
        httpServletResponse.setDateHeader("Expires",0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(kaptchaOutStream);
        responseOutputStream.flush();
        responseOutputStream.close();

    }
}
