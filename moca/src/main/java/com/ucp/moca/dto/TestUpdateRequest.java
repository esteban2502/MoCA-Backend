package com.ucp.moca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestUpdateRequest {
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("status")
    private boolean status;
    
    // Constructors
    public TestUpdateRequest() {}
    
    public TestUpdateRequest(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
}
