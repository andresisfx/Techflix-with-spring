package com.javaCursos.Techflix.Service;

public interface IConvierteDatos {
    <T> T obtenDatos(String json,Class <T> clase);
}
