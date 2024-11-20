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

import com.veterinaria.veterinariakarelife.controller.ControladorCliente;
import com.veterinaria.veterinariakarelife.controller.Facade;
import com.veterinaria.veterinariakarelife.controller.TipoClase;
import com.veterinaria.veterinariakarelife.models.Cliente;
import com.veterinaria.veterinariakarelife.models.Estado;

@Controller
@RequestMapping("/api/cliente")
public class ClienteApi {
    
    @Autowired
    Facade facade;

    @Autowired
    ControladorCliente controladorCliente;

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Cliente> clientes = facade.listar(TipoClase.CLIENTE);
            if (clientes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente entidad) {
        try {
            Cliente _entidad = facade.crear(entidad, TipoClase.CLIENTE);
            return new ResponseEntity<>(_entidad, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") Integer id, @RequestBody Cliente entidad)
            throws NoSuchFieldException {
        try {
            if (entidad != null) {
                facade.actualizar(entidad, TipoClase.CLIENTE);
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
    public ResponseEntity<HttpStatus> statusServicio(@PathVariable Integer id, @RequestBody Estado estado) {
        try {
            facade.cambiarEstado(id, estado, TipoClase.CLIENTE);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Cliente> obtenerClienteByUsuaruiId(@PathVariable Integer id) {
        try {
            Cliente cliente = controladorCliente.obtenerClienteByUsuaruiId(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
