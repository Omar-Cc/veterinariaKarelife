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
import com.veterinaria.veterinariakarelife.models.Cita;
import com.veterinaria.veterinariakarelife.models.Estado;

@Controller
@RequestMapping("/api/cita")
public class CitaApi {
    @Autowired
    Facade facade;

    @GetMapping("/listar")
    public ResponseEntity<List<Cita>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Cita> citas = facade.listar(TipoClase.CITA);
            if (citas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchFieldException | SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Cita> create(@RequestBody Cita entidad) {
        try {
            Cita _entidad = facade.crear(entidad, TipoClase.CITA);
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (NoSuchFieldException | SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> update(@PathVariable("id") Integer id, @RequestBody Cita entidad)
            throws NoSuchFieldException {
        try {
            if (entidad != null) {
                facade.actualizar(entidad, TipoClase.CITA);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException | NoSuchFieldException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> statusServicio(@PathVariable Integer id, @RequestBody Estado estado) {
        try {
            facade.cambiarEstado(id, estado, TipoClase.CITA);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchFieldException | SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
