module Projet {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens chavardage.GUI to javafx.fxml;

    exports chavardage.GUI;
}