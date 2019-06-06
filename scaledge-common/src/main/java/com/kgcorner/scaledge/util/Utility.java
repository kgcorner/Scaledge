package com.kgcorner.scaledge.util;

import java.time.Instant;
import java.util.Date;

public class Utility {
    private Utility(){}
    public static Date getTimeAfterSeconds(int expiresInSeconds){
        Instant now = Instant.now();
        return Date.from(now.plusSeconds(expiresInSeconds));
    }
}
