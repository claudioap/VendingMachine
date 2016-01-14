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

    public void addCompartment(int compartment, int compartmentSize) {
        if (compartment >= 0 && compartment < compartments.length || compartmentSize > 0) {
            compartments[compartment] = new Compartment(compartmentSize);
        } else {
            System.out.println("Error: There is no such compartment slot, or the its size isn't positive.");
        }
    }

    public void removeCompartment(int compartment) {
        if (compartment >= 0 && compartment < compartments.length) {
            compartments[compartment] = null;
        } else {
            System.out.println("Error: There is no such compartment.");
        }
    }
}
