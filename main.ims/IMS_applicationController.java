package main.ims;

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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;
import static model.Inventory.getAllParts;

/** This class is the controller for the main menu. */

public class IMS_applicationController implements Initializable {

    Stage stage;
    Parent scene;

    private static Part selectedPartModify;

    private static Product selectedProductModify;

    @FXML
    private TableView<Part> mainformTableView1;

    @FXML
    private TableView<Product> mainformTableView2;

    @FXML
    private Button mainformexitBtn;

    @FXML
    private Button mainformpartaddBtn;

    @FXML
    private TableColumn<Part, Double> mainformpartcostCol1;

    @FXML
    private Button mainformpartdeleteBtn;

    @FXML
    private TableColumn<Part, Integer> mainformpartidCol1;

    @FXML
    private TableColumn<Part, Integer> mainformpartinvlevelCol1;

    @FXML
    private Button mainformpartmodifyBtn;

    @FXML
    private TableColumn<Part, String> mainformpartnameCol1;

    @FXML
    private TextField mainformpartSearch;

    @FXML
    private Button mainformproductaddBtn;

    @FXML
    private TableColumn<Product, Double> mainformproductcostCol1;

    @FXML
    private Button mainformproductdeleteBtn;

    @FXML
    private TableColumn<Product, Integer> mainformproductidCol1;

    @FXML
    private TableColumn<Product, Integer> mainformproductinvlevelCol1;

    @FXML
    private Button mainformproductmodifyBtn;

    @FXML
    private TableColumn<Product, String> mainformproductnameCol1;

    @FXML
    private TextField mainformproductSearch;

    /** This getter returns a selected part. */

    public static Part getSelectedPartModify() {
        return selectedPartModify;
    }

    /** This getter returns a selected product. */

    public static Product getSelectedProductModify() {
        return selectedProductModify;
    }

    /** This method initializes the main menu. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainformTableView1.setItems(getAllParts());

        mainformpartidCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainformpartnameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainformpartcostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainformpartinvlevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainformTableView2.setItems(Inventory.getAllProducts());

        mainformproductidCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainformproductnameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainformproductcostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainformproductinvlevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainformTableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mainformTableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /** This method deletes a selected part from the main menu parts table. */

    public void deleteButtonPushed() {

        ObservableList<Part> selectedRows, allParts;
        allParts = mainformTableView1.getItems();
        selectedRows = mainformTableView1.getSelectionModel().getSelectedItems();

        for (Part part : selectedRows) {

            allParts.remove(part);

        }

    }

    /** This method deletes a selected product from the main menu products table. */

    public void deleteButtonPushed2() {

        ObservableList<Product> selectedRows2, allProducts;
        allProducts = mainformTableView2.getItems();
        selectedRows2 = mainformTableView2.getSelectionModel().getSelectedItems();
        ObservableList<Part> assocParts = selectedProductModify.getAllAssociatedParts();

        if (assocParts.size() >= 1) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot delete this product because it has associated parts.");

            Optional<ButtonType> result = alert.showAndWait();

        }
        else{
                for (Product product : selectedRows2) {

                    allProducts.remove(product);

                }
            }

    }

    /** This method searches for parts in the main menu parts table. */

    @FXML
    void onActionsearchpartField(ActionEvent event) throws IOException {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        String searchedPart = mainformpartSearch.getText();

        for (Part part : allParts) {

            if ((part.getName().contains(searchedPart)) || (String.valueOf(part.getId()).contains(searchedPart))) {
                matchingParts.add(part);

            }

        }

        if (matchingParts.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");

            Optional<ButtonType> result = alert.showAndWait();

        }

        mainformTableView1.setItems(matchingParts);

    }

    /** This method searches products in the main menu product table. */

    @FXML
    void mainformproductsearchBtn(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        String searchedProduct = mainformproductSearch.getText();

        for (Product product : allProducts) {

            if ((product.getName().contains(searchedProduct)) || (String.valueOf(product.getId()).contains(searchedProduct))) {

                matchingProducts.add(product);

            }

        }

        if (matchingProducts.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");

            Optional<ButtonType> result = alert.showAndWait();

        }

        mainformTableView2.setItems(matchingProducts);

    }

    /** This method opens the add part menu. */

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = load(getClass().getResource("/view/AddPartFormInHouse.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method opens the add product menu. */

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method deletes a selected part in the main menu parts table. */

    @FXML
    void onActionDeletePart(ActionEvent event) {

        selectedPartModify = mainformTableView1.getSelectionModel().getSelectedItem();

        if(selectedPartModify == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to delete.");

            Optional<ButtonType> result = alert.showAndWait();

        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && result.get() == ButtonType.OK) {

                deleteButtonPushed();

            }
        }

    }

    /** This method deletes a selected product in the main menu product table. */

    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        selectedProductModify = mainformTableView2.getSelectionModel().getSelectedItem();

        if(selectedProductModify == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a product to delete.");

            Optional<ButtonType> result = alert.showAndWait();

        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && result.get() == ButtonType.OK) {

                deleteButtonPushed2();

            }
        }
    }

    /** This method closes the entire program. */

    @FXML
    void onActionExitProgram(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit this amazing program?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && result.get() == ButtonType.OK) {

            System.exit(0);

        }

    }

    /** This method opens the modify-part menu for a selected part. */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        selectedPartModify = mainformTableView1.getSelectionModel().getSelectedItem();

        if(selectedPartModify == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a part to modify.");

            Optional<ButtonType> result = alert.showAndWait();

        }
        else {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = load(getClass().getResource("/view/ModifyPartFormInHouse.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /** This method opens the modify-product menu for a selected product. */

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        selectedProductModify = mainformTableView2.getSelectionModel().getSelectedItem();

        if(selectedProductModify == null){

            Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a product to modify.");

            Optional<ButtonType> result = alert.showAndWait();

        }
        else {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = load(getClass().getResource("/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
}

