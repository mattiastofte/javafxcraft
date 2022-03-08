package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Player {
    PerspectiveCamera camera;
    ArrayList<Double> velocity;

    // CONSTRUCTOR

    public Player() {
        this.velocity = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
        this.camera = new PerspectiveCamera();
        this.camera.setFieldOfView(80.0);
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

    public void moveX(Double tx) {
        camera.setTranslateX(camera.getTranslateX() + tx);
    }

    public void moveY(Double ty) {
        camera.setTranslateY(camera.getTranslateY() + ty);
    }

    public void moveZ(Double tz) {
        camera.setTranslateZ(camera.getTranslateZ() + tz);
    }

    public void rotateHorizontally(Double degrees) {
        Rotate rx = new Rotate(degrees, camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ() + 10.0);
        camera.getTransforms().add(rx);
    }

    public void rotateVertically(Double degrees) {
        Rotate rx = new Rotate(degrees, Rotate.X_AXIS);
        camera.getTransforms().add(rx);
    }

    // update position based on velocity
    public void updatePos() {
        this.moveX(this.velocity.get(0));
        this.moveY(this.velocity.get(1));
        this.moveZ(this.velocity.get(2));
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
