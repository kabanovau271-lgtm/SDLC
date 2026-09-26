package com.example.sincalculator.model;

public class SinData {
    private final String name;
    private final String birthDate;
    private final int smokingYears;
    private final int delaysPerWeek;
    private final double socialHoursPerDay;
    private final int brokenPromisesPerWeek;
    private final int swearingPerWeek;

    public SinData() {
        this("", "", 0, 0, 0, 0, 0);
    }

    public SinData(String name, String birthDate, int smokingYears, int delaysPerWeek,
                   double socialHoursPerDay, int brokenPromisesPerWeek,
                   int swearingPerWeek) {
        this.name = name;
        this.birthDate = birthDate;
        this.smokingYears = smokingYears;
        this.delaysPerWeek = delaysPerWeek;
        this.socialHoursPerDay = socialHoursPerDay;
        this.brokenPromisesPerWeek = brokenPromisesPerWeek;
        this.swearingPerWeek = swearingPerWeek;
    }

    public String getName() { return name; }
    public String getBirthDate() { return birthDate; }
    public int getSmokingYears() { return smokingYears; }
    public int getDelaysPerWeek() { return delaysPerWeek; }
    public double getSocialHoursPerDay() { return socialHoursPerDay; }
    public int getBrokenPromisesPerWeek() { return brokenPromisesPerWeek; }
    public int getSwearingPerWeek() { return swearingPerWeek; }
}
