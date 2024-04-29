package com.poc.fetch.model.DAO;

import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.util.LoadFetchUtil;

import java.util.List;

public interface IEstudianteDAO {
    public List<Estudiante> getAllStudents();

    public List<Estudiante> getByNombre(String nombre);

    public LoadFetchUtil<Estudiante> getAllStudentsF(int fetchSize);

    public LoadFetchUtil<Estudiante> getByNombreF(String nombre, int fetchSize);
}
