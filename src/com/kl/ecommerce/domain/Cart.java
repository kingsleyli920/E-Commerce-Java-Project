package com.kl.ecommerce.domain;

import java.util.*;

public class Cart {


    private double total = 0;
    private Map<String, CartItem> map = new HashMap<String, CartItem>();

    /**
     * 商品加入购物车
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem) {
        String pid = cartItem.getProduct().getPid();
        if (map.containsKey(pid)) {
            CartItem old = map.get(pid);
            old.setNum(old.getNum() + cartItem.getNum());
        } else {
            map.put(pid, cartItem);
        }
    }

    /**
     * 通过商品ID移除购物车内项目
     * @param pid
     */
    public void removeCartItem(String pid) {
        map.remove(pid);
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        map.clear();
    }

    public double getTotal() {

        total = 0;

        Collection<CartItem> cartItems = map.values();
        for (CartItem cartItem : cartItems) {
            total += cartItem.getSubTotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    public Collection<CartItem> getCartItems() {
        return map.values();
    }
}
