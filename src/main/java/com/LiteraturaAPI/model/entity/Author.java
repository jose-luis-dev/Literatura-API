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
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(name = "fecha_nacimiento")
    private Integer fechaNacimiento;
    @Column(name = "fecha_fallecimiento")
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor")
    private List<Book> libros;

}
