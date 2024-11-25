package com.web.rail.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonPropertyOrder({"status", "message", "size", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponse {
    private List<?> data;
    private String message;
    private Integer size;
    private Integer status;
    Map<String, List<String>> errors;
    Map<String, String> errorsMap;
}
