package com.duxsoftware.service;

import com.duxsoftware.exception.NotFoundException;
import com.duxsoftware.model.Team;
import com.duxsoftware.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Test
    public void testGetAllTeams() {
        // GIVEN
        given(teamRepository.findAll()).willReturn(List.of());

        // WHEN
        List<Team> teams = teamService.getAllTeams();

        // THEN
        then(teams).isNotNull();
        then(teams).isEmpty();
    }

    @Test
    public void testGetTeamById() throws Exception {
        // GIVEN
        Team team = Team.builder()
                .id(1)
                .country("Pais 1")
                .league("Liga 1")
                .name("Equipo 1")
                .build();
        given(teamRepository.findById(1)).willReturn(Optional.of(team));

        // WHEN
        Team result = teamService.getTeamById(1);

        // THEN
        then(result).isEqualTo(team);
    }

    @Test
    public void testGetTeamByName() {
        // GIVEN
        List<Team> teams = List.of(
                Team.builder().name("Barcelona").build(),
                Team.builder().name("Ambar").build()
        );
        given(teamRepository.findByNameContainingIgnoreCase("Bar")).willReturn(teams);

        // WHEN
        List<Team> result = teamService.searchTeamsByName("Bar");

        // THEN
        then(result).isNotNull();
        then(result).isEqualTo(teams);
        then(result).hasSize(2);
    }

    @Test
    public void testGetTeamByIdNotFound() {
        // GIVEN
        given(teamRepository.findById(any(Integer.class))).willReturn(Optional.empty());

        // WHEN
        NotFoundException exception = assertThrows(NotFoundException.class,() -> teamService.getTeamById(1));
        // THEN
        then(exception.getMessage()).isEqualTo("Equipo no encontrado");
    }

    @Test
    public void testCreateTeam() {
        // GIVEN
        Team team= Team.builder()
                .id(1)
                .country("Pais 1")
                .league("Liga 1")
                .name("Equipo 1")
                .build();
        given(teamRepository.save(any(Team.class))).willReturn(team);

        // WHEN
        Team response = teamService.saveTeam(team);

        // THEN
        then(response).isNotNull();
        then(response).isEqualTo(team);
    }

    @Test
    public void testCreateTeamBadRequest() {
        // GIVEN
        given(teamRepository.save(any(Team.class))).willThrow(IllegalArgumentException.class);

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> teamService.saveTeam(Team.builder().build()));
    }

    @Test
    public void testUpdateTeam() throws NotFoundException {
        // GIVEN
        Team team= Team.builder()
                .id(1)
                .country("Pais 1")
                .league("Liga 1")
                .name("Equipo 1")
                .build();

        given(teamRepository.existsById(any(Integer.class))).willReturn(true);
        given(teamRepository.save(any(Team.class))).willReturn(team);

        // WHEN
        Team result = teamService.updateTeam(1, team);

        // THEN
        then(result).isNotNull();
        then(result).isEqualTo(team);
    }

    @Test
    public void testUpdateTeamNotFoundException() {
        // GIVEN
        Team team= Team.builder()
                .id(1)
                .country("Pais 1")
                .league("Liga 1")
                .name("Equipo 1")
                .build();

        given(teamRepository.existsById(any(Integer.class))).willReturn(false);

        // WHEN
        NotFoundException exception = assertThrows(NotFoundException.class,() -> teamService.updateTeam(1, team));
        // THEN
        then(exception.getMessage()).isEqualTo("Equipo no encontrado");
    }

    @Test
    public void testDeleteTeam() {
        // GIVEN
        int teamId = 1;
        given(teamRepository.existsById(teamId)).willReturn(true);

        // WHEN
        assertDoesNotThrow(() -> teamService.deleteTeamById(teamId));

        verify(teamRepository, times(1)).existsById(teamId);
        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    public void testDeleteTeamNotFoundException() {
        //GIVEN
        int teamId = 1;
        given(teamRepository.existsById(teamId)).willReturn(false);

        // WHEN
        assertThrows(NotFoundException.class, () -> teamService.deleteTeamById(teamId));

        // THEN
        verify(teamRepository, times(1)).existsById(teamId);
        verify(teamRepository, times(0)).deleteById(teamId);
    }
}
