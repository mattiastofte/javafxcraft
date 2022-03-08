package dev.mattiastofte;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
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
import javafx.scene.Group;
import javafx.stage.Stage;

public class Main extends Application {
    String title = "Minecraft";
    String version = "1.0.0";
    String developer = "Mattias Tofte";
    Double angleHorizontal = 0.0;
    Double angleVertical = 0.0;

    private static int WIDTH = 1400;
    private static int HEIGHT = 800;

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

        // AXIS
        Box axisX = new Box(20000, 2, 2);
        Box axisY = new Box(2, 20000, 2);
        Box axisZ = new Box(2, 2, 20000);

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

        Xform cameraAxis = new Xform();
        cameraAxis.rx.setAngle(180);

        Group root = new Group();
        root.getChildren().add(cameraAxis);
        cameraAxis.getChildren().add(player.camera);

        root.getChildren().add(box);
        root.getChildren().add(box2);
        root.getChildren().add(box3);
        root.getChildren().add(box4);
        root.getChildren().add(axisX);
        root.getChildren().add(axisY);
        root.getChildren().add(axisZ);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);

        scene.setCamera(player.camera);

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
                angleVertical -= 10.0;
                cameraAxis.rx.setAngle(angleVertical);
            }
            if (key.getCode() == KeyCode.DOWN) {
                angleVertical += 10.0;
                cameraAxis.rx.setAngle(angleVertical);
            }
            if (key.getCode() == KeyCode.LEFT) {
                angleHorizontal -= 10.0;
                cameraAxis.ry.setAngle(angleHorizontal);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                angleHorizontal += 10.0;
                cameraAxis.ry.setAngle(angleHorizontal);
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

        final AnimationTimer orbit = new AnimationTimer() {

            @Override
            public void handle(long arg0) {

                // HANDLE TRANSLATE MOVEMENT
                box4.getTransforms().add(new Rotate(2.0, 0.0, 200.0, 200.0));
            };
        };

        // START ANIMATION TIMERS
        player.handleMovement.start();
        orbit.start();

        // STAGE PROPERTIES
        stage.setTitle(String.format("%s - %s", title, version));
        stage.setScene(scene);
        stage.show();
    }
}
