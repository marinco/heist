package com.agency04.heist.event;

import java.time.Instant;

public class NewHeistEvent {

    private Long heistId;
    private Instant startTime;
    private Instant endTime;

    public NewHeistEvent(Long heistId, Instant startTime, Instant endTime){
        this.heistId=heistId;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public Long getHeistId() {
        return heistId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }
}
