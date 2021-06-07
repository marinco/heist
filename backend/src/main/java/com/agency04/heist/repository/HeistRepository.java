package com.agency04.heist.repository;

import com.agency04.heist.model.Heist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeistRepository extends JpaRepository<Heist, Long> {
    Optional<Heist> findByName(String name);
}
