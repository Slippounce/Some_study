package ru.nsu.fit.g16205.shmidt.konwaylogic;

public class GameParameters {


    private double liveBegin;
    private double lifeEnd;
    private double birthBegin;
    private double birthEnd;
    private double fstImpct;
    private double sndImpct;

    public GameParameters(){
        liveBegin = 2;
        lifeEnd = 3.3;
        birthBegin = 2.3;
        birthEnd = 2.9;
        fstImpct = 1;
        sndImpct = 0.3;
    }

    //region Getters
    public double getLiveBegin() {
        return liveBegin;
    }

    public double getLifeEnd() {
        return lifeEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public double getFstImpct() {
        return fstImpct;
    }

    public double getSndImpct() {
        return sndImpct;
    }
    //endregion

    //region Setters
    public void setLiveBegin(double liveBegin) {
        if(liveBegin > lifeEnd || liveBegin > birthBegin || liveBegin > birthEnd){
            throw new IllegalArgumentException("");
        }
        if(liveBegin <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.liveBegin = liveBegin;
    }

    public void setLifeEnd(double lifeEnd) {
        if(lifeEnd < liveBegin || lifeEnd < birthBegin || lifeEnd < birthEnd){
            throw new IllegalArgumentException("");
        }
        if(lifeEnd <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.lifeEnd = lifeEnd;
    }

    public void setBirthBegin(double birthBegin) {
        if(birthBegin < liveBegin || birthBegin > lifeEnd || birthBegin > birthEnd){
            throw new IllegalArgumentException("");
        }
        if(birthBegin <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.birthBegin = birthBegin;
    }

    public void setBirthEnd(double birthEnd) {
        if(birthEnd < liveBegin || birthEnd < birthBegin || birthEnd > lifeEnd){
            throw new IllegalArgumentException("");
        }
        if(lifeEnd <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.birthEnd = birthEnd;
    }

    public void setFstImpct(double fstImpct) {
        if(fstImpct <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.fstImpct = fstImpct;
    }

    public void setSndImpct(double sndImpct) {
        if(sndImpct <= 0){
            throw new IllegalArgumentException("specified value should be >0");
        }
        this.sndImpct = sndImpct;
    }
    //endregion

    public void printReferenceInformation(){}
}
