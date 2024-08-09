package com.duxsoftware.repository;

import com.duxsoftware.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository  extends JpaRepository<Team, Long> {
    Optional<Team> findById(int id);

    List<Team> findByNameContainingIgnoreCase(String name);

    void deleteById(int id);

    Boolean existsById(int id);
}
