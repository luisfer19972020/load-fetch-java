package com.poc.fetch.service;

import com.poc.fetch.model.DAO.IEstudianteDAO;
import com.poc.fetch.model.Repository.EstudianteRepository;
import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.util.IWriteListUtil;
import com.poc.fetch.util.MemoryStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchService implements IFetchService {

    @Autowired
    private EstudianteRepository repository;

    @Autowired
    private IEstudianteDAO estudianteDAO;
    @Autowired
    private IWriteListUtil writeListUtil;

    @Override
    public void processFetch() {
        MemoryStatus.showMemoryStatus();
        writeListUtil.escribirFile(estudianteDAO.getAllStudents());
    }

    @Override
    public void processFetchByName(String name) {
        MemoryStatus.showMemoryStatus();
        writeListUtil.escribirFile(estudianteDAO.getByNombre(name));
    }

    @Override
    public void processFetchF() {
        writeListUtil.escribirFile(estudianteDAO.getAllStudentsF(Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
    }

    @Override
    public void processFetchByNamef(String name) {
        writeListUtil.escribirFile(estudianteDAO.getByNombreF(name, Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
    }

    @Override
    public void consultMemoryStatus() {
        MemoryStatus.showMemoryStatus();
    }
}