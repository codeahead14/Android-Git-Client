package com.example.gaurav.gitfetchapp.Events.DeploymentEventPayload;

import com.example.gaurav.gitfetchapp.Events.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeploymentEventPayload extends com.example.gaurav.gitfetchapp.Events.Payload{

    @SerializedName("deployment")
    @Expose
    private Deployment deployment;

    /**
     *
     * @return
     * The deployment
     */
    public Deployment getDeployment() {
        return deployment;
    }

    /**
     *
     * @param deployment
     * The deployment
     */
    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

}