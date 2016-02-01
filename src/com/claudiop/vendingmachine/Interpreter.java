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

import java.util.ArrayList;

/**
 *
 * @author Cláudio Pereira
 */
public class Interpreter {

    private String buffer;
    private boolean continuous;
    private String secret;
    private final String ok;
    private final String cancel;

    public Interpreter(boolean continuous, String secret) {
        this.buffer = "";
        this.continuous = continuous;
        this.secret = secret;
        this.ok = Character.toString((char) 0);//FIXME
        this.cancel = Character.toString((char) 0);//FIXME
    }

    public ArrayList<Action> parse(String input) {
        if (input.trim().equals("")) {//FIXME isempty
            return new ArrayList<>();
        } else {
            this.buffer = this.continuous ? this.buffer + input : input;
            filterLastCommands();
            return findActions();
            //Add action showText with the buffer remainings
        }
    }

    //Filters the issued commands which weren't cancelled
    private void filterLastCommands() {
        if (this.buffer.endsWith(this.cancel)) {
            this.buffer = this.cancel;
        }
        //Ignore anything before the last cancel
        if (this.buffer.contains(this.cancel)) {
            String temp[] = this.buffer.split(this.cancel);
            this.buffer = temp[temp.length - 1];
        }
    }

    private ArrayList<Action> findActions() {
        ArrayList<Action> actions = new ArrayList();
        //Filter redundant OK's
        while (this.buffer.length() > 0 && this.buffer.startsWith(this.ok)) {
            this.buffer = this.buffer.substring(1);
        }
        //If there was nothing left, or last command as a cancel command
        if (this.buffer.length() == 0 || this.buffer.equals(this.cancel)) {
            actions.add(new Action(ActionType.CLEAR, null));
            return actions;
        }
        boolean lastWasOk = this.buffer.endsWith(this.ok);
        String userInput[] = this.buffer.split(this.ok);
        for (String input : userInput) {
            if (input.equals(this.secret)) {
                actions.add(new Action(ActionType.ESCALATE, null));
            } else if (isPosition(input)) {
                actions.add(new Action(ActionType.DROP, input));
            }
        }
        if (!lastWasOk) {
            this.buffer = userInput[userInput.length - 1];
            actions.get(actions.size() - 1).dropToShow();
        }
        return actions;
    }

    private boolean isPosition(String input) {
        if (input.length() == 2) {
            return input.charAt(0) > 64 && input.charAt(0) < 91
                    && input.charAt(1) > 47 && input.charAt(1) < 58;
        }
        return false;
    }

    public void setContinuous(boolean value) {
        this.continuous = value;
    }

    public void changeSecret(String secret) {
        this.secret = secret;
    }

}
