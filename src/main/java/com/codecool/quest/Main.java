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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    static Button Pickup=new Button("Pick Up");
    static Button DropItem=new Button("Drop");
    static Button OpenDoor=new Button("Open");
    GameMap map=MapLoader.loadMap();
    Canvas canvas=new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context=canvas.getGraphicsContext2D();
    Label healthLabel=new Label();
    Label playerName=new EditableLabel(map.getPlayer().getTileName());
    Label attackLabel=new Label();
    Label defenseLabel=new Label();
    ArrayList<String> itemsListArray=new ArrayList<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        ObservableList<String> items=FXCollections.observableArrayList();
        ListView<String> itemsList=new ListView<>(items);

        itemsList.prefWidth(20);
        itemsList.prefHeight(10);
        GridPane ui=new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));
        ui.add(playerName, 0, 0);
        ui.setVgap(5);
        ui.add(healthLabel, 0, 1);
        ui.add(attackLabel, 0, 2);
        ui.add(defenseLabel, 0, 3);
        ui.add(Pickup, 0, 5);
        ui.add(DropItem, 0, 6);
        ui.add(OpenDoor, 0, 7);
        ui.add(new Label("Inventory: "), 0, 8);
        ui.add(itemsList, 0, 9);

        BorderPane borderPane=new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene=new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();

//        if(MapLoader.currentMap == "/level2.txt") {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            MapLoader.currentMap = "/map4.txt";
//            map=MapLoader.loadMap();
//        }


        // DROP
        DropItem.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    for (int x=0; x < map.getWidth(); x++) {
                        for (int y=0; y < map.getHeight(); y++) {
                            Cell cell=map.getCell(x, y);
                            Cell cellBear=map.getCell(x, y);
                            if (cell.getTileName().equals("floor") && cell.getActor() != null) {
                                cell.setType(CellType.FISH);
                                cellBear.setType(CellType.BEAR);
                                itemsList.getItems().remove("fish");
                                refresh();
                            }
                        }
                    }
                });

        //PICK UP
        Pickup.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    for (int x=0; x < map.getWidth(); x++) {
                        for (int y=0; y < map.getHeight(); y++) {
                            Cell cell=map.getCell(x, y);
                            if (cell.getTileName().equals("weapon") && cell.getActor() != null) {
                                map.getPlayer().setAttack(map.getPlayer().getAttack() + 5);
                                refresh();
                                items.add(cell.getTileName());
                                itemsListArray.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            } else if (cell.getTileName().equals("key") && cell.getActor() != null) {
                                items.add(cell.getTileName());
                                itemsListArray.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            } else if (cell.getTileName().equals("helmet") && cell.getActor() != null) {
                                map.getPlayer().setDefense(map.getPlayer().getDefense() + 2);
                                refresh();
                                items.add(cell.getTileName());
                                itemsListArray.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);
                            } else if (cell.getTileName().equals("fish") && cell.getActor() != null) {
                                items.add(cell.getTileName());
                                itemsListArray.add(cell.getTileName());
                                cell.setType(CellType.FLOOR);

                            }
                        }
                    }
                });

        // OPEN DOOR

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
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case Y:
                int playerX=map.getPlayer().getX();
                int playerY=map.getPlayer().getY();
                System.out.println(itemsListArray.toString());
                if (itemsListArray.contains("key")) {
                    Cell possibleDoorCell=map.getCell(playerX, playerY - 1);
                    if (possibleDoorCell.getType() == CellType.DOOR) {
                        possibleDoorCell.setType(CellType.OPEN_DOOR);
                        refresh();
                        itemsListArray.remove("key");
                        System.out.println(itemsListArray + "ItemsListArray");
                        MapLoader.currentMap="/map4.txt";
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        map=MapLoader.loadMap();

                    }
                }
                Cell possibleStairCell = map.getCell(playerX, playerY);
                if (possibleStairCell.getType() == CellType.LADDER) {
                    MapLoader.currentMap = "/map5.txt";
                    map = MapLoader.loadMap();
                } else if(possibleStairCell.getType() == CellType.DOWNLADDER) {
                MapLoader.currentMap = "/map4.txt";
                map = MapLoader.loadMap();
            }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x=0; x < map.getWidth(); x++) {
            for (int y=0; y < map.getHeight(); y++) {
                Cell cell=map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());

        healthLabel.setText("Health:" + map.getPlayer().getHealth());
        attackLabel.setText("Attack:" + map.getPlayer().getAttack());
        defenseLabel.setText("Defense:" + map.getPlayer().getDefense());
    }

    private void attack() {
    }

    class EditableLabel extends Label {
        TextField tf=new TextField();
        String backup="";

        public EditableLabel(String str) {
            super(str);
            this.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    tf.setText(backup=this.getText());
                    this.setGraphic(tf);
                    this.setText("");
                    tf.requestFocus();
                }
            });
            tf.setOnKeyReleased(e -> {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    toLabel();
                    map.getPlayer().setPlayerName(tf.getText());
//                    map.getCell() l1.getText();
                } else if (e.getCode().equals(KeyCode.ESCAPE)) {
                    tf.setText(backup);
                    toLabel();
                }
            });
        }

        void toLabel() {
            this.setGraphic(null);
            this.setText(tf.getText());


//
        }

    }
}

