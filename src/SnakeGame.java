import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends Application {

    Group root;
    GraphicsContext gc;
    Scene scene;
    AnimationTimer timer;
    Timeline applePosRan;
    Canvas canvas;
    Bounds bounds;
    Label gameOver;
    Pane pane;

    private int sceneWidth = 600;
    private int sceneHeight = 600;

    private ImageView apple;
    private ImageView snakeHead;
    private ImageView startGame;
    private ImageView newGame;
    private ImageView pauseGame;
    private ImageView contin;
    private int appleSize = 14;

    private int score;
    private Label scoreLabel;

    private int snakeWidth = 13;
    private int snakeHeight = 13;
    private double snakePosX = 250;
    private double snakePosY = 250;

    private double snakePartX;
    private double snakePartY;

    private int snakeGap = 15;
    private double snakeSpeed = 13;

    private ArrayList<SnakeTail> tails = new ArrayList<>();

    private int applePosX, applePosY;
    private Clip clip;

    private Directions currentDir = Directions.RIGHT;

    private List<SnakeParts> snake = new ArrayList<>();






    public void gameMenu(){

        snakeHead.setVisible(false);
       // snakePart.setVisible(false);
        apple.setVisible(false);
        pauseGame.setVisible(false);
        contin.setVisible(false);
        //gameOver.setVisible(false);
        //newGame.setVisible(false);
        timer.stop();
        //applePosRan.stop();


        clip.start();

        startGame = new ImageView("images/start.png");
        startGame.setFitWidth(180);
        startGame.setFitHeight(180);
        startGame.setPreserveRatio(true);
        startGame.setSmooth(true);
        startGame.setLayoutX(200);
        startGame.setLayoutY(200);

        startGame.setOnMouseClicked((e) -> {
            snakeHead.setVisible(true);
           // snakePart.setVisible(true);
            apple.setVisible(true);
            pauseGame.setVisible(true);
            timer.start();
            //applePosRan.play();

            startGame.setVisible(false);
            //rotateGame();

        });

    }

    public void pauseGame(){
        pauseGame = new ImageView("images/pause.png");
        pauseGame.setFitWidth(60);
        pauseGame.setFitHeight(60);
        pauseGame.setPreserveRatio(true);
        pauseGame.setSmooth(true);
        pauseGame.setLayoutX(20);
        pauseGame.setLayoutY(-4);
    }

    public void contin(){
        contin = new ImageView("images/contin.png");
        contin.setFitWidth(60);
        contin.setFitHeight(80);
        contin.setPreserveRatio(true);
        contin.setSmooth(true);
        contin.setLayoutX(20);
        contin.setLayoutY(6);
    }



    public void loadIcon(){

        apple = new ImageView("images/apple-logo.png");
        apple.setFitWidth(appleSize);
        apple.setFitHeight(appleSize);
        apple.setPreserveRatio(true);
        apple.setSmooth(true);
        apple.setLayoutX(applePosX);
        apple.setLayoutY(applePosY);

        snakeHead = new ImageView("images/snakeHead.png");
        snakeHead.setFitWidth(snakeWidth);
        snakeHead.setFitHeight(snakeHeight);
        snakeHead.setPreserveRatio(true);
        snakeHead.setSmooth(true);
        snakeHead.setLayoutX(350);
        snakeHead.setLayoutY(320);

        /*snakePart = new ImageView("images/snakePart.png");
        snakePart.setFitWidth(snakeWidth);
        snakePart.setFitHeight(snakeHeight);
        snakePart.setPreserveRatio(true);
        snakePart.setSmooth(true);
        snakePart.setLayoutX(snakePosX);
        snakePart.setLayoutY(snakePosY);*/

    }

    public void initSnakeParts(){

        SnakeParts sp = new SnakeParts(350,320);
        snake.add(sp);

        SnakeParts sp1 = new SnakeParts(350,320);
        snake.add(sp1);


        root.getChildren().addAll(snake.get(0),snake.get(1));
    }

    public void locateApple() {

        Random ran = new Random();
        applePosX = ran.nextInt(sceneWidth-appleSize);
        applePosY = ran.nextInt(sceneHeight-35);

        apple.setLayoutX(applePosX);
        apple.setLayoutY(applePosY);


        applePosRan = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Random ran = new Random();
                applePosX = ran.nextInt(sceneWidth-appleSize);
                applePosY = ran.nextInt(sceneHeight-35);
                apple.setLayoutX(applePosX);
                apple.setLayoutY(applePosY);
            }
        }));
        applePosRan.setCycleCount(Timeline.INDEFINITE);
        applePosRan.play();

    }

    public void gameSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioInputStream audioIn = AudioSystem.getAudioInputStream(SnakeGame.class.getResource("sound/snakesound.wav"));
        clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);



    }

    public void snakeBorderCollision(){

        if( snakeHead.getX() >= sceneWidth-snakeWidth)
        {
            gameOver();
            applePosRan.stop();
        }

    }

    private void snakeAppleColission() {

        if(snakeHead.getBoundsInParent().intersects(apple.getBoundsInParent())) {
            System.out.println("Collission detected");
            locateApple();
            addSnakePart();

            score += 10;
            scoreLabel.setText("Score: " + score);

            if(score == 70) {
                snakeHead.setFitWidth(26);
                snakeHead.setFitHeight(26);
            } else if (score == 120) {
                snakeHead.setFitWidth(snakeWidth);
                snakeHead.setFitHeight(snakeHeight);
            }
        }

    }

    public void rotateGame(){

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5),root);
        rotateTransition.setToAngle(90);
        rotateTransition.play();


    }


    public void moveSnake(){

        snakeAppleColission();



        for (int i = snake.size()-1; i >= 0 ; i--) {

            System.out.println(i);

            if ( i > 0 ){
                snake.get(i).setX(snake.get(i-1).getX());
                snake.get(i).setY(snake.get(i-1).getY());
            } else {
                snake.get(i).setX(snakeHead.getX());
                snake.get(i).setY(snakeHead.getY());
            }
        }

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
            if (key.getCode() == KeyCode.UP && currentDir != Directions.DOWN) {
                currentDir = Directions.UP;

            } else if (key.getCode() == KeyCode.DOWN && currentDir != Directions.UP) {
                currentDir = Directions.DOWN;

            } else if (key.getCode() == KeyCode.LEFT && currentDir != Directions.RIGHT) {
                currentDir = Directions.LEFT;

            } else if (key.getCode() == KeyCode.RIGHT && currentDir != Directions.LEFT) {
                currentDir = Directions.RIGHT;
            }

        });
    }

    public void animationTimer(){

        timer = new AnimationTimer() {

            long lastup = 0;
            @Override
            public void handle(long now) {

                if(now  - lastup >= 100000000) {

                    moveSnake();
                    snakeBorderCollision();

                    lastup = now;

                }
            }
        };
        timer.start();

    }

    public void gameOver(){

        snakeHead.setVisible(true);
        //snakePart.setVisible(true);
        apple.setVisible(true);
        //startGame.setVisible(false);
        timer.stop();
        applePosRan.stop();
        clip.stop();

        gameOver = new Label("GAME OVER");
        gameOver.setStyle("-fx-background-color: #8f0101");
        gameOver.setTextFill(Color.LIGHTGRAY);
        gameOver.setFont(new Font(10));
        gameOver.setPrefWidth(70);
        gameOver.setPrefHeight(10);
        gameOver.setLayoutX(265);
        gameOver.setLayoutY(220);
        gameOver.setAlignment(Pos.CENTER);

        newGame = new ImageView("images/newgame.png");
        newGame.setFitWidth(150);
        newGame.setFitHeight(150);
        newGame.setPreserveRatio(true);
        newGame.setSmooth(true);
        newGame.setLayoutX(220);
        newGame.setLayoutY(260);

        newGame.setOnMouseClicked((e) -> {
            snakeHead.setVisible(true);
            //snakePart.setVisible(true);
            apple.setVisible(true);
            timer.start();
            //startGame.setVisible(false);
            clip.start();

            gameOver.setVisible(false);
            newGame.setVisible(false);
        });

        RotateTransition rotate = new RotateTransition(Duration.millis(2200));
        rotate.setByAngle(360f);

        ScaleTransition scale = new ScaleTransition(Duration.millis(2200));
        scale.setByX(1.5f);
        scale.setByY(1.2f);
        scale.setAutoReverse(false);

        ParallelTransition seqT = new ParallelTransition (gameOver,rotate, scale);
        seqT.play();



    }

    public void addSnakePart() {
        SnakeParts sp = new SnakeParts((int)snake.get(snake.size()-1).getLayoutX(), (int)snake.get(snake.size()-1).getLayoutY());
        snake.add(sp);
        root.getChildren().add(snake.get(snake.size()-1));
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new Group();
        canvas = new Canvas(600, 600);
        //gc = canvas.getGraphicsContext2D();
        scene = new Scene(root, sceneWidth, sceneHeight, Color.web("#1f1f1f"));

        pane = new Pane();
        pane.setStyle("-fx-background-color: #ffecde");
        pane.setPrefWidth(sceneWidth);
        pane.setPrefHeight(30);

        scoreLabel = new Label("Score:   0");
        //scoreLabel.setStyle("-fx-background-color: #4cff3f");
        scoreLabel.setTextFill(Color.CORAL);
        scoreLabel.setFont(new Font(15));
        scoreLabel.setPrefWidth(80);
        scoreLabel.setLayoutX(500);
        scoreLabel.setLayoutY(6);






        //scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Methods


        initSnakeParts();
        gameSound();
        loadIcon();
        locateApple();
        snakeAppleColission();
        snakeDirections();
        animationTimer();
        snakeBorderCollision();
        //gameOver();
        //rotateGame();

        pauseGame();
        contin();
        //gameMenu();



        root.getChildren().addAll(canvas,pane,scoreLabel,apple,snakeHead,pauseGame,contin);
    }



}
