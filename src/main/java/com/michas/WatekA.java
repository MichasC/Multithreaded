package com.michas;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

//thread that takes care of analyzing one specific .xlsx. It uses ExecutorService for creating another threads B
//for analyzing its input file.
public class WatekA implements Callable<List<String>> {

    private String name;
    private String arguments;
    private int numCell;
    private String pierwszywiersz;
    private List<String> list = new ArrayList<>();
    private Future<String> stringFuture;
    private List<Future<String>> result = new ArrayList<>();

    public WatekA(String name, String arguments, String wierszpi) {
        this.name = name;
        this.arguments = arguments;
        this.pierwszywiersz = wierszpi;
    }

    @Override
    public List<String> call() {
        try {
            generateFiles generateFiles = new generateFiles();
            Sheet sheet = generateFiles.makeSheet(name);
            for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                if (sheet.getRow(0).getCell(i) != null) {
                    if (sheet.getRow(0).getCell(i).toString().contains(pierwszywiersz)) {
                        numCell = i;
                        break;
                    }
                }
            }

            //creating ExecutorService, that will create Watek B in the maximum number of 15
            ExecutorService exec = Executors.newFixedThreadPool(15); //maximal numbers of threads
            for (int x = 1; x <= sheet.getLastRowNum(); x++) {
                Row row = sheet.getRow(x);
                stringFuture = exec.submit(new WatekB(row, x, numCell, arguments));
                result.add(stringFuture);
            }


            while (result.size() > 0) {
                for (Iterator<Future<String>> it = result.iterator();
                     it.hasNext(); ) {
                    Future<String> fut = it.next();
                    if (fut.isDone()) {
                        try {
                            if (fut.get() != null) {
                                list.add(fut.get());
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        it.remove();
                    }
                }
            }

            exec.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
