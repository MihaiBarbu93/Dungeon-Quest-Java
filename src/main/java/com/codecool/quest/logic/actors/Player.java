package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.actors.Actor;

import java.util.List;

public class Player extends Actor {
    private int health = 10;
    private int attack=2;
    private int defense =0;


    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }
    public int getAttack() {
        return attack;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }


    private String PlayerName="player";

    public Player(Cell cell) {
        super(cell);
    }

    public List<Item> get_inventory(){
        return
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
        else {
            attack(this.getCell(),nextCell);
        }
    }

    @Override
    public void loseHealth(int damaghe) {
        this.health-=damaghe;
    }

    public void attack(Cell cell, Cell nextCell){
        Player attacker = (Player) cell.getActor();
        if (nextCell.getActor().getTileName().equals("skeleton")) {
            ((Skeleton) nextCell.getActor()).loseHealth(attacker.getAttack());
            if (((Skeleton) nextCell.getActor()).SkeletonHealth <= 0) {
                nextCell.setActor(null);
                System.out.println("daaa");
                } else attacker.loseHealth(((Skeleton) nextCell.getActor()).SkeletonDamage);
                if (attacker.getHealth() <= 0) {
                    attacker.getCell().setActor(null);
                }
            }
        }
    }


