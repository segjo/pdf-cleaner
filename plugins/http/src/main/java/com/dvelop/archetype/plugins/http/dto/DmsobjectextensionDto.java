package com.dvelop.archetype.plugins.http.dto;

public class DmsobjectextensionDto {
    String id;
    String context;
    String uriTemplate;
    String iconUri;
    CaptionDto[] captions;
    ActivationConditionDto[] activationConditions;

    public static class CaptionDto {
        String culture;
        String caption;
        public CaptionDto(String culture, String caption){
            this.culture=culture;
            this.caption=caption;
        }

        public String getCulture(){
            return culture;
        }

        public String getCaption(){
            return caption;
        }
    }

    public static class ActivationConditionDto {
        String propertyId;
        String operator;
        String[] values;

        public ActivationConditionDto(String propertyId, String operator, String[] values){
            this.propertyId=propertyId;
            this.operator=operator;
            this.values=values;
        }

        public String getPropertyId(){
            return propertyId;
        }

        public String getOperator(){
            return operator;
        }

        public String[] getValues(){
            return values;
        }
    }



    public DmsobjectextensionDto(String id, String context, String uriTemplate, String iconUri) {
        this.id = id;        this.context = context;
        this.uriTemplate = uriTemplate;
        this.iconUri = iconUri;
        
        
        this.captions = new CaptionDto[]{
            new CaptionDto(
                    "de",
                    "pdf cleaner"
            ),
            new CaptionDto(
                "en",
                "pdf cleaner"
        )
    };

    this.activationConditions = new ActivationConditionDto[]{
        new ActivationConditionDto(
                "repository.id",
                "or",
                new String[]{"a31c2c9d-7dc5-421f-879f-dca5afc32bb5"}
        ) 
    };
    }




    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActivationConditionDto[] getActivationConditions() {
        return this.activationConditions;
    }

    public void setActivationConditions(ActivationConditionDto[] activationConditions) {
        this.activationConditions = activationConditions;
    }

    public CaptionDto[] getCaptions() {
        return this.captions;
    }

    public void setCaptions(CaptionDto[] captions) {
        this.captions = captions;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUriTemplate() {
        return this.uriTemplate;
    }

    public void setUriTemplate(String uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    public String getIconUri() {
        return this.iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }
}
