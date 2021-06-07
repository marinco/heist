package com.agency04.heist.dto;

import com.agency04.heist.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class HeistMemberDto {
    private String name;
    private List<Skill> skills;

    public HeistMemberDto(){
        this.skills=new ArrayList<>();
    }

    public HeistMemberDto(String name, List<Skill> skills) {
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "MemberNameSkillsDto{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}
