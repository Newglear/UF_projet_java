module com.example.javafxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires org.apache.logging.log4j.core;


    opens chavardage.GUI to javafx.fxml;
    exports chavardage.GUI;
}