package com.livspace.service_framework.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private SearchOperator operation;
    private Set<String> value;
}
