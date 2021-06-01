module GradeBook {
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;
    opens grade.book.wrappers to java.xml.bind;
    opens grade.book.controllers to javafx.fxml;
    opens grade.book to javafx.fxml, java.xml.bind;
    exports grade.book;
}

