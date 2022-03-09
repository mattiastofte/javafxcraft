package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Player {
    PerspectiveCamera camera;
    ArrayList<Double> velocity;

    Group localAxis;
    Group content;
    Box testBody;
    Box axisX;
    Box axisY;
    Box axisZ;
    PhongMaterial headTexture;

    // CONSTRUCTOR

    public Player() {
        this.velocity = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
        this.testBody = new Box(30, 30, 30);

        /*
         * this.axisX = new Box(200, 1, 1);
         * this.axisY = new Box(1, 200, 1);
         * this.axisZ = new Box(1, 1, 200);
         * 
         * this.axisX.setMaterial(new PhongMaterial(Color.RED));
         * this.axisZ.setTranslateZ(100);
         * this.axisY.setMaterial(new PhongMaterial(Color.GREEN));
         * this.axisZ.setMaterial(new PhongMaterial(Color.BLUE));
         */

        this.localAxis = new XGroup();
        this.content = new XGroup();

        // SET UP CAMERA
        this.camera = new PerspectiveCamera();
        this.camera.setFieldOfView(80.0);
        this.camera.setTranslateX(-700.0);
        this.camera.setTranslateY(-400.0);
        this.camera.setTranslateZ(460.0);

        /*
         * content.getChildren().addAll(testBody, axisX, axisZ, camera);
         * localAxis.getChildren().addAll(content, axisY);
         */
        content.getChildren().addAll(testBody, camera);
        localAxis.getChildren().addAll(content);
    }

    // VELOCITY CONTROLS

    public void setVelocity(Double vx, Double vy, Double vz) {
        this.velocity.set(0, vx);
        this.velocity.set(1, vy);
        this.velocity.set(2, vz);
    }

    public void setVelocityX(Double v) {
        this.velocity.set(0, v);
    }

    public void setVelocityY(Double v) {
        this.velocity.set(1, v);
    }

    public void setVelocityZ(Double v) {
        this.velocity.set(2, v);
    }

    public ArrayList<Double> getVelocity() {
        return this.velocity;
    }

    // HANDLE MOVEMENT

    public void move(Double tx, Double ty, Double tz) {
        Translate movement = new Translate(tx, ty, tz);
        localAxis.getTransforms().addAll(movement);
    }

    public void rotatePitch(Double degrees) {
        Rotate pitch = new Rotate(degrees, Rotate.X_AXIS);
        content.getTransforms().add(pitch);
    }

    public void rotateYaw(Double degrees) {
        Rotate yaw = new Rotate(degrees, Rotate.Y_AXIS);
        localAxis.getTransforms().add(yaw);
    }

    // update position based on velocity
    public void updatePos() {
        this.move(this.velocity.get(0), this.velocity.get(1), this.velocity.get(2));
    }

    // ANIMATION TIMERS

    final AnimationTimer handleMovement = new AnimationTimer() {

        @Override
        public void handle(long arg0) {

            // HANDLE TRANSLATE MOVEMENT
            updatePos();

            /*
             * Rotate ry = new Rotate(10.0,
             * // camera rotates around its location
             * cameraPos.getX(), cameraPos.getY(), cameraPos.getZ(),
             * Rotate.Y_AXIS);
             * 
             * Translate cameraTranslate = new Translate(cameraPos.getX(), cameraPos.getY(),
             * cameraPos.getZ());
             * camera.setTranslateY(cameraPos.getY());
             * camera.setTranslateX(cameraPos.getX());
             * 
             * // cameraX += velX;
             * // camera.setTranslateX(cameraX);
             * // cameraZ += velZ;
             * // camera.setTranslateZ(cameraZ);
             * // cameraY += velY;
             * // camera.setTranslateY(cameraY);
             * camera.setRotationAxis(Rotate.Y_AXIS);
             * camera.setRotate(365.0);
             * // new Robot().mouseMove(600, 200);
             */
        };
    };
}
