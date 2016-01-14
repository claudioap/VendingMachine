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
public class Row {

    private Compartment[] compartments;

    public Row(int slots) {
        if (slots > 0) {
            this.compartments = new Compartment[slots];
        } else {
            System.out.println("Error: The number of compartments and/or the compartment capacity is invalid");
        }
    }

    private boolean compartmentExists(int compartment) {
        return compartment >= 0 && compartment < this.compartments.length;
    }

    public void addCompartment(int compartment, int compartmentSize, String product, int stock, int price) {
        if (compartmentExists(compartment) || compartmentSize > 0) {
            this.compartments[compartment]
                    = new Compartment(product, stock, price, compartmentSize);
        } else {
            System.out.println("Error: There is no such compartment slot or the its size isn't positive.");
        }
    }

    public void removeCompartment(int compartment) {
        if (compartmentExists(compartment)) {
            this.compartments[compartment] = null;
        } else {
            System.out.println("Error: There is no such compartment.");
        }
    }

    public int getNumberOfCompartments() {
        return this.compartments.length;
    }

    public String getProductName(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].getProductName();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return "";
    }

    public int getCapacity(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].getCapacity();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    public int getStock(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].getStock();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    public int getPrice(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].getPrice();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    public void dropProduct(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            this.compartments[compartment].dropProduct();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
    }

    public int changeProduct(int compartment, String name, int stock, int price) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].newProduct(name, compartment, compartment);
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    public void setPrice(int compartment, int price) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            this.compartments[compartment].setPrice(price);
        } else {
            System.out.println("Error: There is no such compartment.");
        }
    }

    public int refillCompartment(int compartment, int quantity) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            if (quantity > 0) {
                return this.compartments[compartment].refill(quantity);
            }
            System.out.println("Error: Invalid quantity.");
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }

    public int emptyCompartment(int compartment) {
        if (compartmentExists(compartment) && this.compartments[compartment] != null) {
            return this.compartments[compartment].empty();
        } else {
            System.out.println("Error: There is no such compartment.");
        }
        return 0;
    }
}
