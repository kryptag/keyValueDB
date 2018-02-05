/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyvaluedb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author florenthaxha
 */
public final class KeyValueDB {

    private HashMap<String , Long> hm;
    private File dbFile;
    private File hashmapFile;
    private RandomAccessFile raf;
    
    public KeyValueDB() throws FileNotFoundException, IOException, ClassNotFoundException{
        dbFile = new File(System.getProperty("user.dir")+ "/Database");
        hashmapFile = new File(System.getProperty("user.dir")+ "/hashmapdb");
        raf = new RandomAccessFile(dbFile, "rw");
        hm = getHashMap();
        
    }
    
    public HashMap getHashMap() throws IOException, ClassNotFoundException {
        if(hashmapFile.exists()){
            //populate a map and return it
            //HashMap<String, Long> hashmap = new HashMap<>();
            FileInputStream fis = new FileInputStream(hashmapFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Long> hashmap = (HashMap) ois.readObject();
            return hashmap;
        }
        
        return new HashMap<>();
    }
    
    public String read(String key) throws IOException{
        raf.seek(hm.get(key));
        String data = raf.readLine();
        String[] lines = data.split("\\s+");
        String decodeddata = new String();
        for (String line : lines) {
            decodeddata += (char) Integer.parseInt(line, 2);
        }
        return decodeddata;
    }
    
    public void write(String key, String value) throws IOException{
        raf.seek(dbFile.length());
        hm.put(key, raf.getFilePointer());
        byte[] infoBin = value.getBytes("UTF-8");
        String byteData = "";
        for (byte b : infoBin) {
            byteData += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0').concat(" ");
        }
        raf.writeBytes(byteData);
        raf.writeBytes(System.getProperty("line.separator"));
        setHashMap();
    }
    
    public void setHashMap() throws FileNotFoundException, IOException{
            FileOutputStream fos = new FileOutputStream(hashmapFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hm);
    }
}
