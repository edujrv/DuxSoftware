package com.duxsoftware.controller;

import com.duxsoftware.dto.request.TeamRequest;
import com.duxsoftware.exception.ErrorResponse;
import com.duxsoftware.exception.NotFoundException;
import com.duxsoftware.model.Team;
import com.duxsoftware.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Operation(summary = "Consulta de Todos los Equipos", description = "Devuelve la lista de todos los equipos de fútbol registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("")
    public ResponseEntity<?> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @Operation(summary = "Consulta de un Equipo por ID", description = "Devuelve la información del equipo correspondiente al ID proporcionado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informacion del equipo devuelta correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Búsqueda de Equipos por Nombre", description = "Devuelve la lista de equipos cuyos nombres contienen el valor proporcionado en el parámetro de búsqueda.")
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    @GetMapping("/buscar")
    public ResponseEntity<?> getTeamByName(@RequestParam("nombre") String value) {
        try {
            return new ResponseEntity<>(teamService.searchTeamsByName(value), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Creación de un equipo", description = "Registra un nuevo equipo de fútbol")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud Inválida")
    })
    @PostMapping("")
    public ResponseEntity<?> createTeam(@Validated @RequestBody TeamRequest teamRequest) {
        try{
            Team team = Team.builder()
                    .name(teamRequest.getName())
                    .league(teamRequest.getLeague())
                    .country(teamRequest.getCountry())
                    .build();
            return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("La solicitud es invalida", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Actualización de Información de un Equipo", description = "Actualiza la informacion de un equipo previamente registrado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Datos del equipo actualizados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable("id") int id, @Validated @RequestBody TeamRequest teamRequest) {
        try {
            Team team = Team.builder()
                    .name(teamRequest.getName())
                    .league(teamRequest.getLeague())
                    .country(teamRequest.getCountry())
                    .build();
            return new ResponseEntity<>(teamService.updateTeam(id, team), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Eliminación de un Equipo", description = "Elimina el equipo y su informacion")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable("id") int id) {
        try {
            teamService.deleteTeamById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
