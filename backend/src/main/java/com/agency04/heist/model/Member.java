package com.agency04.heist.model;

import com.agency04.heist.enums.RobberStatus;
import com.agency04.heist.enums.Sex;
import com.agency04.heist.validator.MainSkillValid;
import com.agency04.heist.validator.Unique;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Unique(message = "{unique.member}")
@MainSkillValid(message = "{mainSkill.invalid}")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "{name.notnull}")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    @Valid
    private List<Skill> skills = new ArrayList<>();

    private String mainSkill;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RobberStatus status;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<Heist> heists;

    public Member() {

    }

    public Member(String name, Sex sex, String email, List<Skill> skills, String mainSkill, RobberStatus status) {
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.skills = skills;
        this.mainSkill = mainSkill;
        this.status = status;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        this.skills.addAll(skills);
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public RobberStatus getStatus() {
        return status;
    }

    public void setStatus(RobberStatus status) {
        this.status = status;
    }

    public Set<Heist> getHeists() {
        return heists;
    }

    public void setHeists(Set<Heist> heists) {
        this.heists = heists;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", skills=" + skills +
                ", mainSkill='" + mainSkill + '\'' +
                ", status=" + status +
                '}';
    }
}
