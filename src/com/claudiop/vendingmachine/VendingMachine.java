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
 * A vending machine which holds several products and alows users and a manager
 * to interact with them
 *
 * @author Cláudio Pereira
 */
public class VendingMachine {

    private final HashMap<Character, Row> ROWS;
    private boolean running = false;
    private final Keyboard KEYBOARD = new Keyboard(false);
    private int credit = 0;
    private final CardReader CARD_READER = new CardReader();
    private final Interpreter INTERPRETER;
    private final Screen SCREEN = new Screen(true);
    SimpleDateFormat date = new SimpleDateFormat("dd-MM ' ' HH:mm:ss");
    private boolean root = false;
    private final int MAX_ROWS;
    private final int SECRET_KEY;
    private final String BRANDING
            = " _   _       _        ____   ___  \n"
            + "| \\ | | ___ | |_ __ _|___ \\ / _ \\   tm\n"
            + "|  \\| |/ _ \\| __/ _` | __) | | | |\n"
            + "| |\\  | (_) | || (_| |/ __/| |_| |\n"
            + "|_| \\_|\\___/ \\__\\__,_|_____|\\___/ \n"
            + " _ __ ___   __ _  __ _ _   _(_)_ __   __ _ ___\n"
            + "| '_ ` _ \\ / _` |/ _` | | | | | '_ \\ / _` / __|\n"
            + "| | | | | | (_| | (_| | |_| | | | | | (_| \\__ \\\n"
            + "|_| |_| |_|\\__,_|\\__, |\\__,_|_|_| |_|\\__,_|___/\n"
            + "  __| | ___  __   __|_| _ __   __| | __ _ \n"
            + " / _` |/ _ \\ \\ \\ / / _ \\ '_ \\ / _` |/ _` |\n"
            + "| (_| |  __/  \\ V /  __/ | | | (_| | (_| |\n"
            + " \\__,_|\\___|   \\_/ \\___|_| |_|\\__,_|\\__,_|";

    /**
     * Create a new vending machine
     *
     * @param key Secret root key
     * @param rows Number of supported rows
     */
    public VendingMachine(int key, int rows) {
        this.INTERPRETER = new Interpreter(true, key);
        if (rows > 0 && rows < 26) {
            this.MAX_ROWS = rows;
        } else {
            this.MAX_ROWS = 6;
            System.out.println("Error: Invalid number of rows");
        }
        this.ROWS = new HashMap(this.MAX_ROWS);
        this.SCREEN.display(this.BRANDING);
        this.SECRET_KEY = key;
    }

    private boolean isWithinRowRange(char row) {
        return this.MAX_ROWS > (int) (row - 'A') && (int) (row) >= 'A';
    }

    /**
     * Creates a new row with support to a number of compartments
     *
     * @param row Row slot
     * @param compartments Compartment capacity
     */
    public void insertRow(char row, int compartments) {
        if (!(isWithinRowRange(row))) {
            System.out.println("HARR");
            System.out.println("Error: Invalid row");
        } else if (compartments > 0) {
            if (this.ROWS.containsKey(row)) {
                System.out.println("Error: Row exists");
            } else {
                this.ROWS.put(row, new Row(compartments));
            }
        } else {
            System.out.println("Error: Invalid number of compartments and/or capacity");
        }
    }

