package com.example.gaurav.gitfetchapp.DataBus;

/**
 * Created by GAURAV on 16-08-2016.
 */
public class UserInteractionEvent {
    private boolean result;
    private String branchName;

    public UserInteractionEvent(boolean result, String branchName) {
        this.branchName = branchName;
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public String getName(){
        return branchName;
    }
}
