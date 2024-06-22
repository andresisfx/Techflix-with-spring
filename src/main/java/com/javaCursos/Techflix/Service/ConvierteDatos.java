package com.javaCursos.Techflix.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    public ObjectMapper objectMaper = new ObjectMapper();

    @Override
    public <T> T obtenDatos(String json, Class<T> clase) {
        try {
            return objectMaper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}