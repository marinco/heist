package com.agency04.heist;

import com.agency04.heist.model.Heist;

public class Util {

    public static boolean overlap(Heist h1, Heist h2) {
        if ((h1.getStartTime().isAfter(h2.getStartTime()) && h1.getStartTime().isBefore(h2.getEndTime()))
                || (h1.getEndTime().isAfter(h2.getStartTime()) && h1.getEndTime().isBefore(h2.getEndTime()))
                || (h1.getStartTime().equals(h2.getStartTime()) || h1.getEndTime().equals(h2.getEndTime()))) {
            return true;
        }
        return false;
    }
}
