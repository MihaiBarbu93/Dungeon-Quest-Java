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

    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label playerName =  new EditableLabel(map.getPlayer().getTileName());
    Label attackLabel = new Label();
    Label defenseLabel = new Label();
    static Button Pickup = new Button("Pick Up");
    static Button DropItem = new Button("Drop");
    static Button restart = new Button("Restart Game");
    ObservableList<String> items = FXCollections.observableArrayList();
    ListView<String> itemsList = map.getPlayer().getInventory();
    ArrayList itemsListArray = map.getPlayer().getKey();



    public static void main(String[] args) {
        launch(args);
    }

    class EditableLabel extends Label{
        TextField tf = new TextField();
        String backup = "";

        public EditableLabel(String str){
            super(str);
            this.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2){
                    tf.setText(backup = this.getText());
                    this.setGraphic(tf);
                    this.setText("");
                    tf.requestFocus();
                }
            });
            tf.setOnKeyReleased(e -> {
                if(e.getCode().equals(KeyCode.ENTER)){
                    toLabel();
                    map.getPlayer().setPlayerName(tf.getText());
//                    map.getCell() l1.getText();
                }else if(e.getCode().equals(KeyCode.ESCAPE)){
                    tf.setText(backup);
                    toLabel();
                }
            });
        }

        void toLabel(){
            this.setGraphic(null);
            this.setText(tf.getText());


//
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        //invetory display

        itemsList.prefWidth(20);
        itemsList.prefHeight(10);
        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));
        ui.add(playerName,0,0);
        ui.setVgap(5);
        ui.add(healthLabel, 0, 1);
        ui.add(attackLabel, 0, 2);
        ui.add(defenseLabel, 0, 3);
        ui.add(Pickup, 0, 5);
        ui.add(DropItem, 0, 6);
        ui.add(restart,0,7);
        ui.add(new Label("Inventory: "), 0, 8);
        ui.add(map.getPlayer().getInventory(), 0, 9);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();


        Pickup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            map.getPlayer().pickup();
            refresh();

        });
        DropItem.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            removeBear();
            map.getPlayer().drop();
        });

        restart.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            primaryStage.close();
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private void gameOver(){
        if(map.getPlayer().getHealth()<=0){
            changeMap("/gameOver.txt");
        }
    }

    public void removeBear(){
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getTileName().equals("bear")){
                    cell.setType(CellType.FLOOR);
                }
            }
        }
    }

    public void win(){
        if(map.getPlayer().getCell().getNeighbor(-1,0).getTileName().equals("princess") ||
                map.getPlayer().getCell().getNeighbor(1,0).getTileName().equals("princess") ||
                map.getPlayer().getCell().getNeighbor(0,1).getTileName().equals("princess") ||
                map.getPlayer().getCell().getNeighbor(0,-1).getTileName().equals("princess")){
            changeMap("/win.txt");
        }

    }

    private void onKeyPressed(KeyEvent keyEvent) {


        switch (keyEvent.getCode()) {
            case W:
//                map.getPlayer().getCell().getType()
                map.getPlayer().move(0, -1);
                win();
                gameOver();
                refresh();
                break;
            case S:
                map.getPlayer().move(0, 1);
                gameOver();
                win();
                refresh();
                break;
            case A:
                map.getPlayer().move(-1, 0);
                win();
                gameOver();
                refresh();
                break;
            case D:
                map.getPlayer().move(1,0);
                gameOver();
                win();
                refresh();
                break;
            case Y:

                int playerX=map.getPlayer().getX();
                int playerY=map.getPlayer().getY();
                System.out.println(map.getPlayer().getInventory().getItems().toString());
                System.out.println(map.getPlayer().getItems().toString());
                System.out.println(map.getPlayer().getKey().toString());
                if (map.getPlayer().getKey().contains("key")) {
                    Cell possibleDoorCell=map.getCell(playerX, playerY - 1);
                    if (possibleDoorCell.getType() == CellType.DOOR) {
                        possibleDoorCell.setType(CellType.OPEN_DOOR);
                        refresh();
                        itemsListArray.remove("key");
                        itemsList.getItems().remove("key");
                        changeMap("/level2.txt");
                        refresh();
                    }
                }
                Cell possibleStairCell = map.getCell(playerX, playerY);
                if (possibleStairCell.getType() == CellType.LADDER) {
                    changeMap("/map5.txt");
                    refresh();
                }
                break;

            case N:
                if (MapLoader.currentMap == "/level2.txt" ) {
                    changeMap("/map4.txt");
                    refresh();
                }
                break;
        }
    }

    public void changeMap(String level) {
        int currentHealth = map.getPlayer().getHealth();
        int currentDamage = map.getPlayer().getAttack();
        int currentArmor = map.getPlayer().getDefense();
        ArrayList<String> currentInventory = new ArrayList<>(map.getPlayer().getKey());
        ObservableList<String> currentItems = FXCollections.observableArrayList(currentInventory);
        ListView<String> currentView = new ListView<>(currentItems);
        MapLoader.currentMap=level;
        map=MapLoader.loadMap();
        map.getPlayer().setHealth(currentHealth);
        map.getPlayer().setAttack(currentDamage);
        map.getPlayer().setDefense(currentArmor);
        map.getPlayer().setKey(currentInventory);
        map.getPlayer().getItems().addAll(currentItems);
        map.getPlayer().setInventory(currentItems);
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
        healthLabel.setText("" + map.getPlayer().getHealth());

        healthLabel.setText("Health:" + map.getPlayer().getHealth());
        attackLabel.setText("Attack:" + map.getPlayer().getAttack());
        defenseLabel.setText("Defense:" + map.getPlayer().getDefense());
    }
}

