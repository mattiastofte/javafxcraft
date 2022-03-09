package dev.mattiastofte;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Box;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    String title = "Minecraft";
    String version = "1.0.0";
    String developer = "Mattias Tofte";

    int currentFrame;

    public static int SCREEN_WIDTH = 1400;
    public static int SCREEN_HEIGHT = 800;

    // MOUSE VARIABLES
    Double deltaXMouseInput = 0.0;
    Double deltaYMouseInput = 0.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // CREATE OBJECTS
        Box box = new Box(50, 50, 50);
        Box box2 = new Box(50, 50, 50);
        Box box3 = new Box(50, 50, 50);
        Box box4 = new Box(50, 50, 50);
        Player player = new Player();

        // SCENE CAMERA
        PerspectiveCamera sceneCamera = new PerspectiveCamera();
        sceneCamera.setFieldOfView(80.0);
        sceneCamera.setTranslateX(-200.0);
        sceneCamera.setTranslateY(-300.0);
        sceneCamera.setTranslateZ(-300.0);

        // AXIS
        Box axisX = new Box(2000, 2, 2);
        Box axisY = new Box(2, 2000, 2);
        Box axisZ = new Box(2, 2, 2000);

        // MONITOR SIZE
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Double screenWidth = screenBounds.getWidth();
        Double screenHeight = screenBounds.getHeight();

        // CONTROLS
        Double mouseSensitivity = 0.2;
        Robot robot = new Robot();
        robot.mouseMove(screenWidth / 2, screenHeight / 2);

        box.getTransforms().add(new Translate(100, 300, 0));
        box2.getTransforms().add(new Translate(100, 100, 0));
        box3.getTransforms().add(new Translate(150, 50, 0));
        box4.getTransforms().add(new Translate(100, 350, 0));
        box.setMaterial(new PhongMaterial(Color.BLUE));
        box2.setMaterial(new PhongMaterial(Color.YELLOW));
        box3.setMaterial(new PhongMaterial(Color.GREEN));
        box4.setMaterial(new PhongMaterial(Color.ORANGE));

        axisX.setMaterial(new PhongMaterial(Color.RED));
        axisY.setMaterial(new PhongMaterial(Color.GREEN));
        axisZ.setMaterial(new PhongMaterial(Color.BLUE));

        Group root = new Group();

        root.getChildren().addAll(
                box, box2, box3, box4, axisX, axisY, axisZ, player.localAxis);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true);

        scene.setCamera(player.camera);
        scene.setCursor(Cursor.NONE);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            // HANDLE PLAYER MOVEMENT INPUT (for pressed keys)
            if (key.getCode() == KeyCode.W) {
                player.setVelocityZ(8.0);
            }
            if (key.getCode() == KeyCode.S) {
                player.setVelocityZ(-8.0);
            }
            if (key.getCode() == KeyCode.A) {
                player.setVelocityX(-8.0);
            }
            if (key.getCode() == KeyCode.D) {
                player.setVelocityX(8.0);
            }
            if (key.getCode() == KeyCode.SPACE) {
                player.setVelocityY(-8.0);
            }
            if (key.getCode() == KeyCode.SHIFT) {
                player.setVelocityY(8.0);
            }
            if (key.getCode() == KeyCode.UP) {
                player.rotatePitch(10.0);
            }
            if (key.getCode() == KeyCode.DOWN) {
                player.rotatePitch(-10.0);
            }
            /*
             * if (key.getCode() == KeyCode.LEFT) {
             * player.rotateYaw(-10.0);
             * }
             * if (key.getCode() == KeyCode.RIGHT) {
             * player.rotateYaw(10.0);
             * }
             */
            if (key.getCode() == KeyCode.C) {
                if (scene.getCamera() == player.camera) {
                    scene.setCamera(sceneCamera);
                } else {
                    scene.setCamera(player.camera);
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {

            // HANDLE PLAYER MOVEMENT INPUT (for pressed keys)
            if (key.getCode() == KeyCode.W) {
                player.setVelocityZ(0.0);
            }
            if (key.getCode() == KeyCode.S) {
                player.setVelocityZ(0.0);
            }
            if (key.getCode() == KeyCode.A) {
                player.setVelocityX(0.0);
            }
            if (key.getCode() == KeyCode.D) {
                player.setVelocityX(0.0);
            }
            if (key.getCode() == KeyCode.SPACE) {
                player.setVelocityY(0.0);
            }
            if (key.getCode() == KeyCode.SHIFT) {
                player.setVelocityY(0.0);
            }
        });

        final AnimationTimer frameCounter = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                currentFrame++;
                if (currentFrame == 60) {
                    currentFrame = 0;
                }
            };
        };
        final AnimationTimer handleMouse = new AnimationTimer() {
            Double oldX = robot.getMouseX();
            Double oldY = robot.getMouseY();
            Double newX, newY;
            Double deltaMouseX;
            Double deltaMouseY;

            @Override
            public void handle(long arg0) {
                newX = robot.getMouseX();
                newY = robot.getMouseY();

                if (newX < 50 || newX > screenWidth - 50 || newY < 50 || newY > screenHeight - 50) {
                    robot.mouseMove(screenWidth / 2, screenHeight / 2);
                    oldX = robot.getMouseX();
                    oldY = robot.getMouseY();
                    newX = robot.getMouseX();
                    newY = robot.getMouseY();
                } else {
                    deltaMouseX = newX - oldX;
                    deltaMouseY = newY - oldY;
                    oldX = newX;
                    oldY = newY;
                    player.rotatePitch(-deltaMouseY * mouseSensitivity);
                    player.rotateYaw(deltaMouseX * mouseSensitivity);
                }

            };
        };

        // START ANIMATION TIMERS
        player.handleMovement.start();
        handleMouse.start();
        frameCounter.start();

        // STAGE PROPERTIES
        stage.setTitle(String.format("%s - %s", title, version));
        stage.setScene(scene);
        stage.show();
    }
}
