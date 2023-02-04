module com.example.cs307_proj2_final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.example.cs307_proj2_final to javafx.fxml;
    exports com.example.cs307_proj2_final;
}