package com.example.gaurav.gitfetchapp.Issues;

import java.util.ArrayList;

/**
 * Created by GAURAV on 22-01-2017.
 */

public class IssueEventsAndComments {
    public ArrayList<IssueCommentsJson> issueComments;
    public ArrayList<IssueEventsJson> issueEvents;

    public IssueEventsAndComments(ArrayList<IssueCommentsJson> issueComments, ArrayList<IssueEventsJson> issueEvents){
        this.issueComments = issueComments;
        this.issueEvents = issueEvents;
    }
}
