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
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Cláudio Pereira
 */
public class VendingMachine {

    private HashMap<Character, Row> rows;
    private boolean running;
    private final Keyboard keyboard;
    private final Interpreter interpreter;
    private final Screen screen;
    SimpleDateFormat date;
    private boolean root;
    private final int maxRows;
    private static final String BRANDING = "";

    public VendingMachine(String key, int rows) {
        if (key == null || key.trim().equals("")) {
            System.out.println("Error: Invalid key");
            this.interpreter = new Interpreter(true, "000000");
        } else {
            this.interpreter = new Interpreter(true, key);
        }
        if (rows > 0 && rows < 26) {
            this.maxRows = rows;
        } else {
            this.maxRows = 6;
            System.out.println("Error: Invalid number of rows");
        }
        this.rows = new HashMap(this.maxRows);
        this.running = false;
        this.keyboard = new Keyboard(false);
        this.screen = new Screen(true);
        this.date = new SimpleDateFormat("dd-MM ' ' HH:mm:ss");
        this.root = false;
    }

    private boolean isWithinRowRange(char row) {
        return this.maxRows + 64 > (int) (row) && (int) (row) > 64;
    }

    public void insertRow(char row, int compartments, int compartmentCapacity) {
        if (!isWithinRowRange(row)) {
            System.out.println("Error: Invalid row");
        } else if (compartments > 0 && compartmentCapacity > 0) {
            if (this.rows.containsKey(row)) {
                System.out.println("Error: Row exists");
            } else {
                this.rows.put(row, new Row(compartments));
            }
        } else {
            System.out.println("Error: Invalid number of compartments and/or capacity");
        }
    }

    public Row removeRow(char row) {
        if (this.rows.containsKey(row)) {
            return this.rows.remove(row);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    public void insertCompartment(char row, int compartment, int capacity, String product, int stock, int price) {
        if (this.rows.containsKey(row)) {
            //This is being checked here because it is specific to the vending machine, not to the container
            if (price < 1000) {
                this.rows.get(row).addCompartment(compartment, capacity, product, stock, price);
            } else {
                System.out.println("Error: The price is too high");
            }
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public Compartment removeCompartment(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).removeCompartment(compartment);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    public int getNumberOfCompartments(char row) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).getNumberOfCompartments();
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public boolean dropProduct(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).dropProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    public String getProductName(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).getProductName(compartment);
        }
        System.out.println("Error: Invalid row");
        return "";
    }

    public boolean hasProduct(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).hasProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    public int getCapacity(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).getCapacity(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int getStock(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).getStock(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int getPrice(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).getPrice(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public void setPrice(char row, int compartment, int price) {
        if (this.rows.containsKey(row)) {
            if (price < 1000) {
                this.rows.get(row).setPrice(compartment, price);
            } else {
                System.out.println("Error: The price is too high.");
            }
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public int refillCompartment(char row, int compartment, int units) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).refillCompartment(compartment, units);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int changeProduct(char row, int compartment, String product, int stock, int price) {
        if (this.rows.containsKey(row)) {
            if (price < 1000) {
                return this.rows.get(row).changeProduct(compartment, product, stock, price);
            } else {
                System.out.println("Error: The price is too high.");
            }
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int emptyCompartment(char row, int compartment) {
        if (this.rows.containsKey(row)) {
            return this.rows.get(row).emptyCompartment(compartment);
        }
        System.out.println("Error: Invalid row");
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
        while (iter.hasNext()) {
            action = iter.next();
            switch (action.type()) {
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
