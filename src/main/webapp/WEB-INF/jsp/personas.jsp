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
        <p class="subtitulo">El DNI tiene 7 u 8 números y no se puede repetir entre residentes y enfermeros.</p>

        <%@include file="avisos.jspf"%>

        <%-- el mismo formulario sirve para el alta y para modificar: depende de si hay alguien en "editando" --%>
        <section class="tarjeta ${empty editando.id ? '' : 'destacada'}">
            <c:choose>
                <c:when test="${empty editando.id}"><h2>Nuevo</h2></c:when>
                <c:otherwise><h2>Modificar a <c:out value="${editando.nombre}"/></h2></c:otherwise>
            </c:choose>
            <form method="post" action="${ruta}">
                <input type="hidden" name="accion" value="${empty editando.id ? 'alta' : 'modificar'}">
                <input type="hidden" name="id" value="${editando.id}">
                <div class="campos">
                    <label class="campo">DNI
                        <input name="dni" value="<c:out value='${editando.dni}'/>" placeholder="30111222" required>
                    </label>
                    <label class="campo">Nombre
                        <input name="nombre" value="<c:out value='${editando.nombre}'/>" required>
                    </label>
                    <label class="campo">Apellido
                        <input name="apellido" value="<c:out value='${editando.apellido}'/>" required>
                    </label>
                    <button>${empty editando.id ? 'Dar de alta' : 'Guardar cambios'}</button>
                    <c:if test="${not empty editando.id}"><a class="boton secundario" href="${ruta}">Cancelar</a></c:if>
                </div>
            </form>
        </section>

        <section class="tarjeta">
            <h2>Listado</h2>
            <table>
                <thead>
                    <tr><th>Id</th><th>DNI</th><th>Nombre</th><th>Apellido</th><th></th></tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${personas}">
                        <tr class="${p.id == editando.id ? 'seleccionada' : ''}">
                            <td class="dato-suave">${p.id}</td>
                            <td><c:out value="${p.dni}"/></td>
                            <td><c:out value="${p.nombre}"/></td>
                            <td><c:out value="${p.apellido}"/></td>
                            <td class="acciones">
                                <a class="boton secundario chico" href="${ruta}?editar=${p.id}">Modificar</a>
                                <form method="post" action="${ruta}" class="en-linea"
                                      onsubmit="return confirm('¿Seguro que querés darlo de baja?')">
                                    <input type="hidden" name="accion" value="baja">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <button class="peligro chico">Baja</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty personas}">
                        <tr><td colspan="5" class="vacio">Todavía no hay nadie cargado.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>
    </main>
</body>
</html>
