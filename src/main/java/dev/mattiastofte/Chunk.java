package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Chunk {
    int[][][] data;
    TriangleMesh meshData;
    MeshView mesh;
    Textures textures;
    int m; // x-coordinate for chunks
    int n; // z-coordinate for chunks

    public Chunk(int m, int n, Textures textures) {
        this.data = new int[16][64][16];
        this.textures = textures;
        this.meshData = new TriangleMesh();
        this.mesh = new MeshView(meshData);
        this.mesh.setMaterial(textures.random);
        this.m = m;
        this.n = n;
        UVMap();
    };

    public void UVMap() {
        this.meshData.getTexCoords().addAll(
                0.0f, 0.0f, // 0
                0.0f, 0.5f, // 1
                0.0f, 1.0f, // 2
                0.25f, 1.0f, // 3
                0.25f, 0.5f, // 4
                0.25f, 0.0f, // 5
                0.5f, 0.0f, // 6
                0.5f, 0.5f, // 7
                0.5f, 1.0f, // 8
                0.75f, 1.0f, // 9
                0.75f, 0.5f, // 10
                0.75f, 0.0f // 11
        );
    }

    public void setData(int[][][] data) {
        this.data = data;
    }

    public void removeBlock(int x, int y, int z) {
        this.data[x][y][z] = 0;
    }

    public void placeBlock(int x, int y, int z, int id) {
        this.data[x][y][z] = id;
    }

    public void generateMeshData() {
        clearMesh();

        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (this.data[x][y][z] == 1) {
                        ArrayList<Boolean> neighbours = getNeighbours(x, y, z);
                        Voxel voxel = new Voxel(x * 64, y * (-64), z * 64, 1);
                        voxel.generateMeshData(neighbours, this.meshData.getPoints().size() / 3);
                        this.meshData.getPoints().addAll(voxel.points.getPoints());
                        this.meshData.getFaces().addAll(voxel.points.getFaces());
                    }
                }
            }
        }

        generateMesh();
    }

    public void generateMesh() {
        this.mesh.setMesh(this.meshData);
        System.out.println("break");
    }

    public void clearMesh() {
        this.meshData.getPoints().clear();
        this.meshData.getFaces().clear();
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