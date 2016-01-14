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
public class VendingMachine {

    final private int maximumRows;
    final private Row[] rows;

    public VendingMachine(int maximumRows) {
        if (maximumRows > 0 && maximumRows < 20) {
            this.maximumRows = maximumRows;
        } else {
            this.maximumRows = 0;
            System.out.println("Error: Invalid row capacity");
        }
        rows = new Row[this.maximumRows];
    }

    public void insertRow(int row, int compartments, int compartmentCapacity) {
        if (row < this.maximumRows && row > 0) {
            if (compartments > 0 && compartmentCapacity > 0) {
                rows[row] = new Row(compartments);
            } else {
                System.out.println("Error: Invalid number of compartments and/or capacity");
            }
        } else {
            System.out.println("Error: Invalid row");
        }
    }

}
