package com.nydia.bedezium.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据来源
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceInfo {
    @Schema(description = "数据库类型： mysql/postgresql")
    private String connector;
    @Schema(description = "数据库")
    private String db;
    @Schema(description = "表Id")
    private String table;
    @Schema(description = "时间戳(毫秒)")
    private Long tsMs;//时间戳（毫秒）
}