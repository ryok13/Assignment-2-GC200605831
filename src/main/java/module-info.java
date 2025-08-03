module com.georgiancollege.assignment2gc200605831 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;
    requires com.google.gson;


    opens com.georgiancollege.assignment2gc200605831 to javafx.fxml, com.google.gson;
    exports com.georgiancollege.assignment2gc200605831;
}