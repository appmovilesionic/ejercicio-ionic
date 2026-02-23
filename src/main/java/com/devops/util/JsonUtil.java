package com.devops.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtil() {}

    /** Convierte cualquier objeto a JSON usando Gson */
    public static String toJson(Object objeto) {
        log.debug("Convirtiendo objeto a JSON: {}", objeto.getClass().getSimpleName());
        return gson.toJson(objeto);
    }

    /** Convierte una lista a JSON */
    public static <T> String listaToJson(List<T> lista) {
        log.debug("Convirtiendo lista de {} elementos a JSON", lista.size());
        return gson.toJson(lista);
    }
}
