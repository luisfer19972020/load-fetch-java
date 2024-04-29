package com.poc.fetch.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Estudiante")
@Table(name = "estudiantes")
/*
//Ejemplo con fetch con join (Debido a que ftech join no soporta paginacion) 1 query trae ids , la segunda trae la info joiniada
@NamedQuery(name = "getAllIds", query = "SELECT e.id FROM Estudiante e")
@NamedQuery(name = "getAllFetchIn", query = "SELECT e FROM Estudiante e join fetch e.proyectos where e.id IN :ids")
@NamedQuery(name = "getByNameIds", query = "SELECT e.id FROM Estudiante e where e.nombre LIKE ?1")
@NamedQuery(name = "getByNameFetchIn", query = "SELECT e FROM Estudiante e join fetch e.proyectos where e.nombre LIKE ?1")
*/

//Ejemplo sin  Fetch con Join
@NamedQuery(name = "getAllJoin", query = "SELECT e FROM Estudiante e join fetch e.proyectos")
@NamedQuery(name = "getByNameJoin", query = "SELECT e FROM Estudiante e join fetch e.proyectos where e.nombre LIKE ?1")

//Ejempplo con/sin fetch y sin join
@NamedQuery(name = "getAll", query = "SELECT e FROM Estudiante e")
@NamedQuery(name = "getByName", query = "SELECT e FROM Estudiante e where e.nombre LIKE ?1")
public class Estudiante implements ICsvModel {

    public static final int FETCH_SIZE = 50000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    private Integer edad;

    @Column(name = "numero_control")
    private Integer numeroControl;
    private String direccion;


    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private List<Proyecto> proyectos;

    @Override
    public String toCsv() {
        return new StringBuilder()
                .append(id).append(",")
                .append(nombre).append(",")
                .append(apellidoPaterno).append(",")
                .append(apellidoMaterno).append(",")
                .append(edad).append(",")
                .append(numeroControl)
                .append("\n")
                .toString();
    }

    @Override
    public String getHeaders() {
        return "Id,Nombre,Apellido Paterno,Apellido Materno, Edad, Numero de control\n";
    }


}
