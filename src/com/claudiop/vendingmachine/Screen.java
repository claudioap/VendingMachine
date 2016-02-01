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
 * Represents a screen used to output information to the user Its optional to
 * redirect the output to the standard output of the system
 *
 * @author Cláudio Pereira
 */
public class Screen {

    private String buffer = "";
    private boolean stdout;

    /**
     * Creates the screen
     *
     * @param screenIsStdOut Is the output redirected to the system output?
     */
    public Screen(boolean screenIsStdOut) {
        this.stdout = screenIsStdOut;
    }

    /**
     * Abstraction to display the content on a preset place
     *
     * @param input
     */
    public void display(String input) {
        if (!input.equals(this.buffer)) {
            this.buffer = input;
            if (this.stdout) {
                System.out.println("Machine:" + input);
            } else {
                //Connect here to a real screen
                //Right now it goes to /dev/null, a place where no one shall go
            }
        }
    }

    /**
     * Changes the output location
     *
     * @param stdout Output to the system standard output
     */
    public void setStdout(boolean stdout) {
        this.stdout = stdout;
    }
}
