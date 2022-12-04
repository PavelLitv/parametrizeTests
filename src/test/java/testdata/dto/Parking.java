package testdata.dto;

import lombok.Getter;

@Getter
public class Parking {
    private String name;
    private Car[] cars;

    @Getter
    public static class Car {
        private String name;
        private String[] models;
    }
}
