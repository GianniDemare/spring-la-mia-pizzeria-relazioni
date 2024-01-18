package org.learning.springlamiapizzeriacrud.repository;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    // METODO CHE CERCA TUTTI I LIBRI IL CUI TITOLO CONTIENE UNA STRINGA DI RICERCA
    List<Pizza> findByNameContaining(String nameFind);


}

