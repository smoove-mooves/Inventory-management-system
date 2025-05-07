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

import static model.Inventory.getAllParts;

/** This class is the controller for the add product form. */

public class AddProductFormController implements Initializable{

    Stage stage;
    Parent scene;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This getter returns the associated parts list. */

    @FXML
    private TableView<Part> addproductTableView1;

    @FXML
    private TableView<Part> addproductTableView2;

    @FXML
    private Button addproductaddBtn;

    @FXML
    private Button addproductcancelBtn;

    @FXML
    private TableColumn<Part, Double> addproductcostCol1;

    @FXML
    private TableColumn<Part, Double> addproductcostCol2;

    @FXML
    private TextField addproductidTxt;

    @FXML
    private TextField addproductinvTxt;

    @FXML
    private TableColumn<Part, Integer> addproductinvlevelCol1;

    @FXML
    private TableColumn<Part, Integer> addproductinvlevelCol2;

    @FXML
    private TextField addproductmaxTxt;

    @FXML
    private TextField addproductminTxt;

    @FXML
    private TextField addproductnameTxt;

    @FXML
    private TableColumn<Part, Integer> addproductpartidCol1;

    @FXML
    private TableColumn<Part, Integer> addproductpartidCol2;

    @FXML
    private TableColumn<Part, String> addproductpartnameCol1;

    @FXML
    private TableColumn<Part, String> addproductpartnameCol2;

    @FXML
    private TextField addproductpriceTxt;

    @FXML
    private Button addproductremoveassociatedpartTxt;

    @FXML
    private Button addproductsaveBtn;

    @FXML
    private TextField addproductsearchTxt;

    /** This method closes the add product screen. */

    @FXML
    void OnActionCancelAddPRO(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void addproductidTxt(ActionEvent event) {

    }

    @FXML
    void addproductinvTxt(ActionEvent event) {

    }

    @FXML
    void addproductmaxTxt(ActionEvent event) {

    }

    @FXML
    void addproductminTxt(ActionEvent event) {

    }

    @FXML
    void addproductnameTxt(ActionEvent event) {

    }

    @FXML
    void addproductpriceTxt(ActionEvent event) {

    }

    /** This method searches for a part in the parts table in the add product menu. */

    @FXML
    void addproductsearchTxt(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        String searchedPart = addproductsearchTxt.getText();

        for(Part part : allParts) {

            if ((part.getName().contains(searchedPart)) || (String.valueOf(part.getId()).contains(searchedPart))) {

                matchingParts.add(part);

            }

        }

        if(matchingParts.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");

            Optional<ButtonType> result = alert.showAndWait();
        }

        addproductTableView1.setItems(matchingParts);

    }

    /** This method adds a selected part to the associated parts list. */

    @FXML
    void onActionAddPartAPr(ActionEvent event) throws IOException{

        Part selectedPart = addproductTableView1.getSelectionModel().getSelectedItem();

        if(selectedPart == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to add.");

            Optional<ButtonType> result = alert.showAndWait();

        }

    else {

            associatedParts.add(selectedPart);
            addproductTableView2.setItems(associatedParts);

        }

    }

    /** This method removes a selected part from the associated parts list. */

    @FXML
    void onActionRemovePartAPr(ActionEvent event) throws IOException{

        Part selectedPart = addproductTableView2.getSelectionModel().getSelectedItem();

        if(selectedPart == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to remove.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && result.get() == ButtonType.OK) {

                associatedParts.remove(selectedPart);
                addproductTableView2.setItems(associatedParts);

            }
        }

    }

    /** This method saves a created product and updates the associated parts list. */

    @FXML
    void onActionSaveAPr(ActionEvent event) throws IOException{

        try {

            int id = Inventory.getNewProductId();
            String name = addproductnameTxt.getText();
            double price = Double.parseDouble(addproductpriceTxt.getText());
            int stock = Integer.parseInt(addproductinvTxt.getText());
            int min = Integer.parseInt(addproductminTxt.getText());
            int max = Integer.parseInt(addproductmaxTxt.getText());

            if (inventoryProductCheck(min, max, stock) && minMaxProductCheck(min, max)) {

                Product newProduct = new Product(id, name, price, stock, min, max);

                for(Part part : associatedParts){

                    newProduct.addAssociatedPart(part);

                }

                newProduct.setId(Inventory.getNewProductId());
                Inventory.addProduct(newProduct);

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
            alert.setContentText("Cost must be an integer and name cannot be a number.");
            alert.showAndWait();
        }
    }

    /** This method checks to make sure that a product's min is less than max. */

    private boolean minMaxProductCheck(int min, int max) {

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

    public boolean inventoryProductCheck(int min, int max, int stock) {

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

    /** This method initializes the add product menu. */

    @Override
    public void initialize(URL url, ResourceBundle rb){

        addproductTableView1.setItems(Inventory.getAllParts());

        addproductpartidCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        addproductpartnameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        addproductcostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        addproductinvlevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));

        addproductTableView2.setItems(associatedParts);

        addproductpartidCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        addproductpartnameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addproductcostCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        addproductinvlevelCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

}