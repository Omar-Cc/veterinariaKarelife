package com.veterinaria.veterinariakarelife.apis;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.veterinaria.veterinariakarelife.controller.Facade;
import com.veterinaria.veterinariakarelife.controller.TipoClase;
import com.veterinaria.veterinariakarelife.models.Adopcion;
import com.veterinaria.veterinariakarelife.models.Estado;


@Controller
@RequestMapping("/api/adopcion")
public class AdopcionApi {
    
    @Autowired
    Facade facade;

    @GetMapping("/listar")
    public ResponseEntity<List<Adopcion>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Adopcion> adopciones = facade.listar(TipoClase.ADOPCION);
            if (adopciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(adopciones, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Adopcion> create(@RequestBody Adopcion entidad) {
        try {
            Adopcion _entidad = facade.crear(entidad, TipoClase.ADOPCION);
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adopcion> update(@PathVariable("id") Integer id, @RequestBody Adopcion entidad) throws NoSuchFieldException {
        try {
            if (entidad == null) {
                facade.actualizar(entidad, TipoClase.ADOPCION);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> statusAdopcion(@PathVariable Integer id, @RequestBody Estado estado) {
        try {
            facade.cambiarEstado(id, estado, TipoClase.ADOPCION);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
