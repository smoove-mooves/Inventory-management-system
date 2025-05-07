package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.In_house;
import model.Inventory;
import model.Outsourced;
import model.Part;
import main.ims.IMS_applicationController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.ims.IMS_applicationController.getSelectedPartModify;

/** This class is the controller for the modify-part form. */

public class ModifyPartFormInHouseController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup modifypartTG;

    @FXML
    private Button modifypartcancelBtn;

    @FXML
    private TextField modifypartcostTxt;

    @FXML
    private TextField modifypartidTxt;

    @FXML
    private RadioButton modifypartinhouseRbtn;

    @FXML
    private TextField modifypartinvTxt;

    @FXML
    private TextField modifypartmachineidTxt;

    @FXML
    private TextField modifypartmaxTxt;

    @FXML
    private TextField modifypartminTxt;

    @FXML
    private TextField modifypartnameTxt;

    @FXML
    private RadioButton modifypartoutsourcedRbtn;

    @FXML
    private Button modifypartsaveBtn;

    @FXML
    private Label modifymachineidLbl;

    /** This method closes the modify-part menu. */

    @FXML
    void modifypartcancelBtn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void modifypartcostTxt(ActionEvent event) {

    }

    @FXML
    void modifypartidTxt(ActionEvent event) {

    }

    /** This method changes a label title upon selection of the in-house radio button. */

    @FXML
    void modifypartinhouseRbtn(ActionEvent event) {

        modifymachineidLbl.setText("machineID");

    }

    @FXML
    void modifypartinvTxt(ActionEvent event) {

    }

    @FXML
    void modifypartmachineidTxt(ActionEvent event) {

    }

    @FXML
    void modifypartmaxTxt(ActionEvent event) {

    }

    @FXML
    void modifypartminTxt(ActionEvent event) {

    }

    @FXML
    void modifypartnameTxt(ActionEvent event) {

    }

    /** This method changes a label title upon selection of the outsourced radio button. */

    @FXML
    void modifypartoutsourcedRbtn(ActionEvent event) {

        modifymachineidLbl.setText("Company name");

    }

    /** This method saves a part that has been modified. */

    @FXML
    void modifypartsaveBtn(ActionEvent event) throws IOException{

    try {

        if (modifypartinhouseRbtn.isSelected()) {

            int id = Integer.parseInt(modifypartidTxt.getText());
            String name = modifypartnameTxt.getText();
            double price = Double.parseDouble(modifypartcostTxt.getText());
            int stock = Integer.parseInt(modifypartinvTxt.getText());
            int min = Integer.parseInt(modifypartminTxt.getText());
            int max = Integer.parseInt(modifypartmaxTxt.getText());
            int machineID;

            if (minMaxModifyCheck(min, max) && inventoryModifyCheck(min, max, stock)) {

                machineID = Integer.parseInt(modifypartmachineidTxt.getText());

                Inventory.getAllParts().remove(getSelectedPartModify());

                Part part = new In_house(id, name, price, stock, min, max, machineID);

                Inventory.addPart(part);

                Inventory.getAllParts().remove(getSelectedPartModify());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }

        if (modifypartoutsourcedRbtn.isSelected()) {

            int id = Integer.parseInt(modifypartidTxt.getText());
            String name = modifypartnameTxt.getText();
            double price = Double.parseDouble(modifypartcostTxt.getText());
            int stock = Integer.parseInt(modifypartinvTxt.getText());
            int min = Integer.parseInt(modifypartminTxt.getText());
            int max = Integer.parseInt(modifypartmaxTxt.getText());
            String companyName;

            if (minMaxModifyCheck(min, max) && inventoryModifyCheck(min, max, stock)) {

                companyName = modifypartmachineidTxt.getText();

                Inventory.getAllParts().remove(getSelectedPartModify());

                Part part = new Outsourced(id, name, price, stock, min, max, companyName);

                Inventory.addPart(part);

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

    /** This method checks to make sure that a part's min is less than max. */

    private boolean minMaxModifyCheck(int min, int max) {

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

    /** This method checks to make sure that a part's inventory level is less than max and greater than min. */

    public boolean inventoryModifyCheck(int min, int max, int stock) {

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

    /** This method initializes the modify-part menu. */

    @Override
    public void initialize(URL url, ResourceBundle rb){

        Part selectedPart = IMS_applicationController.getSelectedPartModify();

        try {

            if (selectedPart instanceof In_house) {

                modifypartinhouseRbtn.setSelected(true);
                modifymachineidLbl.setText("machineID");
                modifypartmachineidTxt.setText(String.valueOf(((In_house) selectedPart).getMachineId()));

            }

            if (selectedPart instanceof Outsourced) {

                modifypartoutsourcedRbtn.setSelected(true);
                modifymachineidLbl.setText("Company name");
                modifypartmachineidTxt.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));

            }

            modifypartidTxt.setText(String.valueOf(selectedPart.getId()));
            modifypartnameTxt.setText(selectedPart.getName());
            modifypartinvTxt.setText(String.valueOf(selectedPart.getStock()));
            modifypartcostTxt.setText(String.valueOf(selectedPart.getPrice()));
            modifypartmaxTxt.setText(String.valueOf(selectedPart.getMax()));
            modifypartminTxt.setText(String.valueOf(selectedPart.getMin()));

        }

        catch(NullPointerException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Null modify part error.");
                alert.setContentText("You must select a part to modify to get to the modify part screen.");
                alert.showAndWait();

        }

    }
}
