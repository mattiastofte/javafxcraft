package dev.mattiastofte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.Group;

public class Generator {
    ArrayList<Chunk> loadedChunks;
    ArrayList<String> loadedChunksPos;
    Group chunkGroup;
    Textures textures = new Textures();
    String map;
    DataHandler dataHandler;
    OpenSimplex2S noise;
    long seed;
    private static final double FEATURE_SIZE = 128;

    public Generator() {
        this.map = "map1";
        this.loadedChunks = new ArrayList<Chunk>();
        this.loadedChunksPos = new ArrayList<String>();
        this.chunkGroup = new Group();
        this.dataHandler = new DataHandler(map);
        this.noise = new OpenSimplex2S();
        this.seed = 12345;
    }

    // GET CHUNK
    public void getChunk(int m, int n) {
        if (!checkIfLoaded(m, n)) {
            if (dataHandler.alreadyGenerated(m, n)) {
                Byte[][][] data = dataHandler.readChunkData(m, n);
                loadChunk(m, n, data);
            } else {
                Chunk chunk = generateChunk(m, n);
                dataHandler.writeChunkData(m, n, chunk.data);
            }
        }
    }

    // GENERATE CHUNK
    public Chunk generateChunk(int m, int n) {
        System.out.println(String.format("[GENERATOR] Generated chunk to chunks.dat at location m: %s n: %s", m, n));
        Chunk chunk = new Chunk(m, n, textures);
        for (int x = 0; x <= 15; x++) {
            for (int z = 0; z <= 15; z++) {
                Float heightMultiplier = this.noise.noise2(seed, (double) (((m * 16) + x) / FEATURE_SIZE),
                        (double) (((n * 16) + z) / FEATURE_SIZE));
                int height = (int) ((heightMultiplier + 1) * 26 + 5);
                for (int y = 0; y <= 63; y++) {
                    if (y < height) {
                        if (y < height - 1) {
                            chunk.data[x][y][z] = (byte) (ThreadLocalRandom.current().nextInt(1, 3));
                        } else {
                            chunk.data[x][y][z] = 5;
                        }
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
        loadedChunksPos.add(String.format("%s:%s", chunk.m, chunk.n));
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
        loadedChunksPos.add(String.format("%s:%s", chunk.m, chunk.n));
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

    public void unloadChunk(Chunk chunk) {
        loadedChunks.remove(chunk);
        loadedChunksPos.remove(String.format("%s:%s", chunk.m, chunk.n));
        chunkGroup.getChildren().remove(chunk.mesh);
    }

    public void unloadChunks(int player_m, int player_n, int radius) {
        Iterator<Chunk> loadedChunksIterator = loadedChunks.listIterator();
        while (loadedChunksIterator.hasNext()) {
            Chunk nextChunk = loadedChunksIterator.next();
            if (nextChunk.m < player_m - radius / 2 || nextChunk.m > player_m + radius / 2) {
                chunkGroup.getChildren().remove(nextChunk.mesh);
                loadedChunksIterator.remove();
            } else if (nextChunk.n < player_n - radius / 2 || nextChunk.n > player_n + radius / 2) {
                chunkGroup.getChildren().remove(nextChunk.mesh);
                loadedChunksIterator.remove();
            }
        }
        getLoadedChunksPos();
    }

    public void getLoadedChunksPos() {
        loadedChunksPos.clear();
        for (int i = 0; i < loadedChunks.size(); i++) {
            loadedChunksPos.add(String.format("%s:%s", loadedChunks.get(i).m, loadedChunks.get(i).n));
        }
    }

    // GET CHUNK ARRAY
    public ArrayList<Chunk> getChunks() {
        return this.loadedChunks;
    }

    private Boolean checkIfLoaded(int m, int n) {
        return this.loadedChunksPos.contains(String.format("%s:%s", m, n));
    }
}
