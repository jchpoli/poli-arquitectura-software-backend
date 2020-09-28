package com.apirest.backendapirest.services;

import java.util.List;
import com.apirest.backendapirest.models.entity.Pokemon;

public interface IPokemonService {

    public List<Pokemon> listPokemons();
    public Pokemon save(Pokemon pokemon);
    public Pokemon findById(Long id);
    public void delete(Long id);
}