import javafx.animation.*;
import javafx.application.Application;
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
    Canvas canvas;



    private ImageView apple;
    private ImageView snakeHead;
    private ImageView part;

    private int snakeWidth = 13;
    private int snakeHeight = 13;
    private int snakePosX = 250;
    private int snakePosY = 250;
    private int snakeGap = 15;

    private ArrayList<ImageView> snakePart;

    private int applePosX = 70;
    private int applePosY = 70;

    private Direction currentDir = Direction.RIGHT;



    public enum Direction{
        LEFT, RIGHT, UP, DOWN
    }


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

        /*snakePart = new ImageView("images/snakePart.png");
        snakePart.setFitWidth(snakeWidth);
        snakePart.setFitHeight(snakeHeight);
        snakePart.setPreserveRatio(true);
        snakePart.setSmooth(true);
        snakePart.setLayoutX(80);
        snakePart.setLayoutY(75);*/

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




    }

    public void animationTimer(){
        Bounds bounds = canvas.getBoundsInLocal();
        timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                //System.out.println("moving diver right!");

                snakeHead.setTranslateX(snakeHead.getTranslateX() + 2);
                //part.setX(part.getX() + 2.5);

                if (snakeHead.getTranslateX() > (bounds.getMaxX() - snakeWidth) ){
                    snakeHead.setTranslateX(0);
                }
                //yourImageView.setY(yourImageView.getY() + 20.0 );

            }


        };


        timer.start();
    }

    public void snakeDirections(){

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
        //initSnakeGame();
        moveSnake();
        animationTimer();









        root.getChildren().addAll(apple,snakeHead,part);
    }


}
