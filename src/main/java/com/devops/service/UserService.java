package com.devops.service;

import com.devops.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final List<User> usuarios = new ArrayList<>();

    public UserService() {
        // Datos de ejemplo
        usuarios.add(new User(1, "Ana García",   "ana@devops.com",   "ADMIN"));
        usuarios.add(new User(2, "Luis Pérez",   "luis@devops.com",  "DEV"));
        usuarios.add(new User(3, "María López",  "maria@devops.com", "DEV"));
        usuarios.add(new User(4, "Carlos Ruiz",  "carlos@devops.com","QA"));
        log.info("UserService inicializado con {} usuarios", usuarios.size());
    }

    /** Retorna todos los usuarios */
    public List<User> obtenerTodos() {
        log.debug("Obteniendo todos los usuarios");
        return new ArrayList<>(usuarios);
    }

    /** Busca usuarios por rol usando commons-lang3 */
    public List<User> buscarPorRol(String rol) {
        // commons-lang3: validación y normalización
        Validate.notBlank(rol, "El rol no puede estar vacío");
        String rolNormalizado = StringUtils.upperCase(StringUtils.trimToEmpty(rol));
        log.info("Buscando usuarios con rol: {}", rolNormalizado);

        List<User> resultado = usuarios.stream()
                .filter(u -> StringUtils.equals(u.getRol(), rolNormalizado))
                .collect(Collectors.toList());

        // commons-collections4: verificar si la colección tiene elementos
        if (CollectionUtils.isEmpty(resultado)) {
            log.warn("No se encontraron usuarios con rol: {}", rolNormalizado);
        }

        return resultado;
    }

    /** Retorna un resumen de roles disponibles usando commons-lang3 */
    public String resumenRoles() {
        List<String> roles = usuarios.stream()
                .map(User::getRol)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // commons-lang3: join de lista
        return StringUtils.join(roles, " | ");
    }

    /** Valida formato básico de email usando commons-lang3 */
    public boolean esEmailValido(String email) {
        return StringUtils.isNotBlank(email) && StringUtils.contains(email, "@");
    }
}
