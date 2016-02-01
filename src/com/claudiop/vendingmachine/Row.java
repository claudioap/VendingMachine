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

import java.util.HashMap;

/**
 * The row is a placeholder for several containers
 *
 * @author Cláudio Pereira
 */
public class Row {

    private HashMap<Integer, Compartment> compartments;
    private final int maxCompartments;

    /**
     * Creates a new row witch holds containers
     *
     * @param compartments Compartment capacity
     */
    public Row(int compartments) {
        if (compartments > 0 && compartments < 11) {
            this.maxCompartments = compartments;
        } else {
            this.maxCompartments = 10;
            System.out.println("Error: The number of compartments and/or the compartment capacity is invalid");
        }
        this.compartments = new HashMap(this.maxCompartments);
    }

    /**
     * Add a compartment to one of the slots in the row
     *
     * @param slot Where to insert the container
     * @param capacity Container capacity
     * @param product Product name
     * @param stock Product stock
     * @param price Product price
     */
    public void addCompartment(int slot, int capacity, String product, int stock, int price) {
        if (this.compartments.containsKey(slot)) {
            System.out.println("Error: There is a compartment in that slot");
        } else if (capacity < 0) {
            System.out.println("Error: Invalid compartment size");
        } else {
            this.compartments.put(slot, new Compartment(product, stock, price, capacity));
        }
    }

    /**
     * Remove a compartment from a slot
     *
     * @param compartment Compartment slot to remove
     * @return Removed compartment
     */
    public Compartment removeCompartment(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.remove(compartment);
        }
        System.out.println("Error: There is no such compartment.");
        return null;
    }

    /**
     * Obtains the number of compartments in this row
     *
     * @return Number of compartments
     */
    public int getNumberOfCompartments() {
        return this.compartments.size();
    }

    /**
     * Checks if there is a product in the container
     *
     * @param compartment Compartment slot to check
     * @return Container has a product
     */
    public boolean hasProduct(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).hasProduct();
        }
        System.out.println("Error: There is no such compartment.");
        return false;
    }

    /**
     * Obtains the name of the product in the container
     *
     * @param compartment Compartment slot to check
     * @return Product name
     */
    public String getProductName(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).getProductName();
        }
        System.out.println("Error: There is no such compartment.");
        return "";
    }

    /**
     * Obtains the capacity of the compartment
     *
     * @param compartment Compartment slot to check
     * @return Compartment capacity
     */
    public int getCapacity(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).getCapacity();
        }
        System.out.println("Error: There is no such compartment.");
        return 0;
    }

    /**
     * Obtains the stock of the product in the compartment
     *
     * @param compartment Compartment slot to check
     * @return Product stock
     */
    public int getStock(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).getStock();
        }
        System.out.println("Error: There is no such compartment.");
        return 0;
    }

    /**
     * Obtains the price of the product in the container
     *
     * @param compartment Compartment slot to check
     * @return Product price
     */
    public int getPrice(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).getPrice();
        }
        System.out.println("Error: There is no such compartment.");
        return 0;
    }

    /**
     * Removes one unit from the container
     *
     * @param compartment Compartment slot to check
     * @return Was it successful?
     */
    public boolean dropProduct(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).dropProduct();
        }
        System.out.println("Error: There is no such compartment.");
        return false;
    }

    /**
     * Changes the product in the container
     *
     * @param compartment Compartment slot to check
     * @param name New name
     * @param stock Amount of stock
     * @param price Product price
     * @return Old product stock
     */
    public int changeProduct(int compartment, String name, int stock, int price) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).newProduct(name, stock, price);
        }
        System.out.println("Error: There is no such compartment.");
        return 0;
    }

    /**
     *
     * @param compartment Compartment slot to check
     * @param price New product price
     */
    public void setPrice(int compartment, int price) {
        if (this.compartments.containsKey(compartment)) {
            this.compartments.get(compartment).setPrice(price);
        } else {
            System.out.println("Error: There is no such compartment.");
        }
    }

    /**
     *
     * @param compartment Compartment slot to check
     * @param quantity
     * @return
     */
    public int refillCompartment(int compartment, int quantity) {
        if (this.compartments.containsKey(compartment)) {
            if (quantity > 0) {
                return this.compartments.get(compartment).refill(quantity);
            }
            System.out.println("Error: Invalid quantity.");
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    /**
     * Removes the stock of product of the container
     *
     * @param compartment Compartment slot to check
     * @return The old stock
     */
    public int emptyCompartment(int compartment) {
        if (this.compartments.containsKey(compartment)) {
            return this.compartments.get(compartment).empty();
        }
        System.out.println("Error: There is no such compartment.");
        return 0;
    }
}
