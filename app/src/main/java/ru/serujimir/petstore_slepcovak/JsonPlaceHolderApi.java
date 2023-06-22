package ru.serujimir.petstore_slepcovak;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("pet/{petId}")
    Call<Pet> getPet(@Path("petId") String petId);

    @POST("pet")
    Call<Void> postPet(@Body Pet pet);

    @GET("store/order/{orderId}")
    Call<Order> getOrder(@Path("orderId") String orderId);

    @POST("store/order")
    Call<Void> postOrder(@Body Order order);

    @GET("user/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("user")
    Call<Void> postUser(@Body User user);
}
