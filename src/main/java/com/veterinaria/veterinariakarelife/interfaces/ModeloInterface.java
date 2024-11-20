package com.veterinaria.veterinariakarelife.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface ModeloInterface<T> {
    List<T> ejecutarSP(String accion, T t) throws SQLException;
}
