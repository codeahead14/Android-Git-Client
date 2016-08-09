package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import org.simpleframework.xml.Element;

/**
 * Created by GAURAV on 09-08-2016.
 */
public class Entry {

    @Element(name="id")
    private String id;

    @Element(name="published")
    private String published;

    @Element(name="updated")
    private String updated;

    @Element(name="link")
    private Link link;

    @Element(name="title")
    private Title title;

    @Element(name="author")
    private Author author;

    @Element(name="media:thumbnail")
    private Media media;

    @Element(name="content")
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

    public Media getMedia(){
        return this.media;
    }

    public void setMedia(Media media){
        this.media = media;
    }
}

