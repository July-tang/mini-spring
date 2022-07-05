package com.july.minispring.test.bean;

import com.july.minispring.beans.factory.annotation.Value;
import com.july.minispring.stereotype.Component;

import java.time.LocalDate;

@Component
public class Car {
    @Value("${price}")
    private int price;
    @Value("${produceDate}")
    private LocalDate produceDate;
    @Value("${brand}")
    private String brand;

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                ", produceDate=" + produceDate +
                ", brand='" + brand + '\'' +
                '}';
    }
}
