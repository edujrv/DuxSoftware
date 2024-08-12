package com.duxsoftware.service;

import com.duxsoftware.exception.JwtAuthenticationException;
import com.duxsoftware.exception.NotFoundException;
import com.duxsoftware.model.Team;
import com.duxsoftware.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() throws JwtAuthenticationException{
        return teamRepository.findAll();
    }


    public Team getTeamById(int id) throws NotFoundException {
        return teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
    }

    public List<Team> searchTeamsByName(String name)  {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(int id, Team team) throws NotFoundException {
        if (teamRepository.existsById(id)) {
            team.setId(id);
            return teamRepository.save(team);
        } else {
            throw new NotFoundException("Equipo no encontrado");
        }
    }

    @Transactional
    public void deleteTeamById(int id) throws NotFoundException {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        }else{
            throw new NotFoundException("Equipo no encontrado");
        }
    }

}
