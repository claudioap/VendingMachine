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
public class Interpreter {
    private String oldInput;
    
    public Interpreter() {
        this.oldInput = "";
    }
    
    private String getLastFourChars(String input){
        this.oldInput += input;
        //Any compartment can be selected with 4 chars, anything besides that can be discarded from memory
        //Eg. A12[Ack] = 4 chars
        if(this.oldInput.length() - 5 > 0){
            this.oldInput = this.oldInput.substring(this.oldInput.length());
        }
        return oldInput;
    }
    
    static private String parseCompartment(String location){
        if(location.length() < 3){
            return "";
        }
        if(location.length() > 3){
            location = location.substring(location.length()-4);
        }
        if(isUpperCaseLetter(location.charAt(0)) && isNumber(location.charAt(1))
                && isNumber(location.charAt(2))){
            return location;
        }
        return "";
    }
    
    //Can't use Integer.parseInt(), it requires exception handling
    static private boolean isNumber(char variable){
        return (variable > 47 && variable < 58);
    }
    
    static private boolean isUpperCaseLetter(char variable){
        return (variable > 64 && variable < 91);
    }
    
    
    //TODO make this method either more elegant or break it apart
    public Action  parse(String input){
        final char acknowledge = 10;
        final char cancel = 24;
        Action action = new Action();
        if(input.equals("")){//Nothing was typed
            action.doNothing = true;
        }else if(input.charAt(input.length()-1) == cancel ){ //If last button pressed was cancel
            action.clear = true;
        } else {
            String command = getLastFourChars(input);
            String subCommands[] = command.split(Character.toString(cancel));//Split instruction based on cancel's
            command = subCommands[subCommands.length -1];//And get the part after the last cancel
            subCommands = command.split(Character.toString(acknowledge));//Split instruction based on ack's
            if(command.charAt(subCommands.length -1) == acknowledge){ // If last chat was an ack
                //Then parse what was before it to find a location
                String location = parseCompartment(subCommands[subCommands.length -1]);
                if(location.equals("")){
                    action.locationError = true;
                }else{
                    action.row = 65 - (int)location.charAt(0);
                    //The pain that is not being able to use error handling yet...
                    action.container = 10 * (48 - (int)location.charAt(1)) + 48 - (int)location.charAt(2);
                }
            }else{
                //If last wasn't an aknowledge nor cancel then the user was typing 
                command = subCommands[subCommands.length -1];
                if(command.length()>3){ //Take the first char out
                    action.typeText = command.substring(1);
                }
            }
        }
        return action;
    }
}
