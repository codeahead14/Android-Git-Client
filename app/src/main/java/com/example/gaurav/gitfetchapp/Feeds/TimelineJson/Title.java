package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="title",strict=false)
public class Title {

    @Attribute(name="html")
    private String html;

    @Text
    private String titleValue;

    public String getHtml(){
        return this.html;
    }

    public String getTitleValue(){
        return this.titleValue;
    }
}
