package fr.geoffreypruvost.projetpruvost.helper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static List<String> getAllPalindromes(InputStreamReader in){
        List<String> l = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(in);

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                l.add(mLine);

            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.fillInStackTrace();
                }
            }
        }
        return l;
    }
}
