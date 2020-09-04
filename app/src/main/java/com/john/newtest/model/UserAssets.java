package com.john.newtest.model;

import com.john.newtest.MyApp;
import com.john.newtest.R;

import androidx.databinding.BindingConversion;

/**
 * Created by JohnZh on 2020/7/25
 *
 * <p></p>
 */
public class UserAssets {

    private float cash;
    private float stock;
    private float futures;
    private float spot;

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public float getFutures() {
        return futures;
    }

    public void setFutures(float futures) {
        this.futures = futures;
    }

    public float getSpot() {
        return spot;
    }

    public void setSpot(float spot) {
        this.spot = spot;
    }

    public float getAll() {
        return cash + stock + spot + futures;
    }

    @BindingConversion
    public static String getAllAssets(float assets) {
        return MyApp.getContext().getString(R.string.assets, assets);
    }
}
