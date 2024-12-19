module com.volkerbecker.hdifferenz {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.apache.pdfbox.io;
    requires java.logging;

    opens com.volkerbecker.hdifferenz to javafx.fxml, org.apache.pdfbox.io;
    exports com.volkerbecker.hdifferenz;
}