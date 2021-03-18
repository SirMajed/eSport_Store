package com.example.eeshop.Database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalCartDataSource implements CartDataSource {


    private CartDAO cartDAO;

    public LocalCartDataSource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public Flowable<List<CartItem>> getAllCart(String userPhone) {
        return cartDAO.getAllCart(userPhone);
    }

    @Override
    public Single<Integer> countItemInCart(String userPhone) {
        return cartDAO.countItemInCart(userPhone);
    }

    @Override
    public Single<Double> sumPriceInCart(String userPhone) {
        return cartDAO.sumPriceInCart(userPhone);
    }

    @Override
    public Single<CartItem> getItemInCart(String productId, String userPhone) {
        return cartDAO.getItemInCart(productId,userPhone);
    }

    @Override
    public Completable insertOrReplaceAll(CartItem... cartItems) {
        return cartDAO.insertOrReplaceAll(cartItems);
    }

    @Override
    public Single<Integer> updateCartItems(CartItem cartItem) {
        return cartDAO.updateCartItems(cartItem);
    }

    @Override
    public Single<Integer> deleteCartItem(CartItem cartItem) {
        return cartDAO.deleteCartItem(cartItem);
    }

    @Override
    public Single<Integer> cleanCart(String userPhone) {
        return cartDAO.cleanCart(userPhone);
    }
}
