import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SnakeGame extends Application {

    GraphicsContext gc;
    Scene scene;
    AnimationTimer timer;
    AnimationTimer startDir;
    Canvas canvas;


    private ImageView apple;
    private ImageView snakeHead;
    private ImageView part;

    private int snakeWidth = 13;
    private int snakeHeight = 13;
    private int snakePosX = 250;
    private int snakePosY = 250;
    private int snakeGap = 15;
    private int snakeSpeed = 2;

    private ArrayList<ImageView> snakePart;

    private int applePosX = 70;
    private int applePosY = 70;

    private Directions currentDir = Directions.RIGHT;





    public void loadIcon(){

        apple = new ImageView("images/apple-logo.png");
        apple.setFitWidth(14);
        apple.setFitHeight(14);
        apple.setPreserveRatio(true);
        apple.setSmooth(true);
        apple.setLayoutX(applePosX);
        apple.setLayoutY(applePosY);

        snakeHead = new ImageView("images/snakeHead.png");
        snakeHead.setFitWidth(snakeWidth);
        snakeHead.setFitHeight(snakeHeight);
        snakeHead.setPreserveRatio(true);
        snakeHead.setSmooth(true);
        snakeHead.setLayoutX(0);
        snakeHead.setLayoutY(snakePosY);

        snakePart = new ArrayList<>();
        part = new ImageView("images/snakePart.png");
        snakePart.add(part);
        part.setFitWidth(snakeWidth);
        part.setFitHeight(snakeHeight);
        part.setPreserveRatio(true);
        part.setSmooth(true);
        part.setLayoutX(snakePosX - snakeGap);
        part.setLayoutY(snakePosY);

    }

   /* public void locateApple() {

        int r = (int) (Math.random() * randomPos);
        appleX = ((r * dotSize));

        r = (int) (Math.random() * randomPos);
        appleY = ((r * dotSize));
    }*/


    public void addSnakeParts(){

    }


    public void moveSnake(){

        if (currentDir == Directions.RIGHT) {
            snakeHead.setX(snakeHead.getX() + snakeSpeed);
        } else if (currentDir == Directions.LEFT) {
            snakeHead.setX(snakeHead.getX() - snakeSpeed);
        } else if (currentDir == Directions.UP) {
            snakeHead.setY(snakeHead.getY() - snakeSpeed);
        } else if (currentDir == Directions.DOWN) {
            snakeHead.setY(snakeHead.getY() + snakeSpeed);
        }



    }

    public void snakeDirections(){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.UP) {
                currentDir = Directions.UP;
            } else if (key.getCode() == KeyCode.DOWN) {
                currentDir = Directions.DOWN;

            } else if (key.getCode() == KeyCode.LEFT) {
                currentDir = Directions.LEFT;

            } else if (key.getCode() == KeyCode.RIGHT) {
                currentDir = Directions.RIGHT;

            }
        });
    }

    public void animationTimer(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveSnake();
            }
        };timer.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();

        scene = new Scene(root, 600, 600, Color.web("#1f1f1f"));


        canvas = new Canvas(600, 600);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        //scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadIcon();
        addSnakeParts();
        snakeDirections();
        animationTimer();









        root.getChildren().addAll(apple,snakeHead,part);
    }


}
