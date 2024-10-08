package com.duxsoftware.controller;

import com.duxsoftware.dto.request.TeamRequest;
import com.duxsoftware.exception.ErrorResponse;
import com.duxsoftware.exception.JwtAuthenticationException;
import com.duxsoftware.model.Team;
import com.duxsoftware.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    @Autowired
    private TeamService teamService;


    @Operation(
            summary = "Consulta de Todos los Equipos",
            description = "Devuelve la lista de todos los equipos de fútbol registrados.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Team.class))
                    )
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @Operation(
            summary = "Consulta de un Equipo por ID",
            description = "Devuelve la información del equipo correspondiente al ID proporcionado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Informacion del equipo devuelta correctamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Team.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Equipo no encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"),
                                    }
                                )
                        }

            )
    })
    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<?> getTeamById(@PathVariable("id") int id) {
        return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Búsqueda de Equipos por Nombre",
            description = "Devuelve la lista de equipos cuyos nombres contienen el valor proporcionado en el parámetro de búsqueda.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista devuelta exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Team.class)
                    )
            )
    )
    @GetMapping("/buscar")
    public ResponseEntity<?> getTeamByName(@RequestParam("nombre") String value) {
        return new ResponseEntity<>(teamService.searchTeamsByName(value), HttpStatus.OK);
    }

    @Operation(
            summary = "Creación de un equipo",
            description = "Registra un nuevo equipo de fútbol",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Equipo creado exitosamente",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Team.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud Inválida",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(value = "{\"mensaje\": \"La solicitud es invalida\", \"codigo\": 400}"),
                                    }
                            )
                    }
            )
    })
    @PostMapping("")
    public ResponseEntity<?> createTeam(@Validated @RequestBody TeamRequest teamRequest) {
        Team team = Team.builder()
                .name(teamRequest.getName())
                .league(teamRequest.getLeague())
                .country(teamRequest.getCountry())
                .build();
        return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Actualización de Información de un Equipo",
            description = "Actualiza la informacion de un equipo previamente registrado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos del equipo actualizados exitosamente",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Team.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Equipo no encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject( value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"),
                                    }
                            )
                    }
            )
    })
    @PutMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<?> updateTeam(@PathVariable("id") int id, @Validated @RequestBody TeamRequest teamRequest) {

        Team team = Team.builder()
                .name(teamRequest.getName())
                .league(teamRequest.getLeague())
                .country(teamRequest.getCountry())
                .build();
        return new ResponseEntity<>(teamService.updateTeam(id, team), HttpStatus.OK);
    }


    @Operation(
            summary = "Eliminación de un Equipo",
            description = "Elimina el equipo y su informacion",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Equipo eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Equipo no encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject( value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": 404}"),
                                    }
                            )
                    }
            )
    })
    @DeleteMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<?> deleteTeam(@PathVariable("id") int id) {
            teamService.deleteTeamById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
