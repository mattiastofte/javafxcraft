package dev.mattiastofte;

import java.util.ArrayList;
import java.util.FormatterClosedException;

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
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
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
    String version = "1.1.0";
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

    MediaPlayer mixer;

    @Override
    public void start(Stage stage) throws Exception {

        Player player = new Player();
        // Chunk chunk1 = new Chunk();
        // chunk1.generateMesh();

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

        // TEST

        // CONTROLS
        Double mouseSensitivity = 0.2;
        Robot robot = new Robot();
        robot.mouseMove(screenWidth / 2, screenHeight / 2);

        axisX.setMaterial(new PhongMaterial(Color.RED));
        axisY.setMaterial(new PhongMaterial(Color.GREEN));
        axisZ.setMaterial(new PhongMaterial(Color.BLUE));

        AmbientLight sunLight = new AmbientLight();
        // PointLight pointLight = new PointLight(Color.rgb(255, 255, 255, 0.25));

        Group root = new Group();

        root.getChildren().addAll(
                axisX, axisY, axisZ, player.localAxis, sunLight/* chunk1.mesh */);

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                Chunk chunk = new Chunk();
                chunk.generateChunk();
                chunk.group.setTranslateX(1024 * x);
                chunk.group.setTranslateZ(1024 * z);
                root.getChildren().add(chunk.group);
            }
        }

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true, SceneAntialiasing.BALANCED);

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
