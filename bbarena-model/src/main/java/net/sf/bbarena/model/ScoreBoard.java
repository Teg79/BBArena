package net.sf.bbarena.model;

import java.io.Serializable;
import java.util.Observable;

public class ScoreBoard extends Observable implements Serializable {
    
    private Integer _score;
    private Integer _cas;
    private Integer _fame;
    private Integer _fans;

    public Integer getScore() {
        return _score;
    }
    
    public void addScore() {
        _score++;
    }
    
    public Integer getCas() {
        return _cas;
    }
    
    public void addCas() {
        _cas++;
    }
    
    public Integer getFame() {
        return _fame;
    }
    
    public void setFame(Integer fame) {
        _fame = fame;
    }


    public void setFans(Integer fans) {
        _fans = fans;
    }

    public Integer getFans() {
        return _fans;
    }
}