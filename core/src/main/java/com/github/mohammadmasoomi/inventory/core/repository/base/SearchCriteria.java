package com.github.mohammadmasoomi.inventory.core.repository.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchCriteria {

    private String key;
    private Operation operation;
    private Object value;

    public SearchCriteria(String key, Operation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}
