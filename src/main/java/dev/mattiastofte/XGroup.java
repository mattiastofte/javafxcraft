package dev.mattiastofte;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class XGroup extends Group {
    public Rotate rx = new Rotate(0, Rotate.X_AXIS);
    public Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    public Rotate rz = new Rotate(0, Rotate.Z_AXIS);
}