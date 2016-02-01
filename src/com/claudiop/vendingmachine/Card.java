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
 * Represents a vending machine card used to buy products
 *
 * @author Cláudio Pereira
 */
public class Card {

    private final int ID; //FIXME
    private int credit;
    private final int MAX_CREDIT;

    /**
     * Create a new card
     *
     * @param credit Base credit
     * @param maxCredit Max credit
     */
    public Card(int credit, int maxCredit) {
        if (credit > maxCredit) {
            this.credit = maxCredit;
            System.out.println("Warning: Maximum credid exceeded. Remaider not used");
        } else {
            this.credit = credit;
        }
        this.MAX_CREDIT = maxCredit;
        this.ID = 0;
    }

    /**
     * Obtains the credit of the card
     *
     * @return Credit
     */
    public int getCredit() {
        return credit;
    }

    /**
     * Debit the card
     *
     * @param amount Amount to debit
     * @return success
     */
    public boolean debit(int amount) {
        if (amount <= this.credit) {
            this.credit -= amount;
            return true;
        }
        return false;
    }

    /**
     * Credit the card
     *
     * @param amount Amount to credit
     * @return Credited amount
     */
    public int credit(int amount) {
        if (amount + this.credit > this.MAX_CREDIT) {
            int temp = amount - this.MAX_CREDIT;
            this.credit = this.MAX_CREDIT;
            return temp;
        }
        this.credit += amount;
        return amount;
    }

}
