package com.poc.fetch.model.DAO;

import com.poc.fetch.model.entity.Estudiante;
import com.poc.fetch.util.LoadFetchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstudianteDAO implements IEstudianteDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estudiante> getAllStudents() {
        Query query = entityManager.createNamedQuery("getAllJoin", Estudiante.class);//COn Join
        //Query query = entityManager.createNamedQuery("getAll", Estudiante.class);//Sin Join
        List<Estudiante> estudiantes = query.getResultList();

        return estudiantes;
    }

    @Override
    public List<Estudiante> getByNombre(String nombre) {
        Query query = entityManager.createNamedQuery("getByNameJoin", Estudiante.class);//Con Join
        //Query query = entityManager.createNamedQuery("getByName", Estudiante.class);//SIn Join
        query.setParameter(1, "%".concat(nombre).concat("%"));
        List<Estudiante> estudiantes = query.getResultList();
        return estudiantes;
    }

    public LoadFetchUtil<Estudiante> getAllStudentsF(int fetchSize) {
        return new LoadFetchUtil<>(entityManager, "getAll", fetchSize, LoadFetchUtil.NAMED_QUERY_TYPE);//Sin join
        //return new LoadFetchUtil<>(entityManager, "getAllIds", "getAllFetchIn", fetchSize, LoadFetchUtil.NAMED_QUERY_TYPE);//Con join
    }

    public LoadFetchUtil<Estudiante> getByNombreF(String nombre, int fetchSize) {
        return new LoadFetchUtil<>(entityManager, "getByName", fetchSize, LoadFetchUtil.NAMED_QUERY_TYPE, "%".concat(nombre).concat("%"));//Sin join
        //return new LoadFetchUtil<>(entityManager, "getByNameIds", "getByNameFetchIn", fetchSize, LoadFetchUtil.NAMED_QUERY_TYPE, "%".concat(nombre).concat("%"));//Con join
    }

}
