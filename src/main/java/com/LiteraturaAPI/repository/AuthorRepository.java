package com.LiteraturaAPI.repository;

import com.LiteraturaAPI.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(
            Integer añoNacimiento1,
            Integer añoFallecimiento,
            Integer añoNacimiento2
    );

}
