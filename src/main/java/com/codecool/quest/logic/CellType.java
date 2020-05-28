package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Skeleton;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    WEAPON("weapon"),
    KEY("key"),
    HELMET("helmet"),
    DOOR("door"),
    OPEN_DOOR("open door"),
    TREE("tree"),
    PINE("pine"),
    BEAR("bear"),
    RIVER1("river1"),
    RIVER2("river2"),
    FISH("fish"),
    BRIDGE("bridge"),
    LETTERG("letterG"),
    LETTERA("letterA"),
    LETTERM("letterM"),
    LETTERE("letterE"),
    LETTERO("letterO"),
    LETTERV("letterV"),
    LETTERR("letterR"),
    LETTERL("letterL"),
    DIGIT2("digit2"),
    EXCLAMATION("sign!"),
    LADDER("ladder");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
