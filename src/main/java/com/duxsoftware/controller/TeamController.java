package com.duxsoftware.controller;

import com.duxsoftware.exception.ErrorResponse;
import com.duxsoftware.exception.NotFoundException;
import com.duxsoftware.model.Team;
import com.duxsoftware.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("")
    public ResponseEntity<?> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

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

    @GetMapping("/buscar")
    public ResponseEntity<?> getTeamByName(@RequestParam("nombre") String value) {
        try {
            return new ResponseEntity<>(teamService.searchTeamsByName(value), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createTeam(@Validated @RequestBody Team team) {
        try{
            return new ResponseEntity<>(teamService.saveTeam(team), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("La solicitud es invalida", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable("id") int id, @Validated @RequestBody Team team) {
        try {
            return new ResponseEntity<>(teamService.updateTeam(id, team), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



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
