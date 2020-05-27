package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;


public class Main extends Application {

    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    Label defenseLabel = new Label();

    static Button Pickup = new Button("Pick Up");



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<String> items= FXCollections.observableArrayList();
        ListView<String> itemsList= new ListView<>(items);
        itemsList.prefWidth(20);
        itemsList.prefHeight(10);
        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));
        ui.setVgap(5);
        ui.add(healthLabel, 0, 0);
        ui.add(attackLabel, 0, 1);
        ui.add(defenseLabel,0,2);
        ui.add(Pickup,0,5);
        ui.add(new Label("Inventory: "), 0, 6);
        ui.add(itemsList,0,7);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();


        Pickup.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    for (int x = 0; x < map.getWidth(); x++) {
                        for (int y = 0; y < map.getHeight(); y++) {
                            Cell cell = map.getCell(x, y);
                            if (cell.getTileName().equals("weapon") && cell.getActor() != null) {
                                map.getPlayer().setAttack(map.getPlayer().getAttack()+5);
                                refresh();
                                items.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            } else if (cell.getTileName().equals("key") && cell.getActor() != null ) {
                                items.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            } else if (cell.getTileName().equals("helmet") && cell.getActor() != null) {
                                map.getPlayer().setDefense(map.getPlayer().getDefense()+2);
                                refresh();
                                items.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            }
                        }
                    }
                });
    }

    private void onKeyPressed(KeyEvent keyEvent) {


        switch (keyEvent.getCode()) {
            case W:
//                map.getPlayer().getCell().getType()
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case S:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case A:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case D:
                map.getPlayer().move(1,0);
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("Health:" + map.getPlayer().getHealth());
        attackLabel.setText("Attack:" + map.getPlayer().getAttack());
        defenseLabel.setText("Defense:" + map.getPlayer().getDefense());
    }
    private void attack(){
    }
}