    /**
     * Removes a row from its slot
     *
     * @param row Row slot
     * @return Removed row
     */
    public Row removeRow(char row) {
        if (!this.root) {
            System.out.println("Error: Premission denied");
            return null;
        }
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.remove(row);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    /**
     * Creates a new compartment into one of the row slots
     *
     * @param row Row
     * @param compartment Row slot
     * @param capacity Compartment capacity
     * @param product Product name
     * @param stock Product stock
     * @param price Product price
     */
    public void insertCompartment(char row, int compartment, int capacity, String product, int stock, int price) {
        if (this.ROWS.containsKey(row)) {
            this.ROWS.get(row).addCompartment(compartment, capacity, product, stock, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    /**
     * Removes a compartment from a slot in a row
     *
     * @param row Row
     * @param compartment Compartment slot
     * @return Removed compartment
     */
    public Compartment removeCompartment(char row, int compartment) {
        if (!this.root) {
            System.out.println("Error: Premission denied");
            return null;
        }
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).removeCompartment(compartment);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    /**
     * Gets the number of compartments in a row
     *
     * @param row Row
     * @return Number of compartments
     */
    public int getNumberOfCompartments(char row) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getNumberOfCompartments();
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Drops a product from a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Sucess
     */
    public boolean dropProduct(char row, int compartment) {
        if (!this.root) {
            System.out.println("Error: Premission denied");
            return false;
        }
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).dropProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    /**
     * Obtains the name of the product in a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Product name
     */
    public String getProductName(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getProductName(compartment);
        }
        System.out.println("Error: Invalid row");
        return "";
    }

    /**
     * Checks if a compartment has product on it
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Compartment has product
     */
    public boolean hasProduct(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).hasProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    /**
     * Obtains the compartment capacity
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Compartment capacity
     */
    public int getCapacity(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getCapacity(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Obtains the stock of a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return
     */
    public int getStock(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getStock(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Obtains the price of the products in a container
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Product price
     */
    public int getPrice(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getPrice(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Sets a new price to an existing container
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @param price
     */
    public void setPrice(char row, int compartment, int price) {
        if (this.ROWS.containsKey(row)) {
            this.ROWS.get(row).setPrice(compartment, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    /**
     * Refills a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @param units Units to refill
     * @return Unites refilled
     */
    public int refillCompartment(char row, int compartment, int units) {
        if (!this.root) {
            System.out.println("Error: Premission denied");
            return 0;
        }
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).refillCompartment(compartment, units);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Changes the product of a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @param product
     * @param stock
     * @param price
     * @return Old stock
     */
    public int changeProduct(char row, int compartment, String product, int stock, int price) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).changeProduct(compartment, product, stock, price);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    /**
     * Empties a compartment
     *
     * @param row Row where the compartment is
     * @param compartment Compartment slot
     * @return Old stock
     */
    public int emptyCompartment(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).emptyCompartment(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    //This would make much more sense if I was allowed to use threads and timers.
    /**
     * Starts the machine
     */
    public void start() {
        if (this.running) {
            System.out.println("Warning: The machine was already started.");
        } else {
            loop();
        }
    }

    /**
     * Stops the machine
     */
    public void stop() {
        this.running = false;
    }

    private void loop() {
        ArrayList<Action> actionList;
        boolean hasCard = this.CARD_READER.hasCard();
        while (this.running) {
            if (hasCard != this.CARD_READER.hasCard()) {
                if (this.CARD_READER.hasCard()) {
                    this.SCREEN.display("Saldo:" + this.CARD_READER.getCredit() / 100.0 + "€");
                } else {
                    this.takeAction(new Action(ActionType.CLEAR, ""));
                }
            }
            takeAction(this.INTERPRETER.parse(this.KEYBOARD.read()));
            //Can't implement Thread.sleep(100);
        }
    }

    private void takeAction(Action action) {
        ArrayList<Action> actionList = new ArrayList();
        actionList.add(action);
        this.takeAction(actionList);
    }

    private void takeAction(ArrayList<Action> actionList) {
        Iterator<Action> iter = actionList.iterator();
        Action action;
        boolean wasRoot = this.root;
        while (iter.hasNext()) {
            action = iter.next();
            String parameter = action.getParameter();
            char row = 0;
            int compartment = -1;
            if (parameter.length() == 2) {
                row = parameter.charAt(0);
                compartment = (int) (parameter.charAt(1) - '0');
            }
            switch (action.type()) {
                case ESCALATE:
                    this.root = !this.root;
                    this.SCREEN.display(
                            "Modo de administrador:"
                            + (this.root ? "Activado" : "Desativado"));
                    break;
                case DROP:
                    if (!hasProduct(row, compartment)) {
                        this.SCREEN.display("Compartimento inválido (" + parameter + ")");
                    } else if (this.getStock(row, compartment) > 0) {
                        if (!this.CARD_READER.hasCard()) {
                            this.SCREEN.display("Insira um cartão");
                        } else if (this.CARD_READER.getCredit() >= this.getPrice(row, compartment)) {
                            this.CARD_READER.debit(this.getPrice(row, compartment));
                            this.credit += this.getPrice(row, compartment);
                            this.root = true;
                            this.dropProduct(row, compartment);
                            this.root = wasRoot;
                            this.SCREEN.display("Compra efetuada com sucesso.\n"
                                    + "Volte sempre!");
                        } else {
                            this.SCREEN.display("Saldo insuficiente.\n"
                                    + "O produto custa:" + this.getPrice(row, compartment) / 100.0 + "€\n"
                                    + "Dispõe de:" + this.CARD_READER.getCredit() / 100.0 + "€");
                        }
                    } else {
                        this.SCREEN.display("Sem produto (" + parameter + ")");
                    }
                    break;
                case SHOW:
                    if (!hasProduct(row, compartment)) {
                        this.SCREEN.display("Compartimento inválido (" + parameter + ")");
                    } else if (this.getStock(row, compartment) > 0) {
                        this.SCREEN.display(
                                this.getProductName(row, compartment) + " - "
                                + this.getPrice(row, compartment) / 100.0 + "€"
                        );
                    } else {
                        this.SCREEN.display("Sem produto (" + parameter + ")");
                    }
                    break;
                case CLEAR:
                default:
                    this.SCREEN.display(date.toString()
                            + "Escolha um produto");
            }
        }
    }

    /**
     * Lets the manager preform an action assuming it is logged
     *
     * @param action Action to preform
     * @return Success
     */
    public boolean manage(Action action) {
        if (this.root) {
            return this.manage(action, this.SECRET_KEY);
        }
        System.out.println("Error: Not authenticated");
        return false;
    }

    /**
     * Lets the manager preform an action
     *
     * @param action Action to preform
     * @param key Secret key
     * @return Success
     */
    public boolean manage(Action action, int key) {
        if (key != this.SECRET_KEY) {
            System.out.println("Error: Invalid key");
        } else if (action == null) {
            System.out.println("Error: Invalid action");
        } else {
            this.takeAction(action);
            return true;
        }
        return false;
    }

    /**
     * Lets the manager preform an action assuming it is logged
     *
     * @param amount Amount to remove
     * @return Removed credit
     */
    public int removeCredit(int amount) {
        if (this.root) {
            return this.removeCredit(amount, this.SECRET_KEY);
        }
        System.out.println("Error: Not authenticated");
        return 0;
    }

    /**
     * Lets the manager preform an action
     *
     * @param amount Amount to remove
     * @param key Secret key
     * @return Removed credit
     */
    public int removeCredit(int amount, int key) {
        if (amount < 0) {
            System.out.println("Error: Invalid amount");
        } else if (this.SECRET_KEY == key) {
            if (this.credit < amount) {
                int temp = this.credit;
                this.credit = 0;
                return temp;
            }
            this.credit -= amount;
            return amount;
        } else {
            System.out.println("Error: Invalid key");
        }
        return 0;
    }

    /**
     * Lets the manager preform an action assuming it is logged
     *
     * @return Credit
     */
    public int getCredit() {
        if (this.root) {
            return this.getCredit(this.SECRET_KEY);
        }
        System.out.println("Error: Not authenticated");
        return 0;
    }

    /**
     * Lets the manager preform an action assuming it is logged
     *
     * @param key Secret key
     * @return Credit
     */
    public int getCredit(int key) {
        if (this.SECRET_KEY == key) {
            return this.credit;
        } else {
            System.out.println("Error: Invalid key");
        }
        return 0;
    }
}
