package com.example.hi.data;

public class English_ETC4_Factor {
    private int id;
    private String wordHead;//英文
    private String usphone;//音标
    private String spos;//词性
    private String stran;//词性中文

    private String descj;//同近
    private String w;//近义词

    private String desj;//例句
    private String sContent;//例句英文
    private String sCn;//例句中文

    private String tranOther;//其他

    public English_ETC4_Factor(int id, String wordHead, String usphone, String spos, String stran, String descj, String w, String desj, String sContent, String sCn, String tranOther) {
        this.id = id;
        this.wordHead = wordHead;
        this.usphone = usphone;
        this.spos = spos;
        this.stran = stran;
        this.descj = descj;
        this.w = w;
        this.desj = desj;
        this.sContent = sContent;
        this.sCn = sCn;

        this.tranOther = tranOther;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordHead() {
        return wordHead;
    }

    public void setWordHead(String wordHead) {
        this.wordHead = wordHead;
    }

    public String getUsphone() {
        return usphone;
    }

    public void setUsphone(String usphone) {
        this.usphone = usphone;
    }

    public String getSpos() {
        return spos;
    }

    public void setSpos(String spos) {
        this.spos = spos;
    }

    public String getStran() {
        return stran;
    }

    public void setStran(String stran) {
        this.stran = stran;
    }

    public String getDescj() {
        return descj;
    }

    public void setDescj(String descj) {
        this.descj = descj;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getDesj() {
        return desj;
    }

    public void setDesj(String desj) {
        this.desj = desj;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public String getsCn() {
        return sCn;
    }

    public void setsCn(String sCn) {
        this.sCn = sCn;
    }

    public String getTranOther() {
        return tranOther;
    }

    public void setTranOther(String tranOther) {
        this.tranOther = tranOther;
    }
}
