package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="content",strict = false)
public class Content {

    @Attribute(name="type")
    private String type;

    @Text
    private String value;

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getValue(){return this.value;}

    public void setValue(String value){
        this.value = value;
    }
}
