package dev.mattiastofte;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import javafx.scene.text.Text;
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

    // LOGGER
    Logger logger = new Logger();

    // SETTINGS
    Double mouseSensitivity = 0.2;

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

        // MONITOR SIZE
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Double screenWidth = screenBounds.getWidth();
        Double screenHeight = screenBounds.getHeight();

        // CREATE OBJETS
        Player player = new Player();

        // AXIS
        Box axisX = new Box(2000, 2, 2);
        Box axisY = new Box(2, 2000, 2);
        Box axisZ = new Box(2, 2, 2000);
        axisX.setMaterial(new PhongMaterial(Color.RED));
        axisY.setMaterial(new PhongMaterial(Color.GREEN));
        axisZ.setMaterial(new PhongMaterial(Color.BLUE));

        // GENERATOR
        Generator generator = new Generator();

        // CONTROLS
        Robot robot = new Robot();
        robot.mouseMove(screenWidth / 2, screenHeight / 2);

        AmbientLight sunLight = new AmbientLight();
        // PointLight pointLight = new PointLight(Color.rgb(255, 255, 255, 0.25));
        Group root = new Group();

        root.getChildren().addAll(
                axisX, axisY, axisZ, player.localAxis, sunLight, generator.chunkGroup/* chunk1.mesh */);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, true, SceneAntialiasing.DISABLED);

        scene.setCamera(player.camera);
        scene.setCursor(Cursor.NONE);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            // HANDLE PLAYER MOVEMENT INPUT (for pressed keys)
            if (key.getCode() == KeyCode.W) {
                player.setVelocityZ(12.0);
            }
            if (key.getCode() == KeyCode.S) {
                player.setVelocityZ(-12.0);
            }
            if (key.getCode() == KeyCode.A) {
                player.setVelocityX(-12.0);
            }
            if (key.getCode() == KeyCode.D) {
                player.setVelocityX(12.0);
            }
            if (key.getCode() == KeyCode.SPACE) {
                player.setVelocityY(-12.0);
            }
            if (key.getCode() == KeyCode.SHIFT) {
                player.setVelocityY(12.0);
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
        final AnimationTimer chunkLoader = new AnimationTimer() {
            int player_m = 10;
            int player_n = 10;
            int radius = 8;
            ArrayList<String> loadedChunksIndex;
            private long lastUpdate = 0;

            @Override
            public void handle(long now) { // GENERATE CHUNKS IN RADIUS AROUND PLAYER
                // DENNE FUNKER
                if (now - lastUpdate >= 28_000_000) {
                    if (player_m != player.getX().intValue() / 1024 || player_n != player.getZ().intValue() / 1024) {
                        player_m = player.getX().intValue() / 1024;
                        player_n = player.getZ().intValue() / 1024;
                        for (int m = 0; m < radius; m++) {
                            for (int n = 0; n < radius; n++) {
                                generator.getChunk(player_m + m - (radius / 2), player_n + n - (radius / 2));
                            }
                        }
                        generator.unloadChunks(player_m, player_n, radius);
                    }
                    lastUpdate = now;
                }
            };
        };

        // START ANIMATION TIMERS
        player.handleMovement.start();
        handleMouse.start();
        chunkLoader.start();

        // STAGE PROPERTIES
        stage.setTitle(String.format("%s - %s", title, version));
        stage.setScene(scene);
        stage.show();
    }
}
