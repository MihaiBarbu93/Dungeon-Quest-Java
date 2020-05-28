package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {


    public static List<String> mapsList = new ArrayList<>() {{
        add("/map1.txt");
        add("/map2.txt");
        add("/map3.txt");
    }};

    public static int randMap = (int) (Math.random() * 3);

    public static String currentMap = mapsList.get(randMap);


    public static GameMap loadMap() {


        InputStream is = MapLoader.class.getResourceAsStream(currentMap);

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
                            cell.setType(CellType.ARMOUR);
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
                            if (currentMap == "/map1.txt" || currentMap == "/map2.txt" ||
                                    currentMap == "/map3.txt" || currentMap == "/map4.txt" || currentMap == "/map5.txt") {
                                cell.setType(CellType.RIVER2);
                                break;
                            } else {
                                cell.setType(CellType.LETTERR);
                                break;
                            }
                        case 'f':
                            cell.setType(CellType.FISH);
                            break;
                        case 'e':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 'G':
                            cell.setType(CellType.LETTERG);
                            break;
                        case 'A':
                            cell.setType(CellType.LETTERA);
                            break;
                        case 'M':
                            cell.setType(CellType.LETTERM);
                            break;
                        case 'E':
                            cell.setType(CellType.LETTERE);
                            break;
                        case '0':
                            cell.setType(CellType.LETTERO);
                            break;
                        case 'V':
                            cell.setType(CellType.LETTERV);
                            break;
                        case 'L':
                            cell.setType(CellType.LETTERL);
                            break;
                        case 'Y':
                            cell.setType(CellType.LETTERY);
                            break;
                        case 'u':
                            cell.setType(CellType.LETTERU);
                            break;
                        case 'W':
                            cell.setType(CellType.LETTERW);
                            break;
                        case 'I':
                            cell.setType(CellType.LETTERI);
                            break;
                        case 'n':
                            cell.setType(CellType.LETTERN);
                            break;
                        case '2':
                            cell.setType(CellType.DIGIT2);
                            break;
                        case '!':
                            cell.setType(CellType.EXCLAMATION);
                            break;
                        case 'U':
                            cell.setType(CellType.LADDER);
                            break;
                        case 'N':
                            cell.setType(CellType.DOWNLADDER);
                            break;
                        case 'z':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case '<':
                            cell.setType(CellType.PRINCESS);
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
