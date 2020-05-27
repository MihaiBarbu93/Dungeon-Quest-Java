package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        List<String> maps = new ArrayList<>();
        Collections.addAll(maps, "/map3.txt", "/map2.txt", "/map3.txt");
        int randMap = (int) (Math.random() * 3);
        InputStream is = MapLoader.class.getResourceAsStream(maps.get(randMap));
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.WEAPON);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case 'h':
                            cell.setType(CellType.HELMET);
                            break;
                        case 'D':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'O':
                            cell.setType(CellType.OPEN_DOOR);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'p':
                            cell.setType(CellType.PINE);
                            break;
                        case 'b':
                            cell.setType(CellType.BEAR);
                            break;
                        case 'r':
                            cell.setType(CellType.RIVER1);
                            break;
                        case 'R':
                            cell.setType(CellType.RIVER2);
                            break;
                        case 'f':
                            cell.setType(CellType.FISH);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
