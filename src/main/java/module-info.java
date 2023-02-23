module forestoperator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    
    requires java.desktop;
    requires java.logging;
    requires java.base;
    
    opens forestoperator to javafx.fxml;
    
    opens view to javafx.fxml;
    exports forestoperator;
    
}
