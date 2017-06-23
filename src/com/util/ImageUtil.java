package com.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Created by ki264 on 2017/6/22.
 */
public class ImageUtil {
    public static byte[] waterMark(byte[] imageDate, String waterMarkFile) throws IOException {

        int paddingRight = 10; //浮水印圖片的右邊距
        int paddingBottom = 10;//浮水印圖片的下邊距


        Image image = new ImageIcon(imageDate).getImage();//原始影像
        int imageWidth = image.getWidth(null);//原始圖片的寬度
        int imageHeight = image.getHeight(null);//原始圖片的高度

        Image waterMark = ImageIO.read(new File(waterMarkFile));//浮水印圖片
        int waterMarkWidth = waterMark.getWidth(null);//浮水印圖片的寬度
        int waterMarkHeight = waterMark.getHeight(null);//浮水印圖片的高度

        //如果圖片尺寸過小，則不打浮水印，直接傳回
        if (imageWidth < waterMarkWidth + 2 * paddingRight || imageHeight < waterMarkHeight + 2 * waterMarkHeight) {
            return imageDate;
        }

        //影像緩衝區
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //繪圖用的Graphics物件
        Graphics graphics = bufferedImage.createGraphics();
        //繪製原始圖片
        graphics.drawImage(image, 0, 0, imageWidth, imageHeight, null);
        //繪製浮水印圖片
        graphics.drawImage(waterMark,
                imageWidth - waterMarkWidth - paddingRight,
                imageHeight - waterMarkHeight - paddingBottom,
                waterMarkWidth, waterMarkHeight,
                null);
        graphics.dispose();

        //輸出流，快取記憶體資料
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //轉成JPG圖
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //轉換
        encoder.encode(bufferedImage);
        //輸出成二進位
        byte[] data = out.toByteArray();
        out.close();

        return data;
    }
}
