package com.volkerbecker.hdifferenz;

public interface PunktColumns {
   ColumnDefinition<Punkt, String> PUNKTNUMMER =
           new ColumnDefinition<>("Punktnummer", "punktnummer", 150);
    ColumnDefinition<Punkt, Double> RECHTSWERT =
            new ColumnDefinition<>("Rechtswert", "rechtswert", 100);
    ColumnDefinition<Punkt, Double> HOCHWERT =
            new ColumnDefinition<>("Hochwert", "hochwert", 100);
    ColumnDefinition<Punkt, Double> HOEHE =
            new ColumnDefinition<>("Höhe", "höhe", 100);
    ColumnDefinition<Punkt, Double> HDIFFERENZ =
            new ColumnDefinition<>("H-Differenz", "hdifferenz", 100);

}



