package com.ticketapp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonPropertyOrder({
        "id",
        "code",
        "description"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String description;
}
