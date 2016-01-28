/*
 * Copyright (C) 2016 Cláudio Pereira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.claudiop.vendingmachine;

/**
 *
 * @author Cláudio Pereira
 */
public class Compartment {

    private String name;
    private int price;
    private int stock;
    final private int capacity;
    private boolean hasProduct;

    public Compartment(String product, int price, int stock, int capacity) {
        this.name = product.trim().equals("") ? "Undefined" : product;
        this.price = price > 0 ? price : 0;
        //TODO validate price < 10€ in the VendingMachine as it is not specific to the Compartment
        this.capacity = capacity > 0 ? capacity : 0;
        if (stock > capacity) {
            this.stock = capacity;
            System.out.println("Warning: " + (stock - capacity)
                    + " units were discarded for exceeding the compartment capacity.");
        } else {
            this.stock = stock;
        }
        this.hasProduct = !(this.name.equals("Undefined")
                || this.price == 0
                || this.capacity == 0);
    }

    public boolean hasProduct() {
        return this.hasProduct;
    }

    public String getProductName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setPrice(int price) {
        if (this.hasProduct) {
            if (price > 0 && price < 10000) {
                this.price = price;
            } else {
                System.out.println("Error: Invalid price. Has to be positive and less than 10€");
            }
        } else {
            System.out.println("Error: There is nothing in this compartment");
        }
    }

    public boolean dropProduct() {
        if (this.stock == 0) {
            System.out.println("Error: No stock!");
            return false;
        }
        this.stock--;
        return true;
    }

    public int refill(int quantity) {
        if (this.hasProduct) {
            if (quantity > 0) {
                if (this.capacity > this.stock + quantity) {
                    this.stock += quantity;
                    return quantity;
                } else {
                    int refilled = this.capacity - this.stock;
                    this.stock = this.capacity;
                    return refilled;
                }
            } else {
                System.out.println("Error: Invalid quantity");
            }
        } else {
            System.out.println("Error: There is nothing in this compartment");
        }
        return 0;
    }

    public int empty() {
        int removed = this.stock;
        this.stock = 0;
        return removed;
    }

    public int newProduct(String name, int stock, int price) {
        int oldStock = empty();
        if (name.trim().equalsIgnoreCase(" ") || stock > capacity || stock < 0) {
            this.name = name;
            this.stock = stock;
            this.hasProduct = true;
        } else {
            System.out.println("The name and/or quantity of product is/are invalid");
        }
        return oldStock;
    }
}
