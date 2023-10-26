package com.example.e_commerce.Service.BookService;

import android.util.Log;

import com.example.e_commerce.Activity.CategoryProductsActivity;
import com.example.e_commerce.Model.Book;
import com.example.e_commerce.Repository.RepositoryBase;
import com.example.e_commerce.Service.IBookService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookService {

    IBookService bookService;

    public BookService(){
        bookService = RepositoryBase.getBookService();
    }

    /*public List<Book> getAllBook() throws IOException {
        Call<List<Book>> call = bookService.getAll();
        Response<List<Book>> response = call.execute();

        if (response.isSuccessful()) {
            List<Book> books = response.body();
            if (books != null) {
                return books;
            }
        }

        // Handle API error or empty response
        return null;
    }*/
}
