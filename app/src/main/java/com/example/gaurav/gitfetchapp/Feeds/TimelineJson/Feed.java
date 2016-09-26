package com.example.gaurav.gitfetchapp.Feeds.TimelineJson;

import android.widget.ListView;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by GAURAV on 09-08-2016.
 */

@Root(name="feed", strict = false)
@NamespaceList({
@Namespace(reference="http://www.w3.org/2005/Atom"),
        @Namespace(reference="http://search.yahoo.com/mrss/",prefix="media"),
        @Namespace(reference="en-US",prefix="xml:lang")})
public class Feed {

    @Element(name="id")
    private String id;

    @ElementList(name="links",inline=true, required = false)
    private List<Link> links;

    @Element(name="title")
    private String title;

    @Element(name="updated")
    private String updated;

    @ElementList(name="entry",inline=true, required = false)
    private List<Entry> entry;

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
