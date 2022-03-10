package dev.mattiastofte;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

import java.util.concurrent.ThreadLocalRandom;

public class Chunk {
    int[][][] data;
    Group mesh;

    public Chunk() {
        this.data = new int[16][16][16];
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 15; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (ThreadLocalRandom.current().nextInt(1, 10) == 1) {
                        this.data[x][y][z] = 1;
                    } else {
                        this.data[x][y][z] = 0;
                    }
                }
            }
        }
        this.mesh = new Group();
    };

    public void generateMesh() {
        this.mesh.getChildren().clear();
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 15; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (this.data[x][y][z] == 1) {
                        Box block = new Box(64, 64, 64);
                        block.setTranslateX(x * 64 + 32);
                        block.setTranslateY(y * 64 + 32);
                        block.setTranslateZ(z * 64 + 32);
                        block.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                        block.setDrawMode(DrawMode.LINE);
                        this.mesh.getChildren().add(block);
                    }
                }
            }
        }
    }
}
