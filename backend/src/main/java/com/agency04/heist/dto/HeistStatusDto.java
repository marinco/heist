package com.agency04.heist.dto;

import com.agency04.heist.enums.HeistStatus;

public class HeistStatusDto {
    private HeistStatus status;

    public HeistStatusDto() {
    }

    public HeistStatusDto(HeistStatus status) {
        this.status = status;
    }

    public HeistStatus getStatus() {
        return status;
    }

    public void setStatus(HeistStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HeistStatusDto{" +
                "status=" + status +
                '}';
    }
}
