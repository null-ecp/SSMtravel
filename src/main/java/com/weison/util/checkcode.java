package com.weison.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 * 用于生成和检测
 */
public class checkcode {

    // 类加载时初始化取值集合
    private static String list = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
    // 设置成静态私有 用于获取
    private static String code = "";

    /**
     * 用于生成验证码和获取验证码字符串
     * @param bimg 传入需要生成的图片缓存
     */
    public static void getcheckcode(BufferedImage bimg){
        // 获取画笔来操作图片缓存
        Graphics graphics = bimg.getGraphics();
        // 给画笔设置颜色 , 绘制背景色
        graphics.setColor(Color.GRAY);
        // 填充背景色
        graphics.fillRect(0, 0, bimg.getWidth(), bimg.getHeight());
        // 设置画笔颜色 , 画边框
        graphics.setColor(Color.white);
        graphics.drawRect(0, 0, bimg.getWidth() - 1, bimg.getWidth() - 1);
        // 设置颜色 , 画字符串
        graphics.setColor(Color.BLUE);
        // 设置绘制的字体
        graphics.setFont(new Font("宋体",3,20));
        // 设置随机数获取字符
        code = "";
        Random ran = new Random();
        for (int i = 1; i < 5; i++) {
            int index = ran.nextInt(list.length());
            char c = list.charAt(index);
            // 设置随机大小写
            Random loorhi = new Random();
            String str = null;
            if (loorhi.nextBoolean()) {
                str = (c + "").toLowerCase();
            } else {
                str = (c + "").toUpperCase();
            }
            code += str;
            graphics.drawString(str, bimg.getWidth()/5*i, bimg.getHeight()/5*3);
        }
        // 绘制简单的干扰线
        // 设置干扰线颜色
        graphics.setColor(Color.darkGray);
        // 随机设置干扰线数量10-15根
        for (int i = 0; i < new Random().nextInt(5)+10; i++) {
            // 随机生成起始位置和最终位置
            Random r = new Random();

            int x1 = r.nextInt(bimg.getWidth());
            int x2 = r.nextInt(bimg.getWidth());

            int y1 = r.nextInt(bimg.getHeight());
            int y2 = r.nextInt(bimg.getHeight());

            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 检测用户输入是否匹配
     * @param inputcode 输入项
     * @param checkcode 验证码
     * @return boolean 匹配结果
     */
    public static boolean checkinput(String inputcode, String checkcode){
        return inputcode.toLowerCase().equals(checkcode.toLowerCase())?true:false;
    }

    /**
     * 获取验证码字符串
     * @return code
     */
    public static String getCode(){
        return code;
    }
}