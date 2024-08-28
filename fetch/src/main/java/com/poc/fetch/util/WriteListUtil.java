package com.poc.fetch.util;

import com.poc.fetch.model.entity.ICsvModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class WriteListUtil {
    public static void escribirFile(List<? extends ICsvModel> records) {
        StringBuilder stringBuilder = new StringBuilder(records.get(0).getHeaders());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.csv"))) {
            for (ICsvModel model : records) {
                //((Estudiante) model).getProyectos().get(0).getTitulo();
                stringBuilder.append(model.toCsv());
            }
            writer.write(stringBuilder.toString());
            stringBuilder.setLength(0);
            records.clear();
            System.out.println("Escribido Correctamente :)");
            MemoryStatus.showMemoryStatus();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void escribirFile(LoadFetchUtil<? extends ICsvModel> util, String headers) {
        StringBuilder stringBuilder = new StringBuilder(headers);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.csv"))) {
            MemoryStatus.showMemoryStatus();
            for (ICsvModel model : util) {
                //((Estudiante) model).getProyectos().get(0).getTitulo();
                stringBuilder.append(model.toCsv());
            }
            System.out.println("Escribido Correctamente :)");
            writer.write(stringBuilder.toString());
            stringBuilder.setLength(0);
            MemoryStatus.showMemoryStatus();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void escribirFileMejorado(LoadFetchUtil<? extends ICsvModel> util, String headers) {
        MemoryStatus.showMemoryStatus();
        writeSB(headers, false);
        int limit = util.getFetchSize();
        Iterator<ICsvModel> iterator = (Iterator<ICsvModel>) util.iterator();
        escribeDatos(limit, iterator);
        MemoryStatus.showMemoryStatus();
    }

    private static void escribeDatos(int limit, Iterator<ICsvModel> iterator) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (iterator.hasNext()) {
            sb.append(iterator.next().toCsv());
            index++;
            if (index == limit) {
                writeSB(sb.toString(), true);
                sb.setLength(0);
                index = 0;
            }
        }
        writeSB(sb.toString(), true);
    }

    private static void writeSB(String sb, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.csv", append))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
