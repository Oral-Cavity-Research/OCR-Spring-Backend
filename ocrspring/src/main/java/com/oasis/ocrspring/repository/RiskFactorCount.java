package com.oasis.ocrspring.repository;

public class RiskFactorCount {
    private String habit;
    private long count;

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RiskFactorCount{" +
                "habit='" + habit + '\'' +
                ", count=" + count +
                '}';
    }
}
