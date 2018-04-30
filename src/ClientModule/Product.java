package ClientModule;

import Database.DBAction;

/**
 * Class to store information about products
 *
 * @author Hamish Dickson
 */
public class Product {
    private final String name;
    private final double price;
    private final int quantity;
    private int id;
    private final int sellerID;

    /**
     * Creates an instance of the product class
     *
     * @param name     product name
     * @param price    product price
     * @param quantity product quantity
     * @param sellerID product seller ID
     */
    public Product(String name, double price, int quantity, int sellerID) {
        DBAction dbAction = new DBAction();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerID = sellerID;
        this.id = dbAction.getProductID(name);
    }

    /**
     * @return name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * @return price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return quantity of product purchased
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * @return seller ID of the product
     */
    public int getSellerID() {
        return sellerID;
    }
}
