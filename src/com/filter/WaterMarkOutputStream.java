package com.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Created by ki264 on 2017/6/22.
 */
public class WaterMarkOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream byteArrayOutputStream;

    public WaterMarkOutputStream() throws IOException {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void close() throws IOException {
        byteArrayOutputStream.close();
    }

    @Override
    public void flush() throws IOException {
        byteArrayOutputStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        byteArrayOutputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        byteArrayOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        byteArrayOutputStream.write(b, off, len);
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
