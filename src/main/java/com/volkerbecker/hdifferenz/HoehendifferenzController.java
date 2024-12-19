package com.volkerbecker.hdifferenz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.logging.Level;


public class HoehendifferenzController {
    @FXML
    TableView<Punkt> tableSoll;
    @FXML
    TableView<Punkt> tableIst;
    @FXML
    Label labelDrop;
    @FXML
    TextField textSuche;
    @FXML
    ImageView myImageView;
    @FXML
    AnchorPane pane;

    TableColumn<Punkt, String> punktnummerIst;
    TableColumn<Punkt, Double> rechtswertIst;
    TableColumn<Punkt, Double> hochwertIst;
    TableColumn<Punkt, Double> hoeheIst;
    TableColumn<Punkt, String> punktnummerSoll;
    TableColumn<Punkt, Double> rechtswertSoll;
    TableColumn<Punkt, Double> hochwertSoll;
    TableColumn<Punkt, Double> hoeheSoll;
    TableColumn<Punkt, Double> hoeheDifferenz;

    Stage stage = new Stage();
    String pdftext = "";
    private final GlowButton btnClear = new GlowButton("Clear");
    private final GlowButton btnErstellen = new GlowButton("Daten erstellen");
    private String[] record;
    private static final Logger logger =  Logger.getLogger(Info.class.getName());
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private ObservableList<Punkt> punkte = FXCollections.observableArrayList();
    private ObservableList<Punkt> punkteIST = FXCollections.observableArrayList();
    private final String  benutzer = System.getenv("USERNAME");
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    Button testbtn;

