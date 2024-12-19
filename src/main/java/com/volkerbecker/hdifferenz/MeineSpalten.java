package com.volkerbecker.hdifferenz;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MeineSpalten<T,R> extends TableColumn<T,R> {

        private final String title;
        private final String property;
        private final double prefWidth;

    public MeineSpalten(String title, String property, double prefWidth) {
        this.title = title;
        this.property = property;
        this.prefWidth = prefWidth;
    }

    public TableColumn<T, R> createColumn() {

        TableColumn<T, R> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(prefWidth);

        return column;
    }

    }


