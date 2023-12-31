package com.example.e_commerce.Repository;

import com.example.e_commerce.Network.ApiClient;
import com.example.e_commerce.Service.IBookService;
import com.example.e_commerce.Service.IBookTypeService;
import com.example.e_commerce.Service.IUserService;

public class RepositoryBase {
    public static IBookService getBookService(){
        return ApiClient.getClient().create(IBookService.class);
    }

    public static IUserService getUserService(){
        return ApiClient.getClient().create(IUserService.class);
    }

    public static IBookTypeService getBookTypeService(){
        return ApiClient.getClient().create(IBookTypeService.class);
    }
}
