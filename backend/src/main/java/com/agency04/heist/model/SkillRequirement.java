package com.agency04.heist.model;

import com.agency04.heist.validator.SkillLevelValid;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "level", "heist_id"})
})
public class SkillRequirement {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String name;

    @SkillLevelValid
    private String level;

    private int members;

    public SkillRequirement() {

    }

    public SkillRequirement(String name, String level, int members) {
        this.name = name;
        this.level = level;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "SkillRequirement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", members=" + members +
                '}';
    }
}
