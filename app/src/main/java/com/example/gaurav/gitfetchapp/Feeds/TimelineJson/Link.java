package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="link",strict=false)
public class Link {

    @Attribute(name = "type")
    private String type;

    @Attribute(name = "rel")
    private String rel;

    @Attribute(name = "href")
    private String href;

    public String getType() {
     return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getRel(){
        return this.rel;
    }

    public void setRel(String rel){
        this.rel = rel;
    }

    public String getHref(){
        return this.href;
    }

    public void setHref(String href){
        this.href = href;
    }
}
