package com.agency04.heist.service;

import com.agency04.heist.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> findAll();
    void delete(Skill skill);
}
