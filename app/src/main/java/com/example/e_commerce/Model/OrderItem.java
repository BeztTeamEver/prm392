package com.example.e_commerce.Model;

import java.util.ArrayList;
import java.util.Date;

public class OrderItem {
    private int id;
    private int order_id;
    private int book_id;
    private int quantity;
    private double price;
    private Date created_at;
    private int status;

    ArrayList<Book> products;

    public OrderItem() {
    }

    public OrderItem(int id, int order_id, int book_id, int quantity
            , double price, Date created_at, int status, ArrayList<Book> products) {
        this.id = id;
        this.order_id = order_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        this.created_at = created_at;
        this.status = status;
        this.products = products;
    }

    public OrderItem(int order_id, int book_id, int quantity, double price
            , Date created_at, int status, ArrayList<Book> products) {
        this.order_id = order_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        this.created_at = created_at;
        this.status = status;
        this.products = products;
    }

    public OrderItem(int order_id) {
        this.order_id = order_id;
//        this.product_name = new ArrayList<String>();
//        this.product_quantity = new ArrayList<Integer>();
        this.products = new ArrayList<Book>();
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }


    public ArrayList<Book> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Book> products) {
        this.products = products;
    }
}
