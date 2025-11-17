module pkg.vms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens pkg.vms to javafx.fxml;
    exports pkg.vms;
    exports pkg.vms.model;
    opens pkg.vms.model to javafx.fxml;
}