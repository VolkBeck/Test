
package com.volkerbecker.hdifferenz;
public class Punkt {
    //Attribute
    private String punktnummer;
    private double rechtswert;
    private double hochwert;
    private double höhe;
    private double hdifferenz;

//Konstruktoren
    public Punkt(){

    }
    public Punkt(String punktnummer, double rechtswert, double hochwert, double höhe, double hdifferenz) {
        this.punktnummer = punktnummer;
        this.rechtswert = rechtswert;
        this.hochwert = hochwert;
        this.höhe = höhe;
        this.hdifferenz = hdifferenz;
    }

    //Methoden



    //Getter und Setter

    public String getPunktnummer() {
        return punktnummer;
    }

    public void setPunktnummer(String punktnummer) {
        this.punktnummer = punktnummer;
    }

    public double getRechtswert() {
        return rechtswert;
    }

    public void setRechtswert(double rechtswert) {
        this.rechtswert = rechtswert;
    }

    public double getHochwert() {
        return hochwert;
    }

    public void setHochwert(double hochwert) {
        this.hochwert = hochwert;
    }

    public double getHöhe() {
        return höhe;
    }

    public void setHöhe(double höhe) {
        this.höhe = höhe;
    }

    public double getHdifferenz() {
        return hdifferenz;
    }

    public void setHdifferenz(double hdifferenz) {
        this.hdifferenz = hdifferenz;
    }
}
