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

import java.util.Scanner;

/**
 *
 * @author Cláudio Pereira
 */
public class Keyboard {

    public boolean embededKeyboard; //So far this can be public
    final private Scanner scanner;
    private String buffer;
    private boolean lockedBuffer;

    public Keyboard(boolean embededKeyboard) {
        this.scanner = new Scanner(System.in);
        this.embededKeyboard = embededKeyboard;
    }

    public void buttonPress(char keycode) {

        if (lockedBuffer) {
            System.out.println("Processing...");
        } else {
            //Numbers from 0 to 9 or letters from A to F, Acknowledge/Ok = 10, Cancel = 24
            if (keycode > 47 && keycode < 58 || keycode > 64 && keycode < 71 || keycode == 24) {
                this.buffer += keycode;
            } else if (keycode == 10) {
                this.lockedBuffer = true;
                this.buffer += keycode;
            } else {
                System.out.println("Warning: Invalid char");
            }
        }
    }

    public String read() {
        if (this.embededKeyboard) {
            String result = this.buffer;
            this.buffer = "";
            this.lockedBuffer = false;
            return result;
        } else if (this.scanner.hasNext()) {
            return this.scanner.next().trim().toUpperCase();
        }
        return "";
    }

}
