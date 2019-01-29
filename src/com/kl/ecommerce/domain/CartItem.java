package com.kl.ecommerce.domain;

public class CartItem {
    private Product product;
    private int num;
    private double subTotal;

    public CartItem() {
    }

    public CartItem(Product product, int num) {
        this.product = product;
        this.num = num;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public double getSubTotal() {
        return product.getShop_price() * num;
    }
}
