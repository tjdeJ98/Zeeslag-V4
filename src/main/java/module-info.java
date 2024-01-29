module com.example.isy2zeeslagv4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.example.isy2zeeslagv4.controller to javafx.fxml;
    exports com.example.isy2zeeslagv4;
}