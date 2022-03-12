package dev.mattiastofte;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import java.util.ArrayList;

// DATA-CLASS TO REPRESENT VOXELS
public class Voxel {
    int x;
    int y;
    int z;
    int id;
    TriangleMesh mesh;
    MeshView meshView;

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

    public void generateMesh(ArrayList<Boolean> neighbours) {

        // GENERATE ARRAYS
        mesh.getPoints().clear();
        mesh.getFaces().clear();
        mesh.getPoints().clear();

        int i = 0; // index for face and texture points

        // GENERATE NORTH FACE
        if (!neighbours.get(0)) {
            mesh.getPoints().addAll(
                    0, 0, 64,
                    0, -64, 64,
                    64, -64, 64,
                    64, 0, 64);
            mesh.getFaces().addAll(
                    i, 10, i + 1, 11, i + 2, 6, // FIRST VERTEX
                    i, 10, i + 2, 6, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE SOUTH FACE
        if (!neighbours.get(2)) {
            mesh.getPoints().addAll(
                    64, 0, 0,
                    64, 0, 64,
                    64, -64, 64,
                    64, -64, 0);
            mesh.getFaces().addAll(
                    i, 2, i + 1, 3, i + 2, 4, // FIRST VERTEX
                    i, 2, i + 2, 4, i + 3, 1 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE WEST FACE
        if (!neighbours.get(1)) {
            mesh.getPoints().addAll(
                    0, 0, 0,
                    64, 0, 0,
                    64, -64, 0,
                    0, -64, 0);
            mesh.getFaces().addAll(
                    i, 8, i + 1, 9, i + 2, 10, // FIRST VERTEX
                    i, 8, i + 2, 10, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE WEST FACE
        if (!neighbours.get(3)) {
            mesh.getPoints().addAll(
                    0, 0, 0,
                    0, -64, 0,
                    0, -64, 64,
                    0, 0, 64);
            mesh.getFaces().addAll(
                    i, 8, i + 1, 7, i + 2, 4, // FIRST VERTEX
                    i, 8, i + 2, 4, i + 3, 3 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE UP FACE
        if (!neighbours.get(4)) {
            mesh.getPoints().addAll(
                    0, -64, 0,
                    64, -64, 0,
                    64, -64, 64,
                    0, -64, 64);
            mesh.getFaces().addAll(
                    i, 1, i + 1, 4, i + 2, 5, // FIRST VERTEX
                    i, 1, i + 2, 5, i + 3, 0 // SECOND VERTEX
            );
            i += 4;
        }

        // GENERATE DOWN FACE
        if (!neighbours.get(5)) {
            mesh.getPoints().addAll(
                    64, 0, 64,
                    64, 0, 0,
                    0, 0, 0,
                    0, 0, 64);
            mesh.getFaces().addAll(
                    i, 4, i + 1, 5, i + 2, 6, // FIRST VERTEX
                    i, 4, i + 2, 6, i + 3, 7 // SECOND VERTEX
            );
            i += 4;
        }

        translate();
        this.meshView = new MeshView(mesh);

        // ADD CORRECT TEXTURE BY BLOCK ID
        // if (this.id == 1) {

        // }
    }

    // MOVE POINTS ACCORDING TO X, Y, Z
    public void translate() {
        for (int i = 0; i < mesh.getPoints().size(); i++) {
            if (i % 3 == 0) {
                mesh.getPoints().set(i, mesh.getPoints().get(i) + x);
            }
            if (i % 3 == 1) {
                mesh.getPoints().set(i, mesh.getPoints().get(i) + y);
            }
            if (i % 3 == 2) {
                mesh.getPoints().set(i, mesh.getPoints().get(i) + z);
            }
        }
    }
}
