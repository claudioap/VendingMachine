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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Cláudio Pereira
 */
public class VendingMachine {

    final private Row[] rows;
    private boolean running;
    private Keyboard keyboard;
    private Interpreter interpreter;
    private Screen screen;
    SimpleDateFormat date;
    private boolean root;

    public VendingMachine(int maximumRows, String key) {
        if (maximumRows > 0 && maximumRows < 20) {
        } else {
            System.out.println("Error: Invalid row capacity");
        }
        this.rows = new Row[maximumRows];
        this.running = false;
        this.keyboard = new Keyboard(false);
        this.screen = new Screen();
        this.interpreter = new Interpreter(true, key);
        this.date = new SimpleDateFormat("dd-MM ' ' HH:mm:ss");
        this.root = false;
    }

    private boolean rowExists(int row) {
        return row >= 0 && row < this.rows.length;
    }

    public void insertRow(int row, int compartments, int compartmentCapacity) {
        if (rowExists(row)) {
            if (compartments > 0 && compartmentCapacity > 0) {
                this.rows[row] = new Row(compartments);
            } else {
                System.out.println("Error: Invalid number of compartments and/or capacity");
            }
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public void removeRow(int row) {
        if (rowExists(row)) {
            this.rows[row] = null;
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public void insertCompartment(int row, int capacity, String product, int stock, int price) {
        if (rowExists(row)) {
            this.rows[row].addCompartment(row, capacity, product, stock, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public void removeCompartment(int row, int compartment) {
        if (rowExists(row)) {
            this.rows[row].removeCompartment(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public int getNumberOfCompartments(int row) {
        if (rowExists(row)) {
            return this.rows[row].getNumberOfCompartments();
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public void dropProduct(int row, int compartment) {
        if (rowExists(row)) {
            this.rows[row].dropProduct(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public String getProductName(int row, int compartment) {
        if (rowExists(row)) {
            return this.rows[row].getProductName(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
        return "";
    }

    public int getCapacity(int row, int compartment) {
        if (rowExists(row)) {
            return this.rows[row].getCapacity(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public int getStock(int row, int compartment) {
        if (rowExists(row)) {
            return this.rows[row].getStock(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public int getPrice(int row, int compartment) {
        if (rowExists(row)) {
            return this.rows[row].getPrice(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public void setPrice(int row, int compartment, int price) {
        if (rowExists(row)) {
            this.rows[row].setPrice(compartment, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public int refillCompartment(int row, int compartment, int units) {
        if (rowExists(row)) {
            return this.rows[row].refillCompartment(compartment, units);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public int changeProduct(int row, int compartment, String product, int stock, int price) {
        if (rowExists(row)) {
            return this.rows[row].changeProduct(compartment, product, stock, price);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    public int emptyCompartment(int row, int compartment) {
        if (rowExists(row)) {
            return this.rows[row].emptyCompartment(compartment);
        } else {
            System.out.println("Error: Invalid row");
        }
        return 0;
    }

    //This would make much more sense if I was allowed to use threads and timers.
    public void start() {
        if (this.running) {
            System.out.println("Warning: The machine was already started.");
        } else {
            loop();
        }
    }

    public void stop() {
        this.running = false;
    }

    private void loop() {
        ArrayList<Action> actionList;
        while (this.running) {
            takeAction(
                    this.interpreter.parse(
                            this.keyboard.read()
                    )
            );
            //Can't implement Thread.sleep(100);
        }
    }

    private void takeAction(ArrayList<Action> actionList) {
        Iterator<Action> iter = actionList.iterator();
        Action action;
        while (iter.hasNext()){
             action = iter.next();
             switch(action.type()){
                case ESCALATE:
                    this.root = true;
                    //TODO Make a way to change to regular user again
                    break;
                case DROP:
                    break;
                case SHOW:
                    break;
                case CLEAR:
                    break;
                default:
                    //nothing so far. Think about this
             }
        }
    }
}
