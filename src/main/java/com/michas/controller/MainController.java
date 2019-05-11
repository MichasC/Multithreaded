package com.michas.controller;

import com.michas.WatekA;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class MainController {

    @FXML
    private TextArea text;
    @FXML
    private TextArea text_statistics;
    private int number = 0;
    private String wierszpi;
    private String argument;
    private List<String> arrayList = new ArrayList<>();
    private Future<List<String>> stringFuture;
    private List<Future<List<String>>> list = new ArrayList<>();


    //main method that started multithreating by generating multi thread A
    private void generatedThreadA() {
        //variable that stores how many .xlsx is there to be analyzed
        int numbersFiles = 3;
        arrayList.clear();

        ExecutorService exec = Executors.newFixedThreadPool(numbersFiles); //maximum number of threads defined in numbersFiles
        for (int i = 0; i < numbersFiles; i++) {
            //creating Watek A as many times as there are specified in numbersFiles
            stringFuture = exec.submit(new WatekA("plik" + number, argument, wierszpi));
            number++;
            //adding result from Watek A to list
            list.add(stringFuture);
        }
        number = 0;

        //receiving data from threads
        try {
            while (list.size() > 0) {
                for (Iterator<Future<List<String>>> it = list.iterator(); it.hasNext(); ) {
                    Future<List<String>> fut = it.next();
                    if (fut.isDone()) {
                        try {
                            arrayList.addAll(fut.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // end of receiving data

        exec.shutdown();
    }


    @FXML
    //method used for searching all Women in .xlsx
    private void searchWomen() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Płeć";
        this.argument = "K";
        text.appendText("Wszystkie kobiety biorące udział w zawodach: \n");
        long czas = System.currentTimeMillis();
        generatedThreadA();
        System.out.println(System.currentTimeMillis() - czas) ;
        searchArray();
    }


    @FXML
    //method used for searching all Men in .xlsx
    private void searchMen() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Płeć";
        this.argument = "M";
        text.appendText("Wszyscy mężczyźni biorący udział w zawodach: \n");
        generatedThreadA();
        searchArray();
    }


    @FXML
    //method used for searching all Men in category 30-39 in .xlsx
    private void searchCategoryM3039() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Kategoria";
        this.argument = "M30-39";
        text.appendText("Wszyscy mężczyźni biorący udział w zawodach w kategorii M30-39: \n");
        generatedThreadA();
        searchArray();
    }

    @FXML
    //method used for searching all Women in category 30-39 .xlsx
    private void searchCategoryK3039() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Kategoria";
        this.argument = "K30-39";
        text.appendText("Wszystkie kobiety biorące udział w zawodach w kategorii K30-39: \n");
        generatedThreadA();
        searchArray();
    }

    @FXML
    //method used for searching all seniors (category 60+) in .xlsx
    private void searchCategorySenior() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Kategoria";
        this.argument = "M60+";
        generatedThreadA();
        this.wierszpi = "Kategoria";
        this.argument = "K60+";
        generatedThreadA();
        text.appendText("Wszyscy seniorzy: \n");
        searchArray();
    }

    @FXML
    //method used for searching all women M t-shirt in .xlsx
    private void searchTshirtKM() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "KM";
        text.appendText("Wszystkie osoby z koszulką damską M: \n");
        generatedThreadA();
        searchArray();

    }

    @FXML
    //method used for searching all men M t-shirt in .xlsx
    private void searchTshirtMM() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "MM";
        text.appendText("Wszystkie osoby z koszulką męską M: \n");
        generatedThreadA();
        searchArray();

    }

    @FXML
    //method used for searching all women S t-shirt in .xlsx
    private void searchTshirtKS() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "KS";
        text.appendText("Wszystkie osoby z koszulką damską S: \n");
        generatedThreadA();
        searchArray();

    }

    @FXML
    //method used for searching all men S t-shirt in .xlsx
    private void searchTshirtMS() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "MS";
        text.appendText("Wszystkie osoby z koszulką męską S: \n");
        generatedThreadA();
        searchArray();

    }


    @FXML
    //method used for searching all women L t-shirt in .xlsx
    private void searchTshirtKL() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "KL";
        text.appendText("Wszystkie osoby z koszulką damską L: \n");
        generatedThreadA();
        searchArray();

    }

    @FXML
    //method used for searching all men L t-shirt in .xlsx
    private void searchTshirtML() {
        text.clear();
        text_statistics.clear();
        this.wierszpi = "Koszulka";
        this.argument = "ML";
        text.appendText("Wszystkie osoby z koszulką męską L: \n");
        generatedThreadA();
        searchArray();

    }


    private void searchArray() {
        int result_counter = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null) {
                text.appendText(i + 1 + ". " + arrayList.get(i) + "\n");
                result_counter += 1;
            }
        }
        if (result_counter!=0)
            text_statistics.appendText("Znaleziono " + result_counter + " pozycji.");
        else
            text_statistics.appendText("Nie znalezion posujących pozycji.");
    }

    @FXML
    private void clooseScene(){
        Platform.exit();
    }

}


