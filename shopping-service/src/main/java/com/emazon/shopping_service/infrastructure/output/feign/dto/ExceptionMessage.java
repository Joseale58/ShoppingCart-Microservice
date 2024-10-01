package com.emazon.shopping_service.infrastructure.output.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExceptionMessage {
    @JsonProperty("Message")
    private String message;
}