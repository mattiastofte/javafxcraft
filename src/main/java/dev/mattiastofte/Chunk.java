package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;

import java.util.concurrent.ThreadLocalRandom;

public class Chunk {
    int[][][] data;
    Group group;
    Textures textures;

    public Chunk(Textures textures) {
        this.data = new int[16][64][16];
        this.textures = textures;
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (y < 10) {
                        this.data[x][y][z] = 1;
                    } else {
                        this.data[x][y][z] = 0;
                    }
                }
            }
        }
        this.group = new Group();
    };

    public void generateChunk(int[][][] chunkData) {
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (chunkData[x][y][z] == 1) {
                        ArrayList<Boolean> neighbours = getNeighbours(x, y, z);
                        Voxel voxel = new Voxel(x * 64, y * (-64), z * 64, 1);
                        voxel.generateMesh(neighbours);
                        voxel.meshView.setMaterial(this.textures.random);
                        group.getChildren().add(voxel.meshView);
                    }
                }
            }
        }
    }

    // SIMPLE CULLING ALGORITHM
    public ArrayList<Boolean> getNeighbours(int x, int y, int z) {

        ArrayList<Integer> deltas = new ArrayList<>(Arrays.asList(
                0, 0, 1, // north
                0, 0, -1, // south
                1, 0, 0, // east
                -1, 0, 0, // west
                0, 1, 0, // up
                0, -1, 0 // down
        ));

        ArrayList<Boolean> neighbours = new ArrayList<>(Arrays.asList(false, false, false, false, false, false));
        for (int i = 0; i < 6; i++) {
            try {
                if (this.data[x + deltas.get(3 * i)][y + deltas.get((3 * i) + 1)][z + deltas.get((3 * i) + 2)] == 1) {
                    neighbours.set(i, true);
                }
            } catch (Exception e) {
            }
        }
        return neighbours;
    }
}