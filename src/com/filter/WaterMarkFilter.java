package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ki264 on 2017/6/22.
 */
public class WaterMarkFilter implements Filter {

    //浮水印圖片檔案的路徑，設定在初始化參數中
    private String waterMarkFile;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //自訂的HttpServletResponseWrapper類別物件
        WaterMarkResponseWrapper waterMarkResponseWrapper = new WaterMarkResponseWrapper(response, waterMarkFile);

        chain.doFilter(request, waterMarkResponseWrapper);

        //加上浮水印，並輸出到客戶端瀏覽器
        waterMarkResponseWrapper.finishResponse();

    }

    public void init(FilterConfig config) throws ServletException {
        String file = config.getInitParameter("waterMarkFile");
        waterMarkFile = config.getServletContext().getRealPath(file);

    }

}
