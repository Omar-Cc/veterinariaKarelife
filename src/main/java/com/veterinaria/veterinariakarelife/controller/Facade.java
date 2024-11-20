
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.veterinaria.veterinariakarelife.models.Estado;

@Component
public class Facade {
    private final FactorySystem factorySystem;
    
    public Facade() {
        this.factorySystem = new FactorySystem();
    }

    public <T> T crear(T objeto, TipoClase tipoClase) throws NoSuchFieldException, SQLException {
        Operacion<T> operacion = factorySystem.obtenerOperacion(tipoClase);
        operacion.crear(objeto);
        return objeto;
    }

    public <T> void actualizar(T objeto, TipoClase tipoClase) throws NoSuchFieldException, SQLException {
        Operacion<T> operacion = factorySystem.obtenerOperacion(tipoClase);
        operacion.actualizar(objeto);
    }

    public <T> List<T> listar(TipoClase tipoClase) throws NoSuchFieldException, SQLException {
        Operacion<T> operacion = factorySystem.obtenerOperacion(tipoClase);
        System.out.println(operacion);
        return operacion.listar();
    }

    public void cambiarEstado(int id, Estado estado, TipoClase tipoClase) throws NoSuchFieldException, SQLException {
        Operacion<?> operacion = factorySystem.obtenerOperacion(tipoClase);
        operacion.cambiarEstado(id, estado);
    }

}
