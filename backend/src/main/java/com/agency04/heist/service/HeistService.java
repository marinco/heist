package com.agency04.heist.service;

import com.agency04.heist.model.Heist;

import java.util.List;
import java.util.Optional;

public interface HeistService {
    List<Heist> findAll();

    Optional<Heist> findById(Long id);

    Optional<Heist> findByName(String name);

    Heist add(Heist heist);

    Heist update(Heist heist);

    Heist start(Heist heist);

    Heist finish(Heist heist);

    Heist confirmMembers(Heist heist);
}
