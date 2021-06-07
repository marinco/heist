package com.agency04.heist.dto;

import com.agency04.heist.model.Heist;
import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.model.SkillRequirement;

import java.time.Instant;
import java.util.List;

public class HeistDto {
    private String name;
    private String location;
    private Instant startTime;
    private Instant endTime;
    private List<SkillRequirement> skills;
    private HeistStatus status;

    public HeistDto() {

    }

    public HeistDto(Heist heist) {
        this.name = heist.getName();
        this.location = heist.getLocation();
        this.startTime = heist.getStartTime();
        this.endTime = heist.getEndTime();
        this.skills = heist.getSkills();
        this.status = heist.getStatus();
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
        this.skills = skills;
    }

    public HeistStatus getStatus() {
        return status;
    }

    public void setStatus(HeistStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HeistDto{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", skills=" + skills +
                ", status=" + status +
                '}';
    }
}
