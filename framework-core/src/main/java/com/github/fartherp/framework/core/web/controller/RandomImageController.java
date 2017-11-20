/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * <pre>
 * &lt;input type="text" name="validateCode" class="validateCodeInput" /&gt;
 * &lt;img src="&lt;c:url value="/"/&gt;validateCode/image" title="点击刷新验证码" onclick="this.src='&lt;c:url value="/"/&gt;validateCode/image?d='+Math.random()"/&gt;
 * </pre>
 * Author: CK
 * Date: 2015/8/17
 */
@Controller
@RequestMapping(value = "/validateCode")
public class RandomImageController {

    public static final String LOGIN_VALIDATE_STRING = RandomImageController.class.getName() + "_LOGIN_VALIDATE_STRING";

    private static final String randomString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 图片上的字符串

    @RequestMapping(value = "/image")
    public void createImage(HttpServletRequest req, HttpServletResponse resp) {
        ImageOutputStream ios = null;
        try {
            // 生成100px*22px的包含6个字符的验证码
            RandomImage validateImage = new RandomImage(6, 100, 22);
            // Set to expire far in the past.
            resp.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            resp.setHeader("Pragma", "no-cache");
            // return a jpeg
            resp.setContentType("image/jpeg");

            ios = ImageIO.createImageOutputStream(resp.getOutputStream());
            ImageIO.write(validateImage.getValidateImage(), "JPEG", ios);
            req.getSession().setAttribute(LOGIN_VALIDATE_STRING, validateImage.getValidateString());
        } catch (IOException e) {
            // ignore
        } finally {
            try {
                ios.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public static class RandomImage {
        private String validateString; // 生成的验证字符串
        private int length; // 图片上字符的个数
        private int width; // 图片的宽度
        private int height; // 图片的高度

        public RandomImage(int length, int width, int height) {
            this.length = length;
            this.width = width;
            this.height = height;
        }

        // 获取生成的验证字符串
        public String getValidateString() {
            if (validateString == null) {
                getValidateImage();
            }
            return validateString;
        }

        // 获取生成的验证图片
        public BufferedImage getValidateImage() {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 在内存中创建图象
            Graphics2D raphics = (Graphics2D) image.getGraphics();// 获取图形上下文
            raphics.setColor(new Color(200, 200, 0));// 设定为白色背景色
            raphics.fillRect(0, 0, width, height);
            raphics.setFont(new Font("Times New Roman", Font.ITALIC, 18));// 设定字体
            // style:HANGING_BASELINE
            Random random = new Random(); // 生成随机类
            // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
            for (int i = 0; i < 305; i++) {
                raphics.setColor(getRandColor(160, 200));// 给定范围获得随机颜色
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                raphics.drawLine(x, y, x + xl, y + yl);
            }
            // 取随机产生的认证码(length位数字)
            StringBuilder v = new StringBuilder();
            for (int i = 0; i < length; i++) {
                String rand = String.valueOf(randomString.charAt(random.nextInt(randomString.length())));
                v.append(rand);
                raphics.setColor(Color.BLACK);// 设置为黑色字体
                // raphics.rotate(0.01,20,20);
                raphics.drawString(rand, 15 * i + 10, 15);
            }
            validateString = v.toString(); // 将认证码存入 validateString
            raphics.dispose(); // 图象生效
            return image;
        }

        private Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
            Random random = new Random();
            if (fc > 255)
                fc = 255;
            if (bc > 255)
                bc = 255;
            int r = fc + random.nextInt(bc - fc);
            int g = fc + random.nextInt(bc - fc);
            int b = fc + random.nextInt(bc - fc);
            return new Color(r, g, b);
        }
    }
}
