/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author Søren
 */
public class MyException extends Exception {
        
         public MyException(int i)
        {
        super("The partnumber " + i + " was not added, since it was already in the database");
        }
    
}
