package com.agency04.heist.dto;

import com.agency04.heist.model.SkillRequirement;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class UpdateRequiredSkillsDto {

    @Valid
    private List<SkillRequirement> skills;

    public UpdateRequiredSkillsDto() {
        this.skills = new ArrayList<>();
    }

    public UpdateRequiredSkillsDto(List<SkillRequirement> skills) {
        this.skills = skills;
    }

    public List<SkillRequirement> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillRequirement> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "UpdateRequiredSkillsDto{" +
                "skills=" + skills +
                '}';
    }
}
