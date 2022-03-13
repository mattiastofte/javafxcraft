package dev.mattiastofte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Group;

public class Generator {
    ArrayList<Chunk> loadedChunks;
    Group chunkGroup;
    Textures textures = new Textures();
    String map;

    public Generator() {
        this.loadedChunks = new ArrayList<Chunk>();
        this.map = "test";
        this.chunkGroup = new Group();
    }

    // FILE WRITER
    public void getChunk(int m, int n) {
        try { // CREATE/OPEN CHUNK FILE
            File file = new File("src/main/java/dev/mattiastofte/data/map/" + map + "/" + String.valueOf(m) + "-"
                    + String.valueOf(n) + ".txt"); // " + map + String.valueOf(m)
            // + "-" + String.valueOf(n)
            if (file.createNewFile()) {
                Chunk chunk = generateChunk(m, n);
                loadedChunks.add(chunk); // ADD CHUNKS TO LOADED CHUNKS
                chunkGroup.getChildren().add(chunk.mesh);
                System.out.println("[GENERATOR] Succesfully generated new chunk at " + file.getName());
            } else {
                System.out.println("[GENERATOR] Chunk already exists, loading chunk at " + String.valueOf(m) + ", "
                        + String.valueOf(n) + "..");
            }
        } catch (IOException e) {
            System.out.println("[ERROR] An error occurred.");
            e.printStackTrace();
        }
    }

    // GENERATE A NEW CHUNK
    public Chunk generateChunk(int m, int n) {
        Chunk chunk = new Chunk(m, n, textures);
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 63; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (y < 10) {
                        chunk.data[x][y][z] = 1;
                    } else {
                        chunk.data[x][y][z] = 0;
                    }
                }
            }
        }
        chunk.generateMeshData(); // GENERATE CHUNK MESH
        chunk.mesh.setTranslateX(1024 * chunk.m);
        chunk.mesh.setTranslateZ(1024 * chunk.n);
        return chunk;
    }

    // LOAD CHUNK FROM FILE
    // public void loadChunk() {

    // }

    // GENERATE EXAMPLE WORLD
    // public void generateExample() {

    // }

    // GET CHUNK ARRAY
    public ArrayList<Chunk> getChunks() {
        return this.loadedChunks;
    }
}
