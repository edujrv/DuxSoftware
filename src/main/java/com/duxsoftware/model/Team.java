package com.duxsoftware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("nombre")
    @Column(nullable = false)
    private String name;

    @JsonProperty("liga")
    @Column(nullable = false)
    private String league;

    @JsonProperty("pais")
    @Column(nullable = false)
    private String country;
}
