package dev.mattiastofte;

import javafx.scene.shape.TriangleMesh;
import java.util.ArrayList;
import java.util.Arrays;

// DATA-CLASS TO REPRESENT VOXELS

public class Voxel {
    int x;
    int y;
    int z;
    int id;
    TriangleMesh mesh;
    ArrayList<Integer> points;
    ArrayList<Float> texCoords;
    ArrayList<Integer> faces;

    public Voxel(int x, int y, int z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.mesh = new TriangleMesh();

        // UV-MAPPING
        mesh.getTexCoords().addAll(
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

    public void generateMesh(Boolean north, Boolean east, Boolean south, Boolean west, Boolean up, Boolean down) {

        // GENERATE ARRAYS
        mesh.getPoints().clear();
        mesh.getFaces().clear();
        mesh.getPoints().clear();

        int pointIndex = 0; // index for face and texture points

        // GENERATE NORTH FACE
        if (north) {
            mesh.getPoints().addAll(
                    0, 0, 64,
                    0, -64, 64,
                    64, -64, 64,
                    64, 0, 64);
            mesh.getFaces().addAll(
                    pointIndex, 10, pointIndex + 1, 11, pointIndex + 2, 6, // FIRST VERTEX
                    pointIndex, 10, pointIndex + 2, 6, pointIndex + 3, 7 // SECOND VERTEX
            );
            pointIndex += 4;
        }

        // GENERATE EAST FACE
        if (east) {
            mesh.getPoints().addAll(
                    64, 0, 0,
                    64, 0, 64,
                    64, -64, 64,
                    64, -64, 0);
            mesh.getFaces().addAll(
                    pointIndex, 2, pointIndex + 1, 3, pointIndex + 2, 4, // FIRST VERTEX
                    pointIndex, 2, pointIndex + 2, 4, pointIndex + 3, 1 // SECOND VERTEX
            );
            pointIndex += 4;
        }

        // GENERATE WEST FACE
        if (south) {
            mesh.getPoints().addAll(
                    0, 0, 0,
                    64, 0, 0,
                    64, -64, 0,
                    0, -64, 0);
            mesh.getFaces().addAll(
                    pointIndex, 8, pointIndex + 1, 9, pointIndex + 2, 10, // FIRST VERTEX
                    pointIndex, 8, pointIndex + 2, 10, pointIndex + 3, 7 // SECOND VERTEX
            );
            pointIndex += 4;
        }

        // GENERATE WEST FACE
        if (west) {
            mesh.getPoints().addAll(
                    0, 0, 0,
                    0, -64, 0,
                    0, -64, 64,
                    0, 0, 64);
            mesh.getFaces().addAll(
                    pointIndex, 8, pointIndex + 1, 7, pointIndex + 2, 4, // FIRST VERTEX
                    pointIndex, 8, pointIndex + 2, 4, pointIndex + 3, 3 // SECOND VERTEX
            );
            pointIndex += 4;
        }

        // GENERATE UP FACE
        if (up) {
            mesh.getPoints().addAll(
                    0, -64, 0,
                    64, -64, 0,
                    64, -64, 64,
                    0, -64, 64);
            mesh.getFaces().addAll(
                    pointIndex, 1, pointIndex + 1, 4, pointIndex + 2, 5, // FIRST VERTEX
                    pointIndex, 1, pointIndex + 2, 5, pointIndex + 3, 0 // SECOND VERTEX
            );
            pointIndex += 4;
        }

        // GENERATE DOWN FACE
        if (down) {
            mesh.getPoints().addAll(
                    64, 0, 64,
                    64, 0, 0,
                    0, 0, 0,
                    0, 0, 64);
            mesh.getFaces().addAll(
                    pointIndex, 4, pointIndex + 1, 5, pointIndex + 2, 6, // FIRST VERTEX
                    pointIndex, 4, pointIndex + 2, 6, pointIndex + 3, 7 // SECOND VERTEX
            );
            pointIndex += 4;
        }
    }
}
