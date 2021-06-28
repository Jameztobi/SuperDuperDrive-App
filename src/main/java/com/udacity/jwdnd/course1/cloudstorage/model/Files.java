package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.InputStream;

public class Files {
    private Integer fileid;
    private long filesize;
    private byte[] filedata;
    private String filename;
    private String contenttype;
    private Integer userid;

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public Integer getFileid() {
        return fileid;
    }

    public String getFilename() {
        return filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public Integer getUserid() {
        return userid;
    }

    public long getFilesize() {
        return filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}


