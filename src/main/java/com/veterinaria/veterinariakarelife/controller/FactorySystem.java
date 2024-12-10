
package com.veterinaria.veterinariakarelife.controller;

import java.sql.SQLException;

public class FactorySystem {

    public Operacion obtenerOperacion(TipoClase tipoClase) throws NoSuchFieldException, SQLException {

        switch (tipoClase) {
            case ADOPCION:
                return new ControladorAdopcion();
            // case CATEGORIA:
            // return new ControladorCategoria();
            case CITA:
                return new ControladorCita();
            case CLIENTE:
                return new ControladorCliente();
            case CONSEJO:
                return new ControladorConsejo();
            case CUIDADO:
                return new ControladorCuidado();
            case ESPECIE:
                return new ControladorEspecie();
            case ESTADO:
            //    return new ControladorEstado();
            //case ESTADOCATEGORIA:
            //    return new ControladorAdopcion();
            case MASCOTA:
                return new ControladorMascota();
            case PEDIDO:
                return new ControladorPedido();
            case PEDIDODETALLE:
                return new ControladorPedidoDetalle();
            case PRODUCTO:
                return new ControladorProducto();
            case RAZA:
                return new ControladorRaza();
            case SERVICIO:
                return new ControladorServicio();
            case USUARIO:
                return new ControladorUsuario();
            default:
                throw new NoSuchFieldException("Tipo de operacion no soportado: " + tipoClase);
        }
    }
}
