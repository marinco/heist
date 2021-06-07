package com.agency04.heist.dto;

import com.agency04.heist.enums.HeistOutcome;

public class HeistOutcomeDto {
    private HeistOutcome outcome;

    public HeistOutcomeDto(){
    }

    public HeistOutcomeDto(HeistOutcome outcome) {
        this.outcome = outcome;
    }

    public HeistOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(HeistOutcome outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "HeistOutcomeDto{" +
                "outcome=" + outcome +
                '}';
    }
}
