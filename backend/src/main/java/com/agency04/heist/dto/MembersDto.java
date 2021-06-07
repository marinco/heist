package com.agency04.heist.dto;

import java.util.ArrayList;
import java.util.List;

public class MembersDto {

    private List<String> members;

    public MembersDto(){
        this.members=new ArrayList<>();
    }

    public MembersDto(List<String> members) {
        this.members = members;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "MembersDto{" +
                "members=" + members +
                '}';
    }
}
