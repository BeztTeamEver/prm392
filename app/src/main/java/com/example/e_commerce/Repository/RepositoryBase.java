package com.example.e_commerce.Repository;

import com.example.e_commerce.Network.ApiClient;
import com.example.e_commerce.Service.IBookService;

public class RepositoryBase {
    public static IBookService getBookService(){
        return ApiClient.getClient().create(IBookService.class);
    }
}
