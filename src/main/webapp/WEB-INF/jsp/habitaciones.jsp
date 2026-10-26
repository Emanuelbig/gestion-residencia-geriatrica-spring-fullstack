<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Habitaciones - Residencia Geriátrica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
    <%@include file="encabezado.jspf"%>
    <main>
        <h1>Habitaciones</h1>
        <p class="subtitulo">El código se genera solo. No se puede eliminar una habitación ocupada o con enfermero.</p>

        <%@include file="avisos.jspf"%>

        <c:if test="${not empty detalle}">
            <section class="tarjeta destacada">
                <div class="tarjeta-titulo">
                    <h2>Detalle de ${detalle.codigo}</h2>
                    <a class="boton secundario chico" href="${ruta}">Cerrar</a>
                </div>
                <p><b>Residente:</b>
                    <c:choose>
                        <c:when test="${not empty detalle.residente}"><c:out value="${detalle.residente.nombreCompleto}"/> <span class="dato-suave">(id ${detalle.residente.id})</span></c:when>
                        <c:otherwise>libre</c:otherwise>
                    </c:choose>
                </p>
                <p><b>Enfermero:</b>
                    <c:choose>
                        <c:when test="${not empty detalle.enfermero}"><c:out value="${detalle.enfermero.nombreCompleto}"/> <span class="dato-suave">(id ${detalle.enfermero.id})</span></c:when>
                        <c:otherwise>sin enfermero asignado</c:otherwise>
                    </c:choose>
                </p>
            </section>
        </c:if>

        <section class="tarjeta">
            <div class="tarjeta-titulo">
                <h2>Listado</h2>
                <form method="post" action="${ruta}">
                    <input type="hidden" name="accion" value="alta">
                    <button>+ Nueva habitación</button>
                </form>
            </div>
            <table>
                <thead>
                    <tr><th>Código</th><th>Estado</th><th></th></tr>
                </thead>
                <tbody>
                    <c:forEach var="h" items="${habitaciones}">
                        <tr class="${h.codigo == detalle.codigo ? 'seleccionada' : ''}">
                            <td><b>${h.codigo}</b></td>
                            <td><span class="estado ${h.ocupada ? 'ocupada' : 'libre'}">${h.ocupada ? 'Ocupada' : 'Libre'}</span></td>
                            <td class="acciones">
                                <a class="boton secundario chico" href="${ruta}?ver=${h.codigo}">Ver detalle</a>
                                <form method="post" action="${ruta}" class="en-linea"
                                      onsubmit="return confirm('¿Seguro que querés eliminar ${h.codigo}?')">
                                    <input type="hidden" name="accion" value="baja">
                                    <input type="hidden" name="codigo" value="${h.codigo}">
                                    <button class="peligro chico">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty habitaciones}">
                        <tr><td colspan="3" class="vacio">Todavía no hay habitaciones.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>
