package com.apirest.backendapirest.services;

import java.util.List;
import com.apirest.backendapirest.models.dao.IPokemonDao;
import com.apirest.backendapirest.models.entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolemonServiceImpl implements IPokemonService {

    @Autowired
    private IPokemonDao pokemonDao;

    @Override
    public Pokemon save(Pokemon pokemon) {
        return pokemonDao.save(pokemon);
    }

    @Override
    public Pokemon findById(Long id) {
        return pokemonDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        pokemonDao.deleteById(id);
    }

    @Override
    public List<Pokemon> listPokemons() {
        return pokemonDao.listPokemons();
    }
    
}
