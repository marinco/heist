package com.agency04.heist.dto;

import com.agency04.heist.model.SkillRequirement;

import java.util.ArrayList;
import java.util.List;

public class EligibleMembersDto {
    private List<SkillRequirement> skills;
    private List<HeistMemberDto> members;

    public EligibleMembersDto(){
        this.skills=new ArrayList<>();
        this.members=new ArrayList<>();
    }


    public EligibleMembersDto(List<SkillRequirement> skills, List<HeistMemberDto> members) {
        this.skills=skills;
        this.members=members;
    }

    public List<SkillRequirement> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillRequirement> skills) {
        this.skills = skills;
    }

    public List<HeistMemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<HeistMemberDto> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "EligibleMembersDto{" +
                "skills=" + skills +
                ", members=" + members +
                '}';
    }
}
