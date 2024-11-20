package com.veterinaria.veterinariakarelife.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.veterinaria.veterinariakarelife.models.Singleton;

@Component
public class FactoryModelo {
    private final Connection connection;

    public FactoryModelo() throws SQLException{
        this.connection = Singleton.getInstance().getConnection();
    }

    public ClienteModelo getClientesModelo() {
        return new ClienteModelo(connection);
    }
    
    public MascotaModelo getMascotaModelo() {
        return new MascotaModelo(connection);
    }

    public AdopcionModelo getAdopcionModelo() {
        return new AdopcionModelo(connection);
    }

    public ServiciosModelo getServiciosModelo(){
        return new ServiciosModelo(connection);
    }

    public CitaModelo getCitaModelo(){
        return new CitaModelo(connection);
    }

    public ConsejoModelo getConsejoModelo(){
        return new ConsejoModelo(connection);
    }

    public CuidadoModelo getCuidadoModelo(){
        return new CuidadoModelo(connection);
    }

    public EspecieModelo getEspecieModelo(){
        return new EspecieModelo(connection);
    }

    public PedidoModelo getPedidoModelo(){
        return new PedidoModelo(connection);
    }

    public ProductosModelo getProductosModelo(){
        return new ProductosModelo(connection);
    }

    public RazaModelo getRazaModelo(){
        return new RazaModelo(connection);
    }

    public PedidoDetalleModelo getPedidoDetalleModelo(){
        return new PedidoDetalleModelo(connection);
    }

    public UsuarioModelo getUsuarioModelo(){
        return new UsuarioModelo(connection);
    }
}
