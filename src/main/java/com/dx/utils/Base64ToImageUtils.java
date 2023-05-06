package com.dx.utils;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * @author 32825
 */
public class Base64ToImageUtils {
    static String finalPath;
    public static String generateImage(String base64, String path) {
        // 解密
        try {
            String savePath = "D:/桌面/";
            // 图片分类路径+图片名+图片后缀
            String imgClassPath = path.concat(UUID.randomUUID().toString()).concat(".jpg");
            // 去掉base64前缀 data:image/jpeg;base64,
            base64 = base64.substring(base64.indexOf(",", 1) + 1);
            // 解密，解密的结果是一个byte数组
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] imgbytes = decoder.decode(base64);
            for (int i = 0; i < imgbytes.length; ++i) {
                if (imgbytes[i] < 0) {
                    imgbytes[i] += 256;
                }
            }
            finalPath=savePath.concat(imgClassPath);
            // 保存图片
            OutputStream out = new FileOutputStream(finalPath);
            out.write(imgbytes);
            out.flush();
            out.close();
            // 返回图片的相对路径 = 图片分类路径+图片名+图片后缀
            return finalPath;
        } catch (IOException e) {
            return null;
        }
    }
    public static String getImgFileToBase642(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] buffer = null;
        //读取图片字节数组
        try(InputStream inputStream = new FileInputStream(imgFile);){
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encode = Base64.getEncoder();
        return encode.encodeToString(buffer);
    }
}
