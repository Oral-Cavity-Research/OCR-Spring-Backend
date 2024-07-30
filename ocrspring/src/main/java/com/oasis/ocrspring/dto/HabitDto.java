package com.oasis.ocrspring.dto;

public class HabitDto {
    private String habit;
    private String frequency;

    public HabitDto(String habit, String frequency) {
        this.habit = habit;
        this.frequency = frequency;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
