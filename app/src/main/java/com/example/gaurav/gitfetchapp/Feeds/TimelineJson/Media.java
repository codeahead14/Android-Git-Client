package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Element;

/**
 * Created by GAURAV on 09-08-2016.
 */
public class Media {

    @Element(name="height")
    private String height;

    @Element(name="width")
    private String width;

    @Element(name="url")
    private String url;

    public void setHeight(String height){
        this.height = height;
    }

    public String getHeight(){
        return this.height;
    }

    public void setWidth(String width){
        this.width = width;
    }

    public String getWidth(){
        return this.width;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
