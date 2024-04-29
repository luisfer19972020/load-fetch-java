package com.poc.fetch.util;

import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.model.entity.ICsvModel;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Component
public class WriteListUtil implements IWriteListUtil {
    @Override
    public void escribirFile(List<? extends ICsvModel> records) {
        StringBuilder stringBuilder = new StringBuilder(records.get(0).getHeaders());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.csv"))) {
            for (ICsvModel model : records) {
                ((Estudiante) model).getProyectos().get(0).getTitulo();
                stringBuilder.append(model.toCsv());
            }
            writer.write(stringBuilder.toString());
            System.out.println("Escribido Correctamente :)");
            MemoryStatus.showMemoryStatus();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void escribirFile(LoadFetchUtil<? extends ICsvModel> util, String headers) {
        StringBuilder stringBuilder = new StringBuilder(headers);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.csv"))) {
            MemoryStatus.showMemoryStatus();
            for (ICsvModel model : util) {
                //((Estudiante) model).getProyectos().get(0).getTitulo();
                stringBuilder.append(model.toCsv());
            }
            System.out.println("Escribido Correctamente :)");
            writer.write(stringBuilder.toString());
            MemoryStatus.showMemoryStatus();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


}
