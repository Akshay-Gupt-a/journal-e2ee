package com.megeMind.journalEntry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
public class WeatherResponse{

    private Current current;
    @Getter
    @Setter
   public static  class Current{
        private int feelslike;

    }
}




