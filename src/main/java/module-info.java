module com.example.Piano {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires javafx.graphics;
    requires com.almasb.fxgl.core;
    requires com.almasb.fxgl.scene;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.io;
    requires com.almasb.fxgl.gameplay;

    opens com.example.Piano to javafx.fxml;
    exports com.example.Piano;
}