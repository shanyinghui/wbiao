package com.wbiao.util;

import java.io.Serializable;

public class FastdfsFile implements Serializable {
    private String fileName; //文件名字
    private byte[] content; //文件内容
    private String ext; //文件扩展名
    private String MD5; //文件MD5摘要值
    private String auther; //文件作者

    public FastdfsFile(String fileName, byte[] content, String ext) {
        this.fileName = fileName;
        this.content = content;
        this.ext = ext;
    }

    public FastdfsFile(String fileName, byte[] content, String ext, String MD5, String auther) {
        this.fileName = fileName;
        this.content = content;
        this.ext = ext;
        this.MD5 = MD5;
        this.auther = auther;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }
}
