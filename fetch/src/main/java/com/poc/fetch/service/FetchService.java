package com.poc.fetch.service;

import com.poc.fetch.model.DAO.IEstudianteDAO;
import com.poc.fetch.model.Repository.EstudianteRepository;
import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.util.MemoryStatus;
import com.poc.fetch.util.WriteListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchService implements IFetchService {

    @Autowired
    private EstudianteRepository repository;

    @Autowired
    private IEstudianteDAO estudianteDAO;


    @Override
    public void processFetch() {
        MemoryStatus.showMemoryStatus();
        WriteListUtil.escribirFile(estudianteDAO.getAllStudents());
    }

    @Override
    public void processFetchByName(String name) {
        MemoryStatus.showMemoryStatus();
        WriteListUtil.escribirFile(estudianteDAO.getByNombre(name));
    }

    @Override
    public void processFetchF() {
        //WriteListUtil.escribirFile(estudianteDAO.getAllStudentsF(Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
        WriteListUtil.escribirFileMejorado(estudianteDAO.getAllStudentsF(Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
    }

    @Override
    public void processFetchByNamef(String name) {
        //WriteListUtil.escribirFile(estudianteDAO.getByNombreF(name, Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
        WriteListUtil.escribirFileMejorado(estudianteDAO.getByNombreF(name, Estudiante.FETCH_SIZE), "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n");
    }

    @Override
    public void consultMemoryStatus() {
        MemoryStatus.showMemoryStatus();
    }
}
