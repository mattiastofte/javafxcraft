package dev.mattiastofte;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Main extends Application {
    String title = "Minecraft";
    String version = "1.0.0";
    String developer = "Mattias Tofte";
    Double cameraX = 10.0;
    Double cameraY = 0.0;
    Double cameraZ = 10.0;
    Double velX = 0.0;
    Double velZ = 0.0;
    Double velY = 0.0;
    private static int WIDTH = 1400;
    private static int HEIGHT = 800;

    @Override
    public void start(Stage stage) throws Exception {
        Box box = new Box(50, 50, 50);
        Box box2 = new Box(50, 50, 50);
        Box box3 = new Box(50, 50, 50);
        Box box4 = new Box(50, 50, 50);
        TriangleMesh mesh = new TriangleMesh();
        // Vertex coordinates
        float h = 210; // Height (Y)
        float w = 245; // Width (X)
        float d = 125; // Depth (Z)

        mesh.getPoints().addAll(
                0, -h / 2, 0, // 0
                w / 2, h / 2, d / 2, // 1
                w / 2, h / 2, -d / 2 // 2
        );

        box.setTranslateX(100.0);
        box.setTranslateY(300.0);
        box2.setTranslateX(200.0);
        box2.setTranslateY(200.0);
        box3.setTranslateX(250.0);
        box3.setTranslateY(200.0);
        box4.setTranslateX(250.0);
        box4.setTranslateY(100.0);
        box.setMaterial(new PhongMaterial(Color.BLUE));
        box2.setMaterial(new PhongMaterial(Color.YELLOW));
        box3.setMaterial(new PhongMaterial(Color.GREEN));
        box4.setMaterial(new PhongMaterial(Color.ORANGE));

        Group root = new Group();
        root.getChildren().add(box);
        root.getChildren().add(box2);
        root.getChildren().add(box3);
        root.getChildren().add(box4);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(95.0);
        scene.setCamera(camera);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                velZ = 4.0;
            }
            if (key.getCode() == KeyCode.S) {
                velZ = -4.0;
            }
            if (key.getCode() == KeyCode.A) {
                velX = -4.0;
            }
            if (key.getCode() == KeyCode.D) {
                velX = 4.0;
            }
            if (key.getCode() == KeyCode.SPACE) {
                velY = -4.0;
            }
            if (key.getCode() == KeyCode.SHIFT) {
                velY = 4.0;
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                velZ = 0.0;
            }
            if (key.getCode() == KeyCode.S) {
                velZ = 0.0;
            }
            if (key.getCode() == KeyCode.A) {
                velX = 0.0;
            }
            if (key.getCode() == KeyCode.D) {
                velX = 0.0;
            }
            if (key.getCode() == KeyCode.SPACE) {
                velY = 0.0;
            }
            if (key.getCode() == KeyCode.SHIFT) {
                velY = 0.0;
            }
        });

        final AnimationTimer cameraMovement = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                cameraX += velX;
                camera.setTranslateX(cameraX);
                cameraZ += velZ;
                camera.setTranslateZ(cameraZ);
                cameraY += velY;
                camera.setTranslateY(cameraY);
            }
        };

        cameraMovement.start();
        stage.setTitle(String.format("%s - %s", title, version));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
