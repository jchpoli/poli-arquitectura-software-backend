package com.apirest.backendapirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.apirest.backendapirest.models.entity.Pokemon;
import com.apirest.backendapirest.services.IPokemonService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class PokemonController {
    

    @Autowired
    private IPokemonService pokemonService;


    @GetMapping("pokemon")
    public List<Pokemon> get(){
        return pokemonService.listPokemons();
    }


    @GetMapping("pokemon/{id}")
    public ResponseEntity<?> getPokemon(@PathVariable Long id){

        Pokemon pokemon = null;
        Map<String, Object> response = new HashMap<>();

		try {
			pokemon = pokemonService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error en la consulta de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(pokemon == null) {
			response.put("mensaje", "no existe el pokemon con el id: ".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pokemon>(pokemon, HttpStatus.OK);
    }


    @PostMapping("pokemon")
	public ResponseEntity<?> create(@RequestBody Pokemon pokemon, BindingResult result) {

		Pokemon pokemonNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			pokemonNew = pokemonService.save(pokemon);
		} catch (DataAccessException e) {
			System.out.println("entro");
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Pokemon ha sido creado con éxito");
		response.put("pokemon", pokemonNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    

    @PutMapping("/pokemon/{id}")
	public ResponseEntity<?> update(@RequestBody Pokemon pokemon, BindingResult result, @PathVariable Long id) {

        Integer id_pokemon;
        String name;
        String type;
        String total;
        Integer hp;
        Integer defense;
        Integer attack;
        Integer sp_atk;
        Integer sp_def;
        Integer speed;
        Integer generation;
        Integer legendary;
        String legenday_descripcion; 
		Pokemon pokemonActual = pokemonService.findById(id);
		Pokemon pokemonUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (pokemonActual == null) {
			response.put("mensaje", "Error: no se puede editar, el pokemon ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

        
		try {
			
			id_pokemon = pokemon.getId_pokemon() != null ? pokemon.getId_pokemon() : pokemonActual.getId_pokemon();
			name = pokemon.getName() != null ? pokemon.getName() : pokemonActual.getName();
			type = pokemon.getType() != null ? pokemon.getType() : pokemonActual.getTotal();
			total = pokemon.getTotal() != null ? pokemon.getTotal() : pokemonActual.getTotal();
			hp = pokemon.getHp() != null ? pokemon.getHp() : pokemonActual.getHp();
			defense = pokemon.getDefense() != null ? pokemon.getDefense() : pokemonActual.getDefense();
			attack = pokemon.getAttack() != null ? pokemon.getAttack() : pokemonActual.getAttack();
			sp_atk = pokemon.getSp_atk() != null ? pokemon.getSp_atk() : pokemonActual.getSp_atk();
            sp_def = pokemon.getSp_def() != null ? pokemon.getSp_def() : pokemonActual.getSp_def();
			speed = pokemon.getSpeed() != null ? pokemon.getSpeed() : pokemonActual.getSpeed();
			generation = pokemon.getGeneration() != null ? pokemon.getGeneration() : pokemonActual.getGeneration();
            legendary = pokemon.getLegendary() != null ? pokemon.getLegendary() : pokemonActual.getLegendary();
			legenday_descripcion = pokemon.getLegenday_descripcion() != null ? pokemon.getLegenday_descripcion() : pokemonActual.getLegenday_descripcion();
            
            pokemonActual.setId_pokemon(id_pokemon);
            pokemonActual.setName(name);
            pokemonActual.setType(type);
            pokemonActual.setTotal(total);
            pokemonActual.setHp(hp);
            pokemonActual.setDefense(defense);
            pokemonActual.setAttack(attack);
            pokemonActual.setSp_atk(sp_atk);
            pokemonActual.setSp_def(sp_def);
            pokemonActual.setSpeed(speed);
            pokemonActual.setGeneration(generation);
            pokemonActual.setLegendary(legendary);
            pokemonActual.setLegenday_descripcion(legenday_descripcion);
			
			pokemonUpdated = pokemonService.save(pokemonActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "El pokemon ".concat(id.toString()).concat( " ha sido actualizado con éxito"));
		response.put("cliente", pokemonUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    

	@DeleteMapping("/pokemon/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();

		try {
			pokemonService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el pokemon en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El pokemon ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    

}
