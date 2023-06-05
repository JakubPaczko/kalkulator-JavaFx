module com.example{
    requires javafx.controls;
    requires javafx.fxml;


    opens com.spaceinvadersjavafx to javafx.fxml;
    exports com.spaceinvadersjavafx;
}