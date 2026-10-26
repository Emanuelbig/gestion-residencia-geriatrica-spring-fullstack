<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${titulo} - Residencia Geriátrica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
    <%@include file="encabezado.jspf"%>
    <main>
        <h1>${titulo}</h1>
        <p class="subtitulo">Solo se ofrecen las opciones que permiten las reglas, que controlan los servicios.</p>

        <%@include file="avisos.jspf"%>

        <section class="tarjeta">
            <h2>Asignar</h2>
            <c:choose>
                <c:when test="${empty personas or empty habitaciones}">
                    <p class="dato-suave">No hay ${tipoPersona.toLowerCase()}s o habitaciones disponibles para asignar.</p>
                </c:when>
                <c:otherwise>
                    <form method="post" action="${ruta}">
                        <input type="hidden" name="accion" value="asignar">
                        <div class="campos">
                            <label class="campo">${tipoPersona}
                                <select name="idPersona" required>
                                    <c:forEach var="p" items="${personas}">
                                        <option value="${p.id}"><c:out value="${p.nombreCompleto}"/> (id ${p.id})</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <label class="campo">Habitación
                                <select name="codigo" required>
                                    <c:forEach var="codigo" items="${habitaciones}">
                                        <option>${codigo}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            <button>Asignar</button>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </section>

        <section class="tarjeta">
            <h2>Asignadas</h2>
            <table>
                <thead>
                    <tr><th>Habitación</th><th>${tipoPersona}</th><th></th></tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${relaciones}">
                        <tr>
                            <td><b>${r.codigoHabitacion}</b></td>
                            <td><c:out value="${r.persona.nombreCompleto}"/> <span class="dato-suave">(id ${r.persona.id})</span></td>
                            <td class="acciones">
                                <form method="post" action="${ruta}" class="en-linea"
                                      onsubmit="return confirm('¿Quitar de ${r.codigoHabitacion}?')">
                                    <input type="hidden" name="accion" value="quitar">
                                    <input type="hidden" name="codigo" value="${r.codigoHabitacion}">
                                    <button class="peligro chico">Quitar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty relaciones}">
                        <tr><td colspan="3" class="vacio">Todavía no hay ninguna asignada.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>
