package com.movieTicketService.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonPropertyOrder({
        "id",
        "role",
        "description"
})
@Data
public class RoleDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Role must not be empty")
    private String role;

    private String description;

}
