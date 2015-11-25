package org.team10424102.whisky.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailabilityResult {

    @JsonProperty("result")
    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
