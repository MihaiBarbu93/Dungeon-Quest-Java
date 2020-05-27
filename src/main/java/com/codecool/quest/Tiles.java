package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("weapon", new Tile(3, 30));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("helmet",new Tile(3, 22));
        tileMap.put("door", new Tile(6, 4));
        tileMap.put("open door", new Tile(4, 4));
        tileMap.put("tree", new Tile(4, 2));
        tileMap.put("pine", new Tile(3, 2));
        tileMap.put("bear", new Tile(30, 8));
        tileMap.put("river1", new Tile(8, 4));
        tileMap.put("river2", new Tile(8, 5));
        tileMap.put("fish", new Tile(17, 29));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        StringBuilder xAndY = new StringBuilder();
        xAndY.append(tile.x).append(tile.y);
        System.out.println(xAndY.toString());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
