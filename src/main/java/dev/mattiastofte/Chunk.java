package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

import java.util.concurrent.ThreadLocalRandom;

public class Chunk {
    int[][][] data;
    Group group;
    PhongMaterial texture;

    public Chunk() {
        this.texture = new PhongMaterial();
        this.texture.setDiffuseMap(new Image(getClass().getResourceAsStream("texture.jpg")));
        this.texture.setSpecularPower(0.0f);

        this.data = new int[16][64][16];
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (ThreadLocalRandom.current().nextInt(0, y + 1) == 1) {
                        this.data[x][y][z] = 1;
                    } else {
                        this.data[x][y][z] = 0;
                    }
                }
            }
        }
        this.group = new Group();
    };

    public void generateChunk() {
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (this.data[x][y][z] == 1) {
                        ArrayList<Boolean> neighbours = getNeighbours(x, y, z);
                        Voxel voxel = new Voxel(x * 64, y * (-64), z * 64, 1);
                        voxel.generateMesh(neighbours);
                        voxel.meshView.setMaterial(this.texture);
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

/*
 * Is there any particular reason this is being combined into one mesh to begin
 * with?
 * You can generate each block as a separate mesh and I don't see how that would
 * change anything- you can even render it as 6 planes
 * per cube instead, pool the planes, and handle the whole thing as a
 * 3D-array-based logical construct,
 * instead of a physical one. Destroying one block would simply call "dirty" on
 * all neighboring blocks and tell
 * them to re-check which of their sides should exist based on the new void, and
 * they pull additional planes from the pool to those spots.
 * 
 * But no. Based on your current setup, I
 * really have no idea how you can optimize it- since you're constructing a
 * single mesh to serve as the whole landscape,
 * the only real way to speed things up would be to have a duplicate cache of
 * each individual cube's mesh that went into the final result,
 * so you can start sort of from the halfway point in the process. It would
 * speed things up, but it's twice the memory usage.
 */