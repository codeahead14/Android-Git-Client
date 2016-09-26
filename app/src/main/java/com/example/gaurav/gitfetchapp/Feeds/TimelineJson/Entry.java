package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by GAURAV on 09-08-2016.
 */
@Root(name="entry",strict=false)
public class Entry {

    @Element(name="id",required = false)
    private String id;

    @Element(name="published",required = false)
    private String published;

    @Element(name="updated",required = false)
    private String updated;

    @Element(name="link", required = false)
    private Link link;

    @Element(name="title", required = false)
    private Title title;

    @Element(name="author",required = false)
    private Author author;

    @Element(name="thumbnail", required = false)
    @Namespace(reference="http://search.yahoo.com/mrss/")
    private Thumbnail thumbnail;

    @Element(name="content",required = false)
    private Content content;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPublished(){
        return this.published;
    }

    public void setPublished(String published){
        this.published = published;
    }

    public String getUpdated(){
        return this.updated;
    }

    public void setUpdated(String updated){
        this.updated = updated;
    }

    public Title getTitle(){
        return this.title;
    }

    public void setTitle(Title title){
        this.title = title;
    }

    public Link getLink(){
        return this.link;
    }

    public void setLink(Link link)
    {
        this.link = link;
    }
    public Content getContent(){
        return this.content;
    }

    public void setContent(Content content){
        this.content = content;
    }

    public Author getAuthor(){
        return this.author;
    }

    public void setAuthor(Author author){
        this.author = author;
    }

    public Thumbnail getThumbnail(){
        return this.thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail){
        this.thumbnail = thumbnail;
    }
}

