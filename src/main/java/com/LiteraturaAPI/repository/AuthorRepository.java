package com.LiteraturaAPI.repository;

import com.LiteraturaAPI.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNombreIgnoreCase(String nombre);

    List<Author> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(
            Integer fechaNacimiento1,
            Integer fechaFallecimiento,
            Integer fechaNacimiento2
    );

}
