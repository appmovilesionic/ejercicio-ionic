package com.devops;

import com.devops.model.User;
import com.devops.service.UserService;
import com.devops.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/usuarios")
public class HolaMundoServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(HolaMundoServlet.class);
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rol = req.getParameter("rol");
        log.info("GET /usuarios - filtro rol: {}", rol);

        List<User> usuarios;

        if (rol != null && !rol.isBlank()) {
            usuarios = userService.buscarPorRol(rol);
        } else {
            usuarios = userService.obtenerTodos();
        }

        String json = JsonUtil.listaToJson(usuarios);
        String resumenRoles = userService.resumenRoles();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><head>");
        out.println("<title>DevOps - Usuarios</title>");
        out.println("<style>body{font-family:sans-serif;padding:20px} pre{background:#f4f4f4;padding:15px;border-radius:5px} .badge{background:#0078d4;color:white;padding:2px 8px;border-radius:10px;margin:2px;display:inline-block}</style>");
        out.println("</head><body>");

        out.println("<h1>Gestion de Usuarios</h1>");
        out.println("<p><strong>Java:</strong> " + System.getProperty("java.version") + "</p>");
        out.println("<p><strong>Roles disponibles:</strong> " + resumenRoles + "</p>");

        out.println("<h3>Filtrar por rol:</h3>");
        out.println("<form method='get'>");
        out.println("  <input name='rol' placeholder='Ej: DEV, ADMIN, QA' value='" + (rol != null ? rol : "") + "'/>");
        out.println("  <button type='submit'>Buscar</button>");
        out.println("  <a href='/usuarios'>Ver todos</a>");
        out.println("</form>");

        out.println("<h3>Resultado (" + usuarios.size() + " usuarios):</h3>");
        out.println("<pre>" + json + "</pre>");

        out.println("<hr/><h3>Dependencias usadas desde Nexus:</h3><ul>");
        out.println("<li><span class='badge'>slf4j + logback</span> Logging</li>");
        out.println("<li><span class='badge'>gson</span> Serializacion JSON</li>");
        out.println("<li><span class='badge'>commons-lang3</span> Validacion y strings</li>");
        out.println("<li><span class='badge'>commons-collections4</span> Manejo de colecciones</li>");
        out.println("</ul>");

        out.println("</body></html>");

        log.info("Respuesta enviada con {} usuarios", usuarios.size());
    }
}
