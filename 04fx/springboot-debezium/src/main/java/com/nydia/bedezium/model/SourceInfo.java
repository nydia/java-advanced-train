package com.nydia.bedezium.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceInfo {
    private String connector;
    private String database;
    private String table;
    private String binlogFile;
    private Long binlogPosition;
    private boolean snapshot;
    private Long timestampMs;

    // Getters, Setters
}