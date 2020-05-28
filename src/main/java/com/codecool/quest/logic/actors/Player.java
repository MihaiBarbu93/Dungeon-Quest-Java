package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Actor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


import java.util.ArrayList;


public class Player extends Actor {
    private int health = 3;
    private int attack=2;
    private int defense =0;
    private ArrayList<String> inventory = new ArrayList<String>();
    private ObservableList<String> items = FXCollections.observableArrayList(inventory);
    private ListView<String> itemsList = new ListView<String>(items);

    public ArrayList<String> getKey(){
        return inventory;
    }

    public ObservableList<String> getItems() {
        return items;
    }

    public void setItems(ObservableList<String> items) {
        this.items = items;
    }

    public void setKey(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public ListView<String> getInventory(){
        return itemsList;
    }

    public void setInventory(ObservableList<String> items) {
        this.itemsList = new ListView<String>(items);;
    }

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


    public String getTileName() {
        if (inventory.contains("weapon") && inventory.contains("armour")){
            return "fullsetchar";
        }
        if (inventory.contains("weapon")) {
            return "weaponChar";
        }
        if (inventory.contains("armour")){
            return "armourChar";
        }
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
        if (nextCell.getType()!=CellType.EMPTY && nextCell.getType()!=CellType.WALL && nextCell.getActor()==null && nextCell.getType()!=CellType.PINE &&
                nextCell.getType()!=CellType.TREE && nextCell.getType()!=CellType.RIVER1 && nextCell.getType()!=CellType.RIVER2
                && nextCell.getType()!=CellType.DOOR && nextCell.getType()!=CellType.BEAR){
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
        try {
            if (nextCell.getActor().getTileName().equals("skeleton")) {
                ((Skeleton) nextCell.getActor()).loseHealth(attacker.getAttack());
                if (((Skeleton) nextCell.getActor()).SkeletonHealth <= 0) {
                    nextCell.setActor(null);
                    nextCell.isDead();
                } else attacker.loseHealth(((Skeleton) nextCell.getActor()).SkeletonDamage - 1);
                if (attacker.getHealth() <= 0) {
                    attacker.getCell().setActor(null);
                }
            }
        }catch (NullPointerException ignore){

        }
        }

    public void pickup() {
        if ((this.getCell().getType() != CellType.FLOOR)) {
            getKey().add(this.getCell().getTileName());
            getItems().add(this.getCell().getTileName());
            if (this.getCell().getTileName().equals("weapon")){
                attack+=7;
            }
            if (this.getCell().getTileName().equals("armour")){
                defense+=5;
            }
            this.getCell().setType(CellType.FLOOR);

        }
    }

    public void drop() {
        if ((this.getCell().getType() != CellType.FISH)) {
            this.getCell().setType(CellType.FISH);
            itemsList.getItems().remove("fish");
            if (getCell().getNeighbor(1,0).getTileName().equals("floor")){
                getCell().setNeighbor(1,0,CellType.BEAR);
            }
        }
    }

    }


