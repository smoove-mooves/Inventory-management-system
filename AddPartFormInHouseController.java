package controller;

import javafx.scene.control.*;
import model.In_house;
import model.Inventory;
import model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class is the controller for adding parts. */

public class AddPartFormInHouseController implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup addpartTG;

    @FXML
    private RadioButton addpartoutsourcedRbtn;

    @FXML
    private Button partcancelBtn;

    @FXML
    private TextField partcostTxt;

    @FXML
    private TextField partidTxt;

    @FXML
    private RadioButton partinhouseRbtn;

    @FXML
    private TextField partinvTxt;

    @FXML
    private TextField partmachineidTxt;

    @FXML
    private TextField partmaxTxt;

    @FXML
    private TextField partminTxt;

    @FXML
    private TextField partnameTxt;

    @FXML
    private Button partsaveBtn;

    @FXML
    private Label machineIDlabel;

    /** This method cancels adding a part. */

    @FXML
    void OnActionCancelAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method sets the machineID label upon clicking the machineID radio-button. */

    @FXML
    void onActionInHousePart(ActionEvent event) {

        machineIDlabel.setText("Machine ID");

    }

    /** This method sets the machineID label upon clicking the machineID radio-button. */

    @FXML
    void onActionOutSourcedPart(ActionEvent event) {

        machineIDlabel.setText("Company name");

    }

    /** This method saves a part. */
    
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

    try {

        if (partinhouseRbtn.isSelected()) {

            int id = Inventory.getNewPartId();
            String name = partnameTxt.getText();
            double price = Double.parseDouble(partcostTxt.getText());
            int stock = Integer.parseInt(partinvTxt.getText());
            int min = Integer.parseInt(partminTxt.getText());
            int max = Integer.parseInt(partmaxTxt.getText());
            int machineID;

            if (minMaxCheck(min, max) && (inventoryCheck(min, max, stock))) {

                machineID = Integer.parseInt(partmachineidTxt.getText());

                Inventory.addPart(new In_house(id, name, price, stock, min, max, machineID));

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }

        if (addpartoutsourcedRbtn.isSelected()) {

            int id = Inventory.getNewPartId();
            String name = partnameTxt.getText();
            double price = Double.parseDouble(partcostTxt.getText());
            int stock = Integer.parseInt(partinvTxt.getText());
            int min = Integer.parseInt(partminTxt.getText());
            int max = Integer.parseInt(partmaxTxt.getText());
            String companyName;

            if (minMaxCheck(min, max) && (inventoryCheck(min, max, stock))) {

                companyName = partmachineidTxt.getText();

                Inventory.addPart(new Outsourced(id, name, price, stock, min, max , companyName));

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
    }
        catch(NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Input format error");
            alert.setContentText("Machine ID and cost must be an integer. Name cannot be a number. ");
            alert.showAndWait();

        }
    }

    @FXML
    void partcostTxt(ActionEvent event) {

    }

    @FXML
    void partidTxt(ActionEvent event) {

    }

    @FXML
    void partinvTxt(ActionEvent event) {

    }

    @FXML
    void partmachineidTxt(ActionEvent event) {

    }

    @FXML
    void partmaxTxt(ActionEvent event) {

    }

    @FXML
    void partminTxt(ActionEvent event) {

    }

    @FXML
    void partnameTxt(ActionEvent event) {

    }

    /** This method checks to see if the min is less than the max. */

    private boolean minMaxCheck(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {

            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Max/min value error");
            alert.setContentText("Max value must be greater than min value.");
            alert.showAndWait();

        }

        return isValid;

    }

    /** This method checks to see if the inventory level is less than the max and greater than the min. */

    public boolean inventoryCheck(int min, int max, int stock) {

        boolean isValid = true;

        if (stock >= max || stock <= min) {

            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Invalid value for Inventory");
            alert.setContentText("Inventory must be a value within the min-max range.");
            alert.showAndWait();

        }

        return isValid;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
