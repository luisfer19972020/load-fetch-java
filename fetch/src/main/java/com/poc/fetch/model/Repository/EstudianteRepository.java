package com.poc.fetch.model.Repository;

import com.poc.fetch.model.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante,Long> {
    public List<Estudiante> findByNombreContaining(String nombre);
}
