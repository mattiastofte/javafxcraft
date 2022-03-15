package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Chunk {
    Byte[][][] data;
    TriangleMesh meshPoints;
    MeshView mesh;
    Textures textures;
    int m; // x-coordinate for chunks
    int n; // z-coordinate for chunks

    public Chunk(int m, int n, Textures textures) {
        this.data = new Byte[16][64][16];
        this.textures = textures;
        this.meshPoints = new TriangleMesh();
        this.mesh = new MeshView(meshPoints);
        this.mesh.setMaterial(textures.blocks);
        this.m = m;
        this.n = n;
        loadUVMap();
    };

    public void loadUVMap() {
        for (int i = 0; i < this.textures.UVMap.size(); i++) {
            this.meshPoints.getTexCoords().addAll(textures.UVMap.get(i));
        }
    }

    public void removeBlock(int x, int y, int z) {
        this.data[x][y][z] = 0;
    }

    public void placeBlock(int x, int y, int z, Byte id) {
        this.data[x][y][z] = id;

    }

    public void setData(Byte[][][] data) {
        this.data = data;
    }

    // GENERATES THE MESH DATA
    public void generateMeshPoints() {
        clearMesh();
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (this.data[x][y][z] != 0) {
                        ArrayList<Boolean> neighbours = getNeighbours(x, y, z);
                        Voxel voxel = new Voxel(x * 64, y * (-64), z * 64, this.data[x][y][z]);
                        voxel.generateMeshData(neighbours, this.meshPoints.getPoints().size() / 3);
                        this.meshPoints.getPoints().addAll(voxel.points.getPoints());
                        this.meshPoints.getFaces().addAll(voxel.points.getFaces());
                    }
                }
            }
        }
    }

    // GENERATES MESH
    public void generateMesh() {
        generateMeshPoints();
        this.mesh.setMesh(this.meshPoints);
    }

    // CLEARS MESH DATA
    public void clearMesh() {
        this.meshPoints.getPoints().clear();
        this.meshPoints.getFaces().clear();
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
                if (this.data[x + deltas.get(3 * i)][y + deltas.get((3 * i) + 1)][z + deltas.get((3 * i) + 2)] != 0) {
                    neighbours.set(i, true);
                }
            } catch (Exception e) {
            }
        }
        return neighbours;
    }
}