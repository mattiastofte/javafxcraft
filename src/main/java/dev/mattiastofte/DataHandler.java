package dev.mattiastofte;

import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;
import java.nio.ByteBuffer;

import java.io.File;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataHandler {
    String chunk_path;
    String offsets_path;
    String main_path = "src/main/java/dev/mattiastofte";;
    int chunk_count = 0;

    //
    HashMap<String, Long> chunk_map = new HashMap<>();

    // loadChunk()

    public DataHandler(String folder_name) {
        // Excpected files in world folder
        // 1) chunks.dat -> contains fixed arrays of block ids
        // 2) offsets.dat -> contains a map of x:y:offset values short:short:long

        this.chunk_path = String.format("%s/data/maps/%s/%s", main_path, folder_name, "chunks.dat");
        this.offsets_path = String.format("%s/data/maps/%s/%s", main_path, folder_name, "offsets.dat");

        this.readOffsets();
    }

    private void readOffsets() {
        File file = new File(this.offsets_path);
        long total_size = file.length();

        // if the total size is not modul of 12
        // then the offsets file is missing data
        // x[2] + y[2] + o[8] = 12

        if (total_size % 12 != 0) {
            System.out.println("\u001b[91m[WARNING] TRUNCATED DATA INVALID SIZE!\u001b[0m");
            System.exit(1);
        }

        long total_chunks = total_size / 12;
        this.chunk_count = (int) total_chunks;

        try {
            FileInputStream fis = new FileInputStream(this.offsets_path);

            for (int i = 0; i < total_chunks; i++) {
                byte[] buffer = new byte[12];
                fis.read(buffer);

                // get the buffer
                byte x_buf[] = Arrays.copyOfRange(buffer, 0, 2);
                byte y_buf[] = Arrays.copyOfRange(buffer, 2, 4);
                byte o_buf[] = Arrays.copyOfRange(buffer, 4, 12);

                // System.out.println(y_buf[0]);
                // System.out.println(y_buf[1]);
                short x = ByteBuffer.wrap(x_buf).getShort();
                short y = ByteBuffer.wrap(y_buf).getShort();
                long off = ByteBuffer.wrap(o_buf).getLong();

                // sets the hashmap key for chunks as "x:y" strings
                // the value the hashmap returns will be their offset
                String hash_key = String.format("%s:%s", x, y);
                this.chunk_map.put(hash_key, off);
            }
            fis.close();
        }

        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public Boolean alreadyGenerated(int m, int n) {
        return this.chunk_map.containsKey(String.format("%s:%s", m, n));
    }

    public long getChunkOffset(int m, int n) {
        return this.chunk_map.get(String.format("%s:%s", m, n));
    }

    public Byte[][][] readChunkFromOffset(long offset) {
        // Byte[16][64][16]

        byte[] buffer = new byte[16384];

        try {
            FileInputStream fis = new FileInputStream(this.chunk_path);
            fis.skip(offset);
            fis.read(buffer);
            fis.close();
        }

        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        Byte[][][] chunk_data = new Byte[16][64][16];

        int byte_index = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 64; y++) {
                for (int z = 0; z < 16; z++) {
                    chunk_data[x][y][z] = buffer[byte_index];
                    byte_index++;
                }
            }
        }

        return chunk_data;
    }

    public Byte[][][] readChunkData(int m, int n) {
        long offset = this.getChunkOffset(m, n);
        return this.readChunkFromOffset(offset);
    }

    public void writeChunkData(int m, int n, Byte[][][] data) {
        // Current Bug
        // chunks not writing correclty maybe due to an
        // incorrect placement int this function or getting
        // the wrong hashmap value

        System.out.println(String.format("[DATAHANDLER] chunk written at m: %s n: %s", m, n));

        byte[] chunk_bytes = new byte[16384];

        int byte_index = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 64; y++) {
                for (int z = 0; z < 16; z++) {
                    chunk_bytes[byte_index] = data[x][y][z];
                    byte_index++;
                }
            }
        }

        try {
            long offset;

            if (this.alreadyGenerated(m, n) != true) {
                offset = this.chunk_count * 16384;
                this.chunk_map.put(String.format("%s:%s", m, n), offset);
            } else {
                offset = this.getChunkOffset(m, n);
            }

            FileOutputStream fos_chunks = new FileOutputStream(this.chunk_path);

            FileChannel channel = fos_chunks.getChannel();
            channel.position(offset);

            fos_chunks.write(chunk_bytes);
            fos_chunks.close();
        }

        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        File offset_file = new File(this.offsets_path);
        offset_file.delete();

        try {
            FileOutputStream fos_offset = new FileOutputStream(this.offsets_path);

            for (String key : this.chunk_map.keySet()) {
                String[] key_split = key.split(":");

                short x = Short.parseShort(key_split[0]);
                short y = Short.parseShort(key_split[1]);

                long offset = this.getChunkOffset(x, y);

                byte[] x_buf = ByteBuffer.allocate(2).putShort(x).array();
                byte[] y_buf = ByteBuffer.allocate(2).putShort(y).array();
                byte[] o_buf = ByteBuffer.allocate(8).putLong(offset).array();

                fos_offset.write(x_buf);
                fos_offset.write(y_buf);
                fos_offset.write(o_buf);
            }

            fos_offset.close();
        }

        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}