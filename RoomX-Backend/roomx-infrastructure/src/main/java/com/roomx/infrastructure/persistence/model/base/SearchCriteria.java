package com.roomx.infrastructure.persistence.model.base;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchCriteria {
    private String key;
    private Object value;
    private String operation;
    private boolean isOrCondition; // TRUE = OR, FALSE = AND

    public SearchCriteria(String key, Object value, String operation, boolean isOrCondition) {
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.isOrCondition = isOrCondition;
    }

    public SearchCriteria(String key, Object value, String operation) {
        this(key, value, operation, false);
    }

}

