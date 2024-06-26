package com.sun.controller;

import com.google.code.kaptcha.Producer;
import com.sun.security.CaptchaFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @date 2024/6/28
 */

@Controller
public class CaptchaController {

    @Resource
    private Producer captcha;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置内容类型
        response.setContentType("image/jpeg");
        // 创建验证码文本
        String capText = captcha.createText();

        // 将验证码文本设置到session
        request.getSession().setAttribute(CaptchaFilter.SECURITY_FORM_CAPTCHA_KEY, capText);

        // 创建验证码图片
        BufferedImage bi = captcha.createImage(capText);
        // 获取响应输出流
        ServletOutputStream out = response.getOutputStream();
        // 将图片验证码数据写到响应输出流
        ImageIO.write(bi, "jpg", out);

        // 推送并关闭响应输出流
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
