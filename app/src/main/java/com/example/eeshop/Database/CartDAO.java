package com.example.eeshop.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {
    // الانترفيس هذا بيقرا من الكويري من الداتابيس
    @Query("SELECT * FROM Cart WHERE userPhone=:userPhone")
    Flowable<List<CartItem>> getAllCart(String userPhone);

    @Query("SELECT COUNT(*) from Cart WHERE userPhone=:userPhone")
    Single<Integer> countItemInCart(String userPhone);

    @Query("SELECT SUM ((productPrice* productQuantity)) FROM cart WHERE userPhone=:userPhone")
    Single<Double> sumPriceInCart(String userPhone);

    @Query("SELECT * FROM Cart WHERE productId=:productId AND userPhone=:userPhone")
    Single<CartItem> getItemInCart(String productId , String userPhone);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrReplaceAll (CartItem... cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCartItems (CartItem cartItems);

    @Delete
    Single<Integer> deleteCartItem (CartItem cartItems);

    @Query("DELETE FROM Cart WHERE userPhone=:userPhone")
    Single<Integer> cleanCart(String userPhone);
}