    @FXML
    public void initialize() {
        btnClear.setLayoutX(20);
        btnClear.setLayoutY(485);
        btnErstellen.setLayoutX(953);
        btnErstellen.setLayoutY(484);
        pane.getChildren().addAll(btnClear,btnErstellen);

        Image image = new Image(String.valueOf(getClass().getResource("/Bilder/Kaffee.gif")));
        myImageView.setImage(image);

        labelDrop.setStyle("-fx-background-color: #add8e6");
        labelDrop.setText("loss die PDF falle \n odder beide textdateie");
        btnClear.setOnAction(actionEvent -> listenleeren());

        testbtn.setOnAction(actionEvent ->{
            Systemdruck pdf = new Systemdruck();
            pdf.createPDFDocument();
        } );

        btnErstellen.setOnAction(actionEvent ->  datenerstellen(tableIst));

        punktnummerSoll = PunktColumns.PUNKTNUMMER.createColumn();
        rechtswertSoll = PunktColumns.RECHTSWERT.createColumn();
        hochwertSoll = PunktColumns.HOCHWERT.createColumn();
        hoeheSoll = PunktColumns.HOEHE.createColumn();

        punktnummerIst = PunktColumns.PUNKTNUMMER.createColumn();
        rechtswertIst = PunktColumns.RECHTSWERT.createColumn();
        hochwertIst = PunktColumns.HOCHWERT.createColumn();
        hoeheIst = PunktColumns.HOEHE.createColumn();
        hoeheDifferenz = PunktColumns.HDIFFERENZ.createColumn();

        tableSoll.getColumns().addAll(punktnummerSoll,rechtswertSoll,hochwertSoll,hoeheSoll);
        tableIst.getColumns().addAll(punktnummerIst,rechtswertIst,hochwertIst,hoeheIst,hoeheDifferenz);

        tableSoll.setOnDragOver(event -> {
            if (event.getGestureSource() != tableSoll && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });


        labelDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != tableSoll && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        labelDrop.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            boolean höhen = false;
            boolean koord = false;
            if (db.hasFiles()) {

                success = true;

                File filekoord = null;
                File filehöhen = null;

                List<File> files = db.getFiles();
                for (File file : files) {
                    if (file.getName().equals("Höhen.txt")){
                        System.out.println("Datei heißt höhen");
                        filehöhen = new File(file.getAbsolutePath());
                        höhen = true;
                    }

                    if (file.getName().equals("Koordinaten.txt")){
                        System.out.println("Datei heißt Koordinaten");
                        filekoord = file;
                        koord = true;
                    }

                    if (höhen && koord){
                        kombinieren(filekoord,filehöhen);
                    }

                    if (file.getName().toLowerCase().endsWith(".pdf")){
                        PDFSearcher pdfSearcher = new PDFSearcher();
                        System.out.println(textSuche.getText());

                        pdftext = pdfSearcher.textvonPDF(textSuche.getText(), file);
                        pdftext = pdftext.replace(",", ".");
                        pdftext = pdftext.replace("km 0+", "");
                        pdftext = pdftext.replace(".000", "");

                        if (!pdftext.isEmpty()) {
                            neuetxtDatei(pdftext, textSuche.getText());
                        } else {
                            System.out.println("Datei ist leer");
                        }
                    }
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });

        tableIst.setOnDragOver(event -> {
            if (event.getGestureSource() != tableSoll && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                System.out.println("drag erkannt");
            }
            event.consume();
        });

        tableSoll.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                List<File> files = db.getFiles();
                for (File file : files) {
                    punkte = readPunkteFromFile(file);
                    tableSoll.getItems().addAll(punkte);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        tableIst.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                List<File> files = db.getFiles();
                for (File file : files) {
                    punkteIST = readPunkteFromFileIST(file);
                    tableIst.getItems().addAll(punkteIST);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

    }

    public ObservableList<Punkt> readPunkteFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                // Verwende "\\s+" als Trennzeichen, um mehrere Leerzeichen zu berücksichtigen
                String[] parts = line.trim().split("\\s+");

                String punktnummer = parts[0];
                double rechtswert = Double.parseDouble(parts[1]);
                double hochwert = Double.parseDouble(parts[2]);
                double hoehe = Double.parseDouble(parts[3]);
                if (parts.length == 4 && (parts[0].contains("A") || parts[0].contains("C") || parts[0].contains("G"))) {
                    punkte.add(new Punkt(punktnummer, rechtswert, hochwert, hoehe, 0));
                }

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Fehler",e);
        }
        return punkte;
    }

    public ObservableList<Punkt> readPunkteFromFileIST(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Verwende "\\s+" als Trennzeichen, um mehrere Leerzeichen zu berücksichtigen
                String[] parts = line.trim().split("\\s+");

                String punktnummer = parts[0];
                double rechtswert = Double.parseDouble(parts[1]);
                double hochwert = Double.parseDouble(parts[2]);
                double hoehe = Double.parseDouble(parts[3]);


                for (Punkt punkt : punkte) {
                    double tmprechtswert = punkt.getRechtswert();
                    double tmphochwert = punkt.getHochwert();

                    double maxrechtswert = tmprechtswert + 0.10;
                    double minrechtswert = tmprechtswert - 0.10;

                    double maxhochwert = tmphochwert + 0.10;
                    double minhochwert = tmphochwert - 0.10;

                    if ((rechtswert >= minrechtswert && rechtswert <= maxrechtswert) && (hochwert >= minhochwert && hochwert <= maxhochwert)) {

                        Punkt neuerpunkt = new Punkt(punktnummer, rechtswert, hochwert, hoehe, Math.round((hoehe - punkt.getHöhe()) * 1e10) / 1e10);
                        punkteIST.add(neuerpunkt);
                        System.out.println("Der Rechtswert von Soll ist " + punkt.getRechtswert() + " und die Punktnummer ist " + punkt.getPunktnummer() + " und der Rechswert und die Punktnummer von IST " + punktnummer + " " + rechtswert);
                        System.out.println("Die Höhe vom Sollpunkt ist " + punkt.getHöhe() + " Die Höhe vom IstPunkt ist " + hoehe + " Gerechnet = " + (punkt.getHöhe() - hoehe));
                        System.out.println(Math.round((punkt.getHöhe() - hoehe) * 1e10) / 1e10);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Fehler",e);
        }
        return punkteIST;
    }

    public void listenleeren() {
        punkte.clear();
        punkteIST.clear();
        tableSoll.setItems(punkte);
        tableIst.setItems(punkteIST);
    }

    public void datenerstellen(TableView<Punkt> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wo soll die Gaudi gespeichert werden?");
        fileChooser.setInitialDirectory(new File("C:\\Users\\" + benutzer + "\\Documents"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "+.txt"));

        File file = fileChooser.showSaveDialog(stage);

        if (!(file == null)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
                for (Punkt punkt : tableView.getItems()) {
                    writer.write(punkt.getPunktnummer() + "diff" + " " + "650" + " " + punkt.getRechtswert() + " " + punkt.getHochwert() + " " + punkt.getHdifferenz());
                    writer.newLine();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Fehler",e);
            }
            Info info = new Info(MeinAlertType.INFO,"Gespeichert","Die Datei wurde erfolgreich gespeichert");
            info.showAndWait();
        } else {
            Info info = new Info(MeinAlertType.WARNUNG,"Fehler","Es wurde keine Datei ausgewählt");
            info.showAndWait();
        }
    }


    public void neuetxtDatei(String string, String suche) {
        fileChooser.setTitle("Wo soll die Gaudi gespeichert werden?");
        fileChooser.setInitialDirectory(new File("C:\\Users\\" + benutzer + "\\Documents"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "+.txt"));
        fileChooser.setInitialFileName("Höhen.txt");
        AtomicInteger i = new AtomicInteger();

        File file = fileChooser.showSaveDialog(stage);

        if (!(file == null)) {
            final int[] count = {0};
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
                string.lines().forEach(line -> {
                    try {
                        record = line.split(" ");
                        if (record[0].equals(suche)) {
                            count[0]++;
                        }
                        try {
                            i.set(Integer.parseInt(record[0]));

                            if (count[0] == 2) {
                                writer.write("C" + record[0] + " " + record[1]);
                                writer.newLine();
                                writer.write("D" + record[0] + " " + record[2]);
                            } else {
                                writer.write("A" + record[0] + " " + record[1]);
                                writer.newLine();
                                writer.write("B" + record[0] + " " + record[2]);
                            }
                            writer.newLine();
                        } catch (NumberFormatException e) {
                            throw new NumberFormatException();
                        }

                    } catch (IOException e) {
                        logger.log(Level.SEVERE,"Fehler",e);
                    }
                });
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Fehler",e);
            }
            reverseneuetxtDatei(string, suche, file);
        } else {
            Info info = new Info(MeinAlertType.WARNUNG,"Fehler","Es wurde keine Datei ausgewählt");
            info.showAndWait();
        }
        assert file != null;
        Info info = new Info(MeinAlertType.INFO,"Erfolgreich","Neue Höhendatei wurde erfolgreich unter " + file.getAbsolutePath() + " gespeichert.");
        info.showAndWait();
    }

    public void reverseneuetxtDatei(String string, String suche, File file) {
        AtomicInteger i = new AtomicInteger();
        final int[] count = {0};
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true))) {
            writer.write("Hier sind die Höhen falls in der PDF die Tabellenhierarchie vertauscht ist....");
            writer.newLine();
            string.lines().forEach(line -> {
                try {
                    String[] record = line.split(" ");

                    if (record[0].equals(suche)) {
                        count[0]++;
                    }

                    try {
                        i.set(Integer.parseInt(record[0]));
                        if (count[0] == 2) {
                            writer.write("A" + record[0] + " " + record[1]);
                            writer.newLine();
                            writer.write("B" + record[0] + " " + record[2]);
                        } else {
                            writer.write("C" + record[0] + " " + record[1]);
                            writer.newLine();
                            writer.write("D" + record[0] + " " + record[2]);
                        }
                        writer.newLine();
                    } catch (NumberFormatException e) {
                        logger.log(Level.SEVERE,"Fehler",e);
                    }

                } catch (IOException e) {
                    logger.log(Level.SEVERE,"Fehler",e);
                }

            });

        } catch (IOException e) {
            logger.log(Level.SEVERE,"Fehler",e);
        }
    }

    public void kombinieren(File koord, File höhen) {

        String inputFilePath = koord.getAbsolutePath();
        String inputFilePath2 = höhen.getAbsolutePath();

        fileChooser.setTitle("Wo soll die Gaudi gespeichert werden?");
        fileChooser.setInitialDirectory(new File("C:\\Users\\" + benutzer + "\\Documents"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "+.txt"));
        fileChooser.setInitialFileName("84036085.txt");

        File file = fileChooser.showSaveDialog(stage);

        if (!(file == null)) {
            String outputFilePath = file.getAbsolutePath();
            StringBuilder stringBuilder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath2))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String höhendatei = stringBuilder.toString(); // Die Datei die erstellt wurde durch das Programm um Punktnummern und Höhen aus PDF zu holen

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                 String koordinaten;
                while ((koordinaten = reader.readLine()) != null) {
                    try {
                        koordinaten.lines().forEach(line -> {
                            //Wenn ein G in der Punktnummer, ist soll die Höhe um 1,28 m reduziert werden
                            if (line.contains("G")){
                                String[] tempstring = line.split(" ");
                                double hoehe = Double.parseDouble(tempstring[3]);
                                double neueHoehe =  Math.round((hoehe - 1.28) * 1e10) / 1e10;
                                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                symbols.setDecimalSeparator('.');
                                DecimalFormat decimalFormat = new DecimalFormat("#.000",symbols);
                                String höheformatiert = decimalFormat.format(neueHoehe);

                                try {
                                    writer.write(tempstring[0] + " " + tempstring[1] + " " + tempstring[2] + " " + höheformatiert);
                                    writer.newLine();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                //F für Fuge in der Punktnummer und E für Einlauf
                            } else if (line.contains("F") || line.contains("E") ) {
                                try {
                                    writer.write(line + "Fuge");
                                    writer.newLine();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            höhendatei.lines().forEach(line2 -> {
                                if (line.contains(line2.substring(0,4))) {
                                    String[] tempstring = line2.split(" ");
                                    try {
                                        writer.write(line + " " + tempstring[1]);
                                        writer.newLine();
                                        tempstring[1] = null;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        });


                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE,"Fehler",e);
            }
            Info info = new Info(MeinAlertType.INFO,"Datei gespeichert","Datei wurde erfolgreich " + file.getAbsolutePath() + " gespeichert.");
            info.showAndWait();
        }else {
            Info info = new Info(MeinAlertType.WARNUNG,"Fehler","Es wurde kein Speicherort ausgewählt ausgewählt");
            info.showAndWait();
        }
    }


}



