module ZiqiYang {
    requires java.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;
    requires opencsv;
    opens org.ziqi to javafx.graphics, javafx.fxml, javafx.controls, javafx.media;
    opens org.ziqi.control to javafx.graphics, javafx.fxml, javafx.controls, javafx.media;
    opens org.ziqi.control.screenController to javafx.graphics, javafx.fxml, javafx.controls, javafx.media;
    opens org.ziqi.model to javafx.base;
    exports org.ziqi.control to javafx.graphics, javafx.fxml, javafx.controls, javafx.media;
    exports org.ziqi.control.screenController to javafx.graphics, javafx.fxml, javafx.controls, javafx.media;
}

