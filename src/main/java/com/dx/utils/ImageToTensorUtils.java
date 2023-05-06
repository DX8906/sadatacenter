package com.dx.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 32825
 */
public class ImageToTensorUtils {
    public static float[][][][] getTensor(String picturePath) {
        BufferedImage bf = readImage(picturePath);
        try {
            bf= ResizeImageUtils.resizeImageOne(bf,240,240);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将图片转换为4维数组
        float[][][][] rgb4DArray = convert2DArrayTO3DArray(bf);
        return rgb4DArray;

    }
    public static BufferedImage readImage(String imageFile){
        File file = new File(imageFile);
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf;
    }

    public static float[][][][] convert2DArrayTO3DArray(BufferedImage bf) {
        // 获取图片宽度和高度
        int width = bf.getWidth();
        int height = bf.getHeight();
        int channel = 3;
        int r = 0;
        int g = 1;
        int b = 2;
        int[] data = new int[width*height];
        bf.getRGB(0, 0, width, height, data, 0, width);
        // 将二维数组转换为三维数组
        float[][][][] rgb4DArray = new float[1][height][width][channel];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb4DArray[0][i][j][r] = ((data[i*width + j] & 0xff0000) >> 16)/255.0F;
                rgb4DArray[0][i][j][g] = ((data[i*width + j] & 0xff00) >> 8)/255.0f;
                rgb4DArray[0][i][j][b] = (data[i*width + j] & 0xff)/255.0F;
            }
        }
        return rgb4DArray;
    }
}
