package com.apirest.backendapirest.controllers;

import com.apirest.backendapirest.commons.RestUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.apirest.backendapirest.models.entity.Pokemon;
import com.apirest.backendapirest.services.IPokemonService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {

    @Autowired
    private IPokemonService pokemonService;

    @GetMapping("/pokemon")
    public List<Pokemon> get() {
        return pokemonService.listPokemons();
    }

    @GetMapping("/pokemon/{id}")
    public ResponseEntity<?> getPokemon(@PathVariable Long id) {

        Pokemon pokemon = null;
        Map<String, Object> response = new HashMap<>();

        try {
            pokemon = pokemonService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error en la consulta de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pokemon == null) {
            response.put("mensaje", "no existe el pokemon con el id: ".concat(id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }

    @PostMapping("/pokemon")
    public ResponseEntity<?> create(@RequestBody Pokemon pokemon, BindingResult result) {

        Pokemon pokemonNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            pokemonNew = pokemonService.save(pokemon);
        } catch (DataAccessException e) {
            System.out.println("entro");
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El Pokemon ha sido creado con éxito");
        response.put("pokemon", pokemonNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/pokemon/{id}")
    public ResponseEntity<?> update(@RequestBody Pokemon pokemon, BindingResult result, @PathVariable Long id) {

        Pokemon pokemonActual = pokemonService.findById(id);
        Pokemon pokemonUpdated;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (pokemonActual == null) {
            response.put("mensaje", "Error: no se puede editar, el pokemon ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            RestUtils.copyNonNullProperties(pokemon, pokemonActual);

            pokemonUpdated = pokemonService.save(pokemonActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "El pokemon ".concat(id.toString()).concat(" ha sido actualizado con éxito"));
        response.put("cliente", pokemonUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/pokemon/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            pokemonService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el pokemon en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El pokemon ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
