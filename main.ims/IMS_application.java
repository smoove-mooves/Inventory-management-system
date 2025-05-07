package main.ims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.ZoneId;

/** This class crates an app that manages a company's inventory.*/

public class IMS_application extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMS_application.class.getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method. This is the first method that gets called when you run the program*/

    public static void main(String[] args) {

        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);

        Part p1 = new In_house(1, "wheel" , 350 , 16 , 2 , 20 , 3);
        Inventory.addPart(p1);

        Part p2 = new Outsourced(2 , "starter motor" , 250 , 4 , 1 , 5 , "Honda");
        Inventory.addPart(p2);

        Part p3 = new In_house(3, "fairing kit" , 500 , 8 , 1 , 20 , 4);
        Inventory.addPart(p3);

        Part p4 = new In_house(4, "forks" , 300 , 16 , 2 , 20 , 5);
        Inventory.addPart(p4);


        Product r6 = new Product(5 , "Yamaha R6" , 13000 , 3 , 1 , 4);
        r6.addAssociatedPart(p1);
        Inventory.addProduct(r6);
        Product gsxr = new Product(6 , "Suzuki gsx-r" , 11000 , 3 , 1 , 4);
        gsxr.addAssociatedPart(p2);
        gsxr.addAssociatedPart(p3);
        Inventory.addProduct(gsxr);
        Product cbr = new Product(7 , "CBR600rr" , 14000 , 2 , 1 , 4);
        Inventory.addProduct(cbr);


        launch();
    }
}