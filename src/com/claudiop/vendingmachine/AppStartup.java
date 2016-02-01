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
public class AppStartup {

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(123456, 6);
        for (int row = 0; row < 3; row++) {//Create 3 rows
            machine.insertRow((char) ('A' + row), 5);
            for (int comp = 0; comp < 5; comp++) {//with 5 compartments
                machine.insertCompartment((char) ('A' + row), comp, 8, "", 0, 0);
            }
        }
        for (int row = 0; row < 3; row++) {//and other 3 rows
            machine.insertRow((char) ('D' + row), 5);
            for (int comp = 0; comp < 10; comp++) {//with 12 compartments
                machine.insertCompartment((char) ('D' + row), comp, 12, "", 0, 0);
                //row compartment capacity product stock price
            }
        }

        //row compartment product stock price
        machine.changeProduct('A', 0, "Banana", 100, 100);
        machine.changeProduct('C', 4, "Apple", 100, 500);
        machine.changeProduct('F', 9, "Orange", 100, 999);
        machine.start();
    }

}
