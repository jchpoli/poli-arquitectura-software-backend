package com.apirest.backendapirest.models.dao;

import java.util.List;
import com.apirest.backendapirest.models.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPokemonDao extends JpaRepository<Pokemon, Long> {
    
    @Query("from Pokemon")
    public List<Pokemon> listPokemons();
}
