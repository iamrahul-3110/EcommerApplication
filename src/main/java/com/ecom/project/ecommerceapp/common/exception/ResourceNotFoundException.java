package com.ecom.project.ecommerceapp.common.exception;

public class ResourceNotFoundException extends RuntimeException{
    String reourceName;
    String fieldName;
    String field;
    Long fieldId;

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String reourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : '%s'", reourceName, fieldName, field));
        this.reourceName = reourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFoundException(String reourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s : '%d'", reourceName, field, fieldId));
        this.reourceName = reourceName;
        this.fieldName = field;
        this.fieldId = fieldId;
    }
}
