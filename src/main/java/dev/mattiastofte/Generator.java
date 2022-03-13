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
    DataHandler dataHandler;

    public Generator() {
        this.map = "map1";
        this.loadedChunks = new ArrayList<Chunk>();
        this.chunkGroup = new Group();
        this.dataHandler = new DataHandler(map);
    }

    // GET CHUNK
    public void getChunk(int m, int n) {
        if (dataHandler.alreadyGenerated(m, n)) {
            Byte[][][] data = dataHandler.readChunkData(m, n);
            loadChunk(m, n, data);
        } else {
            Chunk chunk = generateChunk(m, n);
            dataHandler.writeChunkData(m, n, chunk.data);
        }
    }

    // GENERATE CHUNK
    public Chunk generateChunk(int m, int n) {
        System.out.println(String.format("[GENERATOR] Generated chunk to chunks.dat at location m: %s n: %s", m, n));
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
        chunk.generateMeshPoints(); // GENERATE CHUNK MESH
        chunk.generateMesh();
        chunk.mesh.setTranslateX(1024 * chunk.m);
        chunk.mesh.setTranslateZ(1024 * chunk.n);
        chunkGroup.getChildren().add(chunk.mesh);
        loadedChunks.add(chunk);
        return chunk;
    }

    public void loadChunk(int m, int n, Byte[][][] data) {
        System.out.println(String.format("[GENERATOR] Loaded chunk (%s,%s)", m, n));
        Chunk chunk = new Chunk(m, n, textures);
        chunk.setData(data);
        chunk.generateMeshPoints();
        chunk.generateMesh();
        chunk.mesh.setTranslateX(1024 * chunk.m);
        chunk.mesh.setTranslateZ(1024 * chunk.n);
        chunkGroup.getChildren().add(chunk.mesh);
        loadedChunks.add(chunk);
    }

    public void unloadChunk(int m, int n) {
        Boolean didUnload = false;

        for (int i = 0; i > loadedChunks.size(); i++) {
            if (loadedChunks.get(i).m == m) {
                if (loadedChunks.get(i).n == n) {
                    loadedChunks.remove(i);
                    didUnload = true;
                }
            }
        }

        if (!didUnload) {
            throw new IllegalArgumentException(String.format("[GENERATOR] Could not unload chunk (%s,%s)", m, n));
        }
    }

    // GET CHUNK ARRAY
    public ArrayList<Chunk> getChunks() {
        return this.loadedChunks;
    }
}
