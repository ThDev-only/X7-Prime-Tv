package com.ultra.vision.series.object;

public class SeasonObject {

    private int numSeason;
    private int id;

    public SeasonObject(int id, int numSeason) {
        this.id = id;
        this.numSeason = numSeason;
    }

    public int getNumSeason() {
        return this.numSeason;
    }

    public void setNumSeason(int numSeason) {
        this.numSeason = numSeason;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
