package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import android.widget.ListView;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by GAURAV on 09-08-2016.
 */

@Root(name="feed")
public class Feed {

    @Attribute(name="xmlns")
    private String xmlns;

    @Attribute(name="xmlns:media")
    private String xmlns_media;

    @Attribute(name="xml:lang")
    private String xml_lang;

    @Element(name="id")
    private String id;

    @ElementList(name="link",inline=true)
    private List<Link> links;

    @Element(name="title")
    private String title;

    @Element(name="updated")
    private String updated;

    @ElementList(name="Entry",inline=true)
    private List<Entry> entry;

    public String getXmlns(){
        return this.xmlns;
    }

    public void setXmlns(String xmlns){
        this.xmlns = xmlns;
    }

    public String getXmlns_media(){
        return this.xmlns_media;
    }

    public void setXmlns_media(){
        this.xmlns_media = xmlns_media;
    }

    public String getXml_lang(){
        return this.xml_lang;
    }

    public void setXml_lang(String xml_lang){
        this.xml_lang = xml_lang;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public List<Link> getLinks(){
        return this.links;
    }

    public void setLinks(List<Link> links){
        this.links = links;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUpdated(){
        return this.updated;
    }

    public void setUpdated(String updated){
        this.updated = updated;
    }

    public List<Entry> getEntry(){
        return this.entry;
    }

    public void setEntry(List<Entry> entry){
        this.entry = entry;
    }

}
