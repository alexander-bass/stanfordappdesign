module com.example.stanfordappdesign {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires datafaker;


    opens com.example.stanfordappdesign to javafx.fxml;
    exports com.example.stanfordappdesign;
}