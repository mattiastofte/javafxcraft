package dev.mattiastofte;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;

public class Textures {
    PhongMaterial random;

    public Textures() {
        this.random = new PhongMaterial();
        this.random.setDiffuseMap(new Image(getClass().getResourceAsStream("texture.jpg")));
        this.random.setSpecularPower(0.0f);
    }
}
