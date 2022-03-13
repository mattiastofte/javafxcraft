package dev.mattiastofte;

import java.util.ArrayList;
import javafx.scene.shape.TriangleMesh;

// DATA-CLASS TO REPRESENT VOXELS
public class Voxel {
    int x;
    int y;
    int z;
    int id;
    TriangleMesh points;

    public Voxel(int x, int y, int z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
        this.points = new TriangleMesh();
    }

    public void generateMeshData(ArrayList<Boolean> neighbours, int pointIndex) {

        // CLEAR ARRAYS
        this.points.getPoints().clear();
        this.points.getFaces().clear();

        int i = pointIndex; // index for face and texture points

        // GENERATE NORTH FACE
        // GENERATE NORTH FACE
        if (!neighbours.get(0)) {
            this.points.getPoints().addAll(
                    0, 0, 64,
                    0, -64, 64,
                    64, -64, 64,
                    64, 0, 64);
            this.points.getFaces().addAll(
                    i, 10, i + 1, 11, i + 2, 6, // FIRST VERTEX
                    i, 10, i + 2, 6, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE SOUTH FACE
        if (!neighbours.get(2)) {
            this.points.getPoints().addAll(
                    64, 0, 0,
                    64, 0, 64,
                    64, -64, 64,
                    64, -64, 0);
            this.points.getFaces().addAll(
                    i, 2, i + 1, 3, i + 2, 4, // FIRST VERTEX
                    i, 2, i + 2, 4, i + 3, 1 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE WEST FACE
        if (!neighbours.get(1)) {
            this.points.getPoints().addAll(
                    0, 0, 0,
                    64, 0, 0,
                    64, -64, 0,
                    0, -64, 0);
            this.points.getFaces().addAll(
                    i, 8, i + 1, 9, i + 2, 10, // FIRST VERTEX
                    i, 8, i + 2, 10, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE WEST FACE
        if (!neighbours.get(3)) {
            this.points.getPoints().addAll(
                    0, 0, 0,
                    0, -64, 0,
                    0, -64, 64,
                    0, 0, 64);
            this.points.getFaces().addAll(
                    i, 8, i + 1, 7, i + 2, 4, // FIRST VERTEX
                    i, 8, i + 2, 4, i + 3, 3 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE UP FACE
        if (!neighbours.get(4)) {
            this.points.getPoints().addAll(
                    0, -64, 0,
                    64, -64, 0,
                    64, -64, 64,
                    0, -64, 64);
            this.points.getFaces().addAll(
                    i, 1, i + 1, 4, i + 2, 5, // FIRST VERTEX
                    i, 1, i + 2, 5, i + 3, 0 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE DOWN FACE
        if (!neighbours.get(5)) {
            this.points.getPoints().addAll(
                    64, 0, 64,
                    64, 0, 0,
                    0, 0, 0,
                    0, 0, 64);
            this.points.getFaces().addAll(
                    i, 4, i + 1, 5, i + 2, 6, // FIRST VERTEX
                    i, 4, i + 2, 6, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }
        translate();
    }

    // MOVE POINTS ACCORDING TO X, Y, Z
    private void translate() {
        for (int i = 0; i < this.points.getPoints().size(); i++) {
            if (i % 3 == 0) {
                this.points.getPoints().set(i, this.points.getPoints().get(i) + x);
            }
            if (i % 3 == 1) {
                this.points.getPoints().set(i, this.points.getPoints().get(i) + y);
            }
            if (i % 3 == 2) {
                this.points.getPoints().set(i, this.points.getPoints().get(i) + z);
            }
        }
    }
}
