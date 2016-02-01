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
 * Represents a vending machine pseudo-action
 *
 * @author Cláudio Pereira
 */
public class Action {

    /**
     * The action to execute
     */
    public ActionType action;

    /**
     * Details on the action
     */
    public String parameter;

    /**
     * Builds an action to execute
     *
     * @param action The action to execute
     * @param parameter Extra detail
     */
    public Action(ActionType action, String parameter) {
        this.action = action;
        this.parameter = parameter;
    }

    /**
     * Obtains the action type
     *
     * @return ActionType
     */
    public ActionType type() {
        return this.action;
    }

    /**
     * Obtains the additional details of the action
     *
     * @return
     */
    public String getParameter() {
        return this.parameter;
    }

    /**
     * Changes the type of the action from DROP to SHOW
     */
    public void dropToShow() {
        if (action == ActionType.DROP) {
            this.action = ActionType.SHOW;
        } else {
            System.out.println("Warning: This is not a drop action");
        }
    }
}
