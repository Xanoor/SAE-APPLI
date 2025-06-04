module org.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.javafx to javafx.fxml;
    exports vue;
    exports modele;
    exports controleur;
}