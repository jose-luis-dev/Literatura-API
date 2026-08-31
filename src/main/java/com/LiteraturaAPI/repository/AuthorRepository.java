package com.LiteraturaAPI.repository;

import com.LiteraturaAPI.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(
            Integer fechaNacimiento1,
            Integer fechaFallecimiento,
            Integer fechaNacimiento2
    );

}
