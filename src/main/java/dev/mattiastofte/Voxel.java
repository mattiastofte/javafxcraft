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
        int j = id - 1; // texture index

        // GENERATE NORTH FACE
        if (!neighbours.get(0)) {
            this.points.getPoints().addAll(
                    0, 0, 64,
                    0, -64, 64,
                    64, -64, 64,
                    64, 0, 64);
            this.points.getFaces().addAll(
                    i, 0 + (4 * j), i + 1, 2 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j)// SECOND VERTEX
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
                    i, 0 + (4 * j), i + 1, 1 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j)// SECOND VERTEX
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
                    i, 0 + (4 * j), i + 1, 1 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j)// SECOND VERTEX
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
                    i, 0 + (4 * j), i + 1, 1 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j) // SECOND VERTEX
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
                    i, 0 + (4 * j), i + 1, 1 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j)// SECOND VERTEX
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
                    i, 0 + (4 * j), i + 1, 1 + (4 * j), i + 2, 2 + (4 * j), // FIRST VERTEX
                    i, 0 + (4 * j), i + 2, 2 + (4 * j), i + 3, 3 + (4 * j) // SECOND VERTEX
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
