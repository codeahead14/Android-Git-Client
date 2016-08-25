package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="author",strict=false)
public class Author {

    @Element(name="name")
    private String name;

    @Element(name="uri")
    private String uri;

    @Element(name="email",required=false)
    private String email;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUri(){
        return this.uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
