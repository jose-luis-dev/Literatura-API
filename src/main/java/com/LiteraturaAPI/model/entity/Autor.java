package com.LiteraturaAPI.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(name = "fecha_nacimiento")
    private Integer fechaNacimiento;
    @Column(name = "fecha_fallecimiento")
    private Integer fechaFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

}
