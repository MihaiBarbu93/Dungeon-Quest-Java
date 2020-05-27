package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;

public class Bear extends Actor {

    public Bear(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "bear";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(1,0);
        if (nextCell.getType() != CellType.WALL && nextCell.getType() != CellType.GHOST) {
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        } else {
            Cell nextCell2 = this.getCell().getNeighbor(-1, 0);
            this.getCell().setActor(null);
            nextCell2.setActor(this);
            this.setCell(nextCell2);
        }
    }
}