package com.filter;

import com.util.ImageUtil;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by ki264 on 2017/6/22.
 */
public class WaterMarkResponseWrapper extends HttpServletResponseWrapper {

    private String waterMarkFile;//浮水印圖片的位置
    private HttpServletResponse response;//原response
    private WaterMarkOutputStream waterMarkOutputStream;//自訂的ServletOutputStream，用於緩衝圖像資料

    public WaterMarkResponseWrapper(HttpServletResponse response, String waterMarkFile) throws IOException {
        super(response);
        this.response = response;
        this.waterMarkFile = waterMarkFile;
        this.waterMarkOutputStream = new WaterMarkOutputStream();

    }

    /**
     * 覆寫此方法，傳回自訂的WaterMarkOutputStream物件
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return waterMarkOutputStream;
    }

    @Override
    public void flushBuffer() throws IOException {
        waterMarkOutputStream.flush();
    }

    /**
     * 將圖像資料增加浮水印並輸出到用戶端瀏覽器
     *
     * @throws IOException
     */
    public void finishResponse() throws IOException {
        //原圖片資料
        byte[] imageDate = waterMarkOutputStream.getByteArrayOutputStream().toByteArray();
        //增加浮水印後的圖片資料
        byte[] image = ImageUtil.waterMark(imageDate, waterMarkFile);

        response.setContentLength(image.length);//設定輸出內容的實際長度
        response.getOutputStream().write(image);//將影像輸出到瀏覽器

        waterMarkOutputStream.close();//關閉waterMarkOutputStream
    }
}
