package com.agency04.heist.dto;

import com.agency04.heist.model.Skill;
import com.agency04.heist.validator.MainSkillValid;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@MainSkillValid(message = "{mainSkill.invalid}")
public class SkillsDto {
    @Valid
    private List<Skill> skills;

    @NotEmpty(message = "{skill.name.notempty}")
    private String mainSkill;

    public SkillsDto(){
    }

    public SkillsDto(List<Skill> skills, String mainSkill) {
        this.skills = skills;
        this.mainSkill = mainSkill;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    @Override
    public String toString() {
        return "UpdateSkillsDto{" +
                "skills=" + skills +
                ", mainSkill='" + mainSkill + '\'' +
                '}';
    }
}
