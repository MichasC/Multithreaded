package com.michas;

import org.apache.poi.ss.usermodel.Row;

import java.util.concurrent.Callable;

//thread that analyze row from .xlsx in the context of specific argument in it
public class WatekB implements Callable<String> {

    private int actuallynumberRow;
    private Row row;
    private int numCell;
    private String arguments;
    private String zwr;


    public WatekB(Row row, int actuallynumberRow, int numCell, String argument) {
        this.row = row;
        this.actuallynumberRow = actuallynumberRow;
        this.numCell = numCell;
        this.arguments = argument;
    }

    @Override
    public String call() {

        if (row.getCell(numCell) != null) {
            String string = row.getCell(numCell).toString();
            if (string.toUpperCase().contains(arguments)) {
                zwr = row.getCell(1).toString();
                // return to corresponding thread A founded value
                return zwr;
            }
        }
        return null;
    }
}
