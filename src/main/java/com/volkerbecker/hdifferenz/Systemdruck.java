package com.volkerbecker.hdifferenz;

import java.io.*;


public class Systemdruck {

    public static void main(String[] args) {

        punktnummern();
    }

    public Systemdruck() {
    }

    public static void punktnummern(){
        try {
            // Dateiobjekt erstellen
            File file = new File("C:\\Users\\ZZCJ\\OneDrive - HEBERGER Group\\Desktop\\Punktnummern.txt");

            // Neue Datei erstellen
            if (file.createNewFile()) {
                System.out.println("Datei erstellt: " + file.getName());
            } else {
                System.out.println("Datei existiert bereits.");
            }

            // FileWriter zum Schreiben in die Datei verwenden
            FileWriter writer = new FileWriter(file);

            for (int i = 0; i < 100 ; i++) {

                if (i <10){
                    writer.write("121200" + i + "\n");
                }
                if (i >= 10){
                    writer.write("12120" + i + "\n");
                }
                if (i >= 100){
                    writer.write("12120" + i + "\n");
                }
            }
            writer.close();

            System.out.println("Erfolgreich in die Datei geschrieben.");
        } catch (IOException e) {
            System.out.println("Ein Fehler ist aufgetreten.");
            e.printStackTrace();
        }
    }
}