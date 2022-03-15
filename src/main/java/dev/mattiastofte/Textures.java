package dev.mattiastofte;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;

public class Textures {
    PhongMaterial blocks;
    ArrayList<Float> UVMap;

    public Textures() {
        this.blocks = new PhongMaterial();
        this.blocks.setDiffuseMap(new Image(getClass().getResourceAsStream("assets/textures/blocks.png")));
        // this.blocks.setSpecularPower(0.0f);
        this.UVMap = new ArrayList<Float>();
        generateUVMap();
    }

    public void generateUVMap() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                UVMap.addAll(Arrays.asList(
                        0.125f * j, 0.125f * i,
                        0.125f * j, 0.125f * (i + 1),
                        0.125f * (j + 1), 0.125f * (i + 1),
                        0.125f * (j + 1), 0.125f * i));
            }
        }
    }
}
