package com.agency04.heist.model;

import com.agency04.heist.validator.SkillLevelValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "member_id"})
})
public class Skill {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "{skill.name.notempty")
    private String name;

    @Size(min = 1, max = 10, message = "{skill.level.length}")
    @Value("*")
    @SkillLevelValid
    private String level;

    public Skill() {
    }

    public Skill(String name, String level) {
        this.name = name;
        this.level = level;
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
        this.name = name.toLowerCase();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "Skill{" +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
