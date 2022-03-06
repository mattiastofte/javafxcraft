module dev.mattiastofte {
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.mattiastofte to javafx.fxml;
    exports dev.mattiastofte;
}
