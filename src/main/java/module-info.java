module org.exemple.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.exemple.biblioteca to javafx.fxml;
    exports org.exemple.biblioteca;
}