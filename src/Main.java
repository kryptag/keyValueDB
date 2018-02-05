/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import keyvaluedb.KeyValueDB;

/**
 *
 * @author florenthaxha
 */
public class Main {
    
    
    public static void main(String[] args) throws ClassNotFoundException, IOException{
        KeyValueDB dbms = new KeyValueDB();
        if(args.length == 1){
            System.out.println(dbms.read(args[0]));
        }else if(args.length == 2){
            dbms.write(args[0], args[1]);
        }
    }
    
    
}
