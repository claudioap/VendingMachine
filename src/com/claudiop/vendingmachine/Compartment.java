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
 * Container is a class to abstract a slot which has a product inside. It
 *
 * @author Cláudio Pereira
 */
public class Compartment {

    private String name;
    private int price;
    private int stock;
    final private int capacity;
    private boolean hasProduct;

    /**
     * Creates a compartment. Product is set as "Undefined" if any of the
     * arguments is invalid or has a bad value.
     *
     * @param product Product name
     * @param price Product price
     * @param stock Product stock
     * @param capacity Compartment capacity
     */
    public Compartment(String product, int price, int stock, int capacity) {
        this.name = product.trim().equals("") ? "Undefined" : product;
        this.price = price > 0 ? price : 0;
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

    /**
     * Checks if there is a product in the container
     *
     * @return Container has a product
     */
    public boolean hasProduct() {
        return this.hasProduct;
    }

    /**
     * Obtains the name of the product in the container
     *
     * @return Product Product name
     */
    public String getProductName() {
        return this.name;
    }

    /**
     * Obtains the price of the product in the container
     *
     * @return Product price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Obtains the stock of the product in the container
     *
     * @return Product stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Obtains the capacity of the container
     *
     * @return Product capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Sets a new price for the product
     *
     * @param price New price
     */
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

    /**
     * Removes one unit from the container
     *
     * @return Was it successful?
     */
    public boolean dropProduct() {
        if (this.stock == 0) {
            System.out.println("Error: No stock!");
            return false;
        }
        this.stock--;
        return true;
    }

    /**
     * Refills the container
     *
     * @param quantity Product amount to refill
     * @return Quantity refilled
     */
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

    /**
     * Removes the stock of product of the container
     *
     * @return The old stock
     */
    public int empty() {
        int removed = this.stock;
        this.stock = 0;
        return removed;
    }

    /**
     * Changes the product in the container
     *
     * @param name New name
     * @param stock Amount of stock
     * @param price Product price
     * @return Old product stock
     */
    public int newProduct(String name, int stock, int price) {
        int oldStock = empty();
        if (name.trim().equals("") || stock > capacity || stock < 0) {
            this.name = name;
            this.stock = stock;
            this.hasProduct = true;
        } else {
            System.out.println("The name and/or quantity of product is/are invalid");
        }
        return oldStock;
    }
}
