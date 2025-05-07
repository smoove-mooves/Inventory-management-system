package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/** This class has various methods for managing the program's part inventory. */

public class Inventory extends Part{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /** This method adds parts to the all parts list.
     *
      * @param newPart This parameter represents a new part.
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method adds parts to the all products list.
     *
     * @param newProduct This parameter represents a new product.
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static void addPart(int id, String name, double price, int stock, int min, int max, int machineID) {
    }

    private static int partId = 4;
    private static int productId = 7;

    /** This method generates a new part id. */

    public static int getNewPartId(){
        return ++partId;
    }

    /** This method generates a new product id. */

    public static int getNewProductId(){
        return ++productId;
    }

    /** This method searches for parts based on part id. */

    public Part lookupPart(int partID){
        int i = 0;
        for(i = 0 ; i < allParts.size(); i++){
            if(partID == allParts.get(i).getId()){
                return allParts.get(i);
            }
            else{
                System.out.println("Part is not in inventory.");
            }

        }
        return allParts.get(i);
    }

    /** This method searches for products based on product id. */

    public Product lookupProduct(int productID){
        int i = 0;
        for(i = 0 ; i < allProducts.size(); i++){
            if(productID == allProducts.get(i).getId()){
                return allProducts.get(i);
            }
            else{
                System.out.println("Product is not in inventory.");
            }

        }
        return allProducts.get(i);
    }

    /** This method searches for parts based on part name. */

    public static Part lookupPartName(String partName){
        int i = 0;
        for(i = 0 ; i < allParts.size(); i++){
            if(partName == allParts.get(i).getName()){
                return allParts.get(i);
            }
            else{
                System.out.println("Part name not in inventory.");
            }

        }
        return allParts.get(i);
    }

    /** This method searches for products based on product name. */

    public Product lookupProductName(String productName){
        int i = 0;
        for(i = 0 ; i < allProducts.size(); i++){
            if(productName == allProducts.get(i).getName()){
                return allProducts.get(i);
            }
            else{
                System.out.println("Product name not in inventory.");
            }

        }
        return allProducts.get(i);
    }

    /** This method updates a part's index in the all parts list. */

    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    /** This method updates a product's index in the all products list. */

    public void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    /** This method deletes a part from the all parts list. */

    boolean deletePart(Part selectedPart) {
        boolean exists = true;
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            exists = false;
        }
        return false;
    }

    /** This method deletes a product from the all products list. */

   public static boolean deleteProduct(Product selectedProduct) {
        boolean exists = true;
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            exists = false;
        }
        return false;
    }

    /** This method returns the all parts list. */

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /** This method returns the all products list. */

    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
}
