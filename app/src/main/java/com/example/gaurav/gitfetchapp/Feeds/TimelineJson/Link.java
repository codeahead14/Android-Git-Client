package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Element;

/**
 * Created by GAURAV on 09-08-2016.
 */
public class Link {

    @Element(name = "type")
    private String type;

    @Element(name = "ref")
    private String ref;

    @Element(name = "href")
    private String href;

    public String getType() {
     return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getRef(){
        return this.ref;
    }

    public void setRef(String ref){
        this.ref = ref;
    }

    public String getHref(){
        return this.href;
    }

    public void setHref(String href){
        this.href = href;
    }
}
