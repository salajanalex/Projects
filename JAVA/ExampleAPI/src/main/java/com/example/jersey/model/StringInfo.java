package com.example.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringInfo {
    private String string;
    private int length;

//    public StringInfo(String string, int length) {
//        this.string = string;
//        this.length = length;
//    }
//
//    public StringInfo(String string) {
//        this.string = string;
//        this.length = 0;
//    }

    public void setString(String s) {
        this.string = s;
    }

    public String getString() {
        return this.string;
    }

    public void setLength(int l) {
        this.length = l;
    }

    public int getLength() {
        return this.length;
    }
}
