package com.duxsoftware.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequest {
    @JsonProperty("nombre")
    private String name;
    @JsonProperty("liga")
    private String league;
    @JsonProperty("pais")
    private String country;
}

