package com.dvelop.archetype.plugins.http.dto;

import com.dvelop.archetype.plugins.http.template.TemplateName;

@TemplateName("contextaction")
public class ContextactionDto {

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
