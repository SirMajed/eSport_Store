package com.example.eeshop.Database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CartDataSource {
    Flowable<List<CartItem>> getAllCart(String userPhone);

    Single<Integer> countItemInCart(String userPhone);

    Single<Double> sumPriceInCart(String userPhone);

    Single<CartItem> getItemInCart(String productId , String userPhone);

    Completable insertOrReplaceAll (CartItem... cartItems);

    Single<Integer> updateCartItems (CartItem cartItems);

    Single<Integer> deleteCartItem (CartItem cartItems);

    Single<Integer> cleanCart(String userPhone);
}
