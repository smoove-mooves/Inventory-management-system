package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.ims.IMS_applicationController;

/** This class is the controller for the modify-product form. */

public class ModifyProductFormController implements Initializable {

    Stage stage;
    Parent scene;

    Product selectedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This method returns the associated parts list. */

    @FXML
    private TableView<Part> modifyproductTableView1;

    @FXML
    private TableView<Part> modifyproductTableView2;

    @FXML
    private Button modifyproductaddBtn;

    @FXML
    private Button modifyproductcancelBtn;

    @FXML
    private TableColumn<Part, Double> modifyproductcostCol1;

    @FXML
    private TableColumn<Part, Double> modifyproductcostCol2;

    @FXML
    private TextField modifyproductidTxt;

    @FXML
    private TextField modifyproductinvTxt;

    @FXML
    private TableColumn<Part, Integer> modifyproductinvlevelCol1;

    @FXML
    private TableColumn<Part, Integer> modifyproductinvlevelCol2;

    @FXML
    private TextField modifyproductmaxTxt;

    @FXML
    private TextField modifyproductminTxt;

    @FXML
    private TextField modifyproductnameTxt;

    @FXML
    private TableColumn<Part, Integer> modifyproductpartidCol1;

    @FXML
    private TableColumn<Part, Integer> modifyproductpartidCol2;

    @FXML
    private TableColumn<Part, String> modifyproductpartnameCol1;

    @FXML
    private TableColumn<Part, String> modifyproductpartnameCol2;

    @FXML
    private TextField modifyproductpriceTxt;

    @FXML
    private Button modifyproductremoveassociatedpartBtn;

    @FXML
    private Button modifyproductsaveBtn;

    @FXML
    private TextField modifyproductsearchTxt;

    /** This method adds a selected part to the associated parts list. */

    @FXML
    void modifyproductaddBtn(ActionEvent event) {

        Part selectedPart = modifyproductTableView1.getSelectionModel().getSelectedItem();

        if(selectedPart == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to add.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            associatedParts.add(selectedPart);

            modifyproductTableView2.setItems(associatedParts);
        }

    }

    /** This method closes the modify-product screen. */

    @FXML
    void modifyproductcancelBtn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void modifyproductidTxt(ActionEvent event) {

    }

    @FXML
    void modifyproductinvTxt(ActionEvent event) {

    }

    @FXML
    void modifyproductmaxTxt(ActionEvent event) {

    }

    @FXML
    void modifyproductminTxt(ActionEvent event) {

    }

    @FXML
    void modifyproductnameTxt(ActionEvent event) {

    }

    @FXML
    void modifyproductpriceTxt(ActionEvent event) {

    }

    /** This method removes a selected part from the associated parts list. */

    @FXML
    void modifyproductremoveassociatedpartBtn(ActionEvent event) {

        Part selectedPart = modifyproductTableView2.getSelectionModel().getSelectedItem();

        if(selectedPart == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to remove.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && result.get() == ButtonType.OK) {

                associatedParts.remove(selectedPart);
                modifyproductTableView2.setItems(associatedParts);

            }
        }

    }

    /** This method saves a modified product. */

    @FXML
    void modifyproductsaveBtn(ActionEvent event) throws IOException{

        try {

            int id = Integer.parseInt(modifyproductidTxt.getText());
            String name = modifyproductnameTxt.getText();
            double price = Double.parseDouble(modifyproductpriceTxt.getText());
            int stock = Integer.parseInt(modifyproductinvTxt.getText());
            int min = Integer.parseInt(modifyproductminTxt.getText());
            int max = Integer.parseInt(modifyproductmaxTxt.getText());


            if (minMaxModifyProductCheck(min, max) && inventoryModifyProductCheck(min, max, stock)) {

                Product newProduct = new Product(id, name, price, stock, min, max);

                for(Part part : associatedParts) {

                    newProduct.associatedParts.add(part);

                }

                Inventory.addProduct(newProduct);

                Inventory.deleteProduct(selectedProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

           }

        }

        catch(NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Input format error");
            alert.setContentText("Cost must be an integer and name cannot be a string.");
            alert.showAndWait();

        }

    }

    /** This method searches the parts list in the modify-product screen. */

    @FXML
    void modifyproductsearchTxt(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        String searchedPart = modifyproductsearchTxt.getText();

        for(Part part : allParts) {

            if ((part.getName().contains(searchedPart)) || (String.valueOf(part.getId()).contains(searchedPart))) {

                matchingParts.add(part);

            }

        }

        if(matchingParts.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");

            Optional<ButtonType> result = alert.showAndWait();
        }

        modifyproductTableView1.setItems(matchingParts);

    }

    /** This method checks to make sure that a product's min is less than max. */

    private boolean minMaxModifyProductCheck(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {

            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Max/min value error");
            alert.setContentText("Max value must be greater than min value");
            alert.showAndWait();

        }

        return isValid;

    }

    /** This method checks to make sure that a product's inventory level is less than max and greater than min. */

    public boolean inventoryModifyProductCheck(int min, int max, int stock) {

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

    /** This method initializes the modify-product menu. */

    @Override
    public void initialize(URL location, ResourceBundle resources){

        try {

            selectedProduct = IMS_applicationController.getSelectedProductModify();

            for(Part part : selectedProduct.getAllAssociatedParts()) {

                associatedParts.add(part);

            }

            modifyproductTableView1.setItems(Inventory.getAllParts());
            modifyproductTableView2.setItems(associatedParts);

            modifyproductpartidCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
            modifyproductpartnameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
            modifyproductcostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
            modifyproductinvlevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
            modifyproductTableView1.setItems(Inventory.getAllParts());

            modifyproductpartidCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
            modifyproductpartnameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            modifyproductcostCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
            modifyproductinvlevelCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));

            modifyproductidTxt.setText(String.valueOf(selectedProduct.getId()));
            modifyproductnameTxt.setText(selectedProduct.getName());
            modifyproductinvTxt.setText(String.valueOf(selectedProduct.getStock()));
            modifyproductpriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
            modifyproductmaxTxt.setText(String.valueOf(selectedProduct.getMax()));
            modifyproductminTxt.setText(String.valueOf(selectedProduct.getMin()));

        }

        catch(NullPointerException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Null modify product error.");
                alert.setContentText("You must select a product to modify to get to the modify product screen.");
                alert.showAndWait();

        }

    }

}
