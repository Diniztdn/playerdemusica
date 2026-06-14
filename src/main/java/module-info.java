module com.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.musicplayer to javafx.fxml;
    exports com.musicplayer;
}
