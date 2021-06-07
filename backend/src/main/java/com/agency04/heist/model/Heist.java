package com.agency04.heist.model;


import com.agency04.heist.enums.HeistOutcome;
import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.validator.InsertOnly;
import com.agency04.heist.validator.StartTimeBeforeEndTime;
import com.agency04.heist.validator.Unique;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Unique(message = "{unique.heist}")
@StartTimeBeforeEndTime
public class Heist {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotEmpty
    private String location;

    private Instant startTime;

    @Future(message = "{heist.endTime}", groups = {InsertOnly.class})
    private Instant endTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "heist_id")
    private List<SkillRequirement> skills = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private HeistStatus status = HeistStatus.PLANNING;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "heist_member",
            joinColumns = {@JoinColumn(name = "heist_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")})
    private Set<Member> members = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private HeistOutcome outcome;

    public Heist() {
    }

    public Heist(String name, String location, Instant startTime, Instant endTime, List<SkillRequirement> skills) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skills = skills;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public List<SkillRequirement> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillRequirement> skills) {
        this.skills.clear();
        this.skills.addAll(skills);
    }

    public HeistStatus getStatus() {
        return status;
    }

    public void setStatus(HeistStatus status) {
        this.status = status;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public HeistOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(HeistOutcome outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "Heist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", skills=" + skills +
                ", status=" + status +
                ", members=" + members +
                '}';
    }
}
