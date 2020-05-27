package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.actors.Actor;

public class Player extends Actor {



    private String PlayerName="player";

    public Player(Cell cell) {
        super(cell);
    }



    public String getTileName() {
        return "player";
    }

    public String getPlayerName(){
        return PlayerName;
    }

    public void setPlayerName(String PlayerName){
        this.PlayerName=PlayerName;
    }

    public boolean checkPlayers(String player_name){
        switch (player_name){
            case "Mihai":
                return true;
            case "Alex":
                return true;
            case "Silviu":
                return true;
        }
        return false;

    };


    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (nextCell.getType()!=CellType.WALL && nextCell.getActor()==null && nextCell.getType()!=CellType.PINE &&
                nextCell.getType()!=CellType.TREE && nextCell.getType()!=CellType.RIVER1 && nextCell.getType()!=CellType.RIVER2
                && nextCell.getType()!=CellType.DOOR){
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
        if (nextCell.getType()==CellType.WALL && checkPlayers(getPlayerName())){
            this.getCell().setActor(null);
            nextCell.setActor(this);
            this.setCell(nextCell);
        }
    }
}
