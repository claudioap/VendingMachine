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
public class CardReader {

    private Card card = null;

    public CardReader() {

    }

    public boolean hasCard() {
        return this.card != null;
    }

    public boolean insertCard(Card card) {
        if (this.card != null && card != null) {
            this.card = card;//TODO Make a copy instead of an assigment
            return true;
        }
        System.out.println("Error: There is already a card in the machine");
        return false;
    }

    public void removeCard() {
        this.card = null;
        //Return the removed card, maybe
    }

    public int getCredit() {
        if (this.card != null) {
            return this.card.getCredit();
        }
        System.out.println("Warning: No card in the machine");
        return 0;
    }

    public boolean debit(int amount) {
        if (this.card != null) {
            return this.card.debit(amount);
        }
        System.out.println("Warning: No card in the machine");
        return false;
    }
}
