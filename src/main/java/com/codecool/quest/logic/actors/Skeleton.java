package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Skeleton extends Actor {
    public int SkeletonHealth=8;
    public int SkeletonDamage=2;
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void loseHealth(int modifier){
        this.SkeletonHealth-=modifier;
    }
}
