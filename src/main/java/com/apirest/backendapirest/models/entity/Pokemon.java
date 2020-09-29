package com.apirest.backendapirest.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pokemons")
public class Pokemon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer id_pokemon;
    private String name;
    private String type;
    private String total;
    private Integer hp;
    private Integer defense;
    private Integer attack;
    private Integer sp_atk;
    private Integer sp_def;
    private Integer speed;
    private Integer generation;
    private Integer legendary;
    private String legenday_descripcion;

    //*******************  Metodos Getters y Setters *********************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Integer id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getSp_atk() {
        return sp_atk;
    }

    public void setSp_atk(Integer sp_atk) {
        this.sp_atk = sp_atk;
    }

    public Integer getSp_def() {
        return sp_def;
    }

    public void setSp_def(Integer sp_def) {
        this.sp_def = sp_def;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public Integer getLegendary() {
        return legendary;
    }

    public void setLegendary(Integer legendary) {
        this.legendary = legendary;
    }

    public String getLegenday_descripcion() {
        return legenday_descripcion;
    }

    public void setLegenday_descripcion(String legenday_descripcion) {
        this.legenday_descripcion = legenday_descripcion;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
