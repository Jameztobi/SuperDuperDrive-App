package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {

    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private  Integer userid;


    public Notes(){

    }

    public Notes(Integer noteid, String notetite, String notedescription, Integer userid) {
        this.noteid = noteid;
        this.notetitle = notetite;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Notes(String notetite, String notedescription, Integer userid) {
        this.notetitle = notetite;
        this.notedescription = notedescription;
        this.userid = userid;
    }



    public Integer getNoteid() {
        return noteid;
    }

    public String getNotetite() {
        return notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }
    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
