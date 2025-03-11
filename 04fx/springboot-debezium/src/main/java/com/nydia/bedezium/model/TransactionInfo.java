package com.nydia.bedezium.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionInfo {
    private String id;
    private Long totalOrder;
    private Long dataCollectionOrder;

    // Getters, Setters
}