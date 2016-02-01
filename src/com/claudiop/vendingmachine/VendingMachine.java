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

    private final HashMap<Character, Row> ROWS;
    private boolean running = false;
    private final Keyboard KEYBOARD = new Keyboard(false);
    ;
    private final CardReader CARD_READER = new CardReader();
    private final Interpreter INTERPRETER;
    private final Screen SCREEN = new Screen(true);
    SimpleDateFormat date = new SimpleDateFormat("dd-MM ' ' HH:mm:ss");
    private boolean root = false;
    private final int MAX_ROWS;
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

    public VendingMachine(String key, int rows) {
        if (key == null || key.trim().equals("")) {
            System.out.println("Error: Invalid key");
            this.INTERPRETER = new Interpreter(true, "000000");
        } else {
            this.INTERPRETER = new Interpreter(true, key);
        }
        if (rows > 0 && rows < 26) {
            this.MAX_ROWS = rows;
        } else {
            this.MAX_ROWS = 6;
            System.out.println("Error: Invalid number of rows");
        }
        this.ROWS = new HashMap(this.MAX_ROWS);
        this.SCREEN.display(this.BRANDING);
    }

    private boolean isWithinRowRange(char row) {
        return this.MAX_ROWS > (int) (row - 'A') && (int) (row) >= 'A';
    }

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

    public Row removeRow(char row) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.remove(row);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    public void insertCompartment(char row, int compartment, int capacity, String product, int stock, int price) {
        if (this.ROWS.containsKey(row)) {
            this.ROWS.get(row).addCompartment(compartment, capacity, product, stock, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public Compartment removeCompartment(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).removeCompartment(compartment);
        }
        System.out.println("Error: Invalid row");
        return null;
    }

    public int getNumberOfCompartments(char row) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getNumberOfCompartments();
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public boolean dropProduct(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).dropProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    public String getProductName(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getProductName(compartment);
        }
        System.out.println("Error: Invalid row");
        return "";
    }

    public boolean hasProduct(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).hasProduct(compartment);
        }
        System.out.println("Error: Invalid row");
        return false;
    }

    public int getCapacity(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getCapacity(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int getStock(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getStock(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int getPrice(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).getPrice(compartment);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public void setPrice(char row, int compartment, int price) {
        if (this.ROWS.containsKey(row)) {
            this.ROWS.get(row).setPrice(compartment, price);
        } else {
            System.out.println("Error: Invalid row");
        }
    }

    public int refillCompartment(char row, int compartment, int units) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).refillCompartment(compartment, units);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int changeProduct(char row, int compartment, String product, int stock, int price) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).changeProduct(compartment, product, stock, price);
        }
        System.out.println("Error: Invalid row");
        return 0;
    }

    public int emptyCompartment(char row, int compartment) {
        if (this.ROWS.containsKey(row)) {
            return this.ROWS.get(row).emptyCompartment(compartment);
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
                            this.dropProduct(row, compartment);
                            this.SCREEN.display("Compra efetuada com sucesso."
                                    + "Volte sempre! 😉");
                        } else {
                            this.SCREEN.display("Saldo insuficiente.\n"
                                    + "O produto custa:" + this.getPrice(row, compartment) / 100.0 + "€"
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
}
