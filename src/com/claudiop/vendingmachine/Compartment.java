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

    public Compartment(int capacity) {
        this("Undefined", 0, 0, capacity);
    }

    public Compartment(String product, int price, int stock, int capacity) {
        this.name = product;
        this.price = price;
        this.stock = stock;
        this.capacity = capacity;
        this.hasProduct = !this.name.equals("Undefined");
    }

    public String getProductName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setNewCost(int cost) {
        if(this.hasProduct){
            if (cost > 0 && cost < 100000) {
                this.price = cost;
            } else {
                System.out.println("Error: Invalid price. Has to be positive and less than 10€");
            }
        }else{
            System.out.println("Error: There is nothing in this compartment");
        }
    }

    public void dropProduct() {
        if (this.stock == 0) {
            System.out.println("Error: No stock!");
        } else {
            this.stock--;
        }
    }

    public int refill(int quantity) {
        if(this.hasProduct){
            if (quantity > 0) {
                if (this.capacity > this.stock + quantity) {
                    this.stock += quantity;
                    return quantity;
                } else {
                    int refilled =  this.capacity - this.stock;
                    this.stock = this.capacity;
                    return refilled;
                }
            }else{
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
        } else {
            System.out.println("The name and/or quantity of product is/are invalid");
        }
        return oldStock;
    }
}
