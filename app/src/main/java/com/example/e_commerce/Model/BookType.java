package com.example.e_commerce.Model;

import java.io.Serializable;
import java.util.Date;

public class BookType implements Serializable {

    private int id;
    private String type_name;
    private String image_url;
    private Date created_at;
    private int Status;
    private static BookType bookType = null;

    public BookType(){}

    public BookType(int id, String type_name, String image_url, Date created_at, int status) {
        this.id = id;
        this.type_name = type_name;
        this.image_url = image_url;
        this.created_at = created_at;
        Status = status;
    }

    public BookType(int id, String type_name, String image_url) {
        this.id = id;
        this.type_name = type_name;
        this.image_url = image_url;
        this.created_at = null;
        Status = 222;
    }

    public BookType(String type_name, String image_url, Date created_at, int status) {
        this.type_name = type_name;
        this.image_url = image_url;
        this.created_at = created_at;
        Status = status;
    }


    public static BookType getInstance(){
        if(bookType == null)
            bookType = new BookType();
        return bookType;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
