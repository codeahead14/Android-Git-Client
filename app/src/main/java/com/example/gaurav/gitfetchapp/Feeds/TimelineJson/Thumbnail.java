package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="thumbnail",strict=false)
public class Thumbnail {

    @Attribute(name="height")
    private String height;

    @Attribute(name="width")
    private String width;

    @Attribute(name="url")
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
