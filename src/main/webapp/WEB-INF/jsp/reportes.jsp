<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Consultas - Residencia Geriátrica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
    <%@include file="encabezado.jspf"%>
    <main>
        <h1>Consultas</h1>
        <p class="subtitulo">Las tres consultas de la consigna. Solo leen datos, por eso los formularios usan GET.</p>

        <%@include file="avisos.jspf"%>

        <div class="grilla">
            <%-- 1. habitación de un residente --%>
            <section class="tarjeta">
                <h2>¿En qué habitación está un residente?</h2>
                <form method="get">
                    <input type="hidden" name="consulta" value="habitacion">
                    <div class="campos">
                        <select name="id" required>
                            <c:forEach var="r" items="${residentes}">
                                <option value="${r.id}"><c:out value="${r.nombreCompleto}"/> (id ${r.id})</option>
                            </c:forEach>
                        </select>
                        <button>Consultar</button>
                    </div>
                </form>
                <c:if test="${not empty habitacionDeResidente}">
                    <div class="resultado">
                        <b><c:out value="${habitacionDeResidente.persona.nombreCompleto}"/></b>
                        <c:choose>
                            <c:when test="${not empty habitacionDeResidente.codigoHabitacion}">está en la habitación <b>${habitacionDeResidente.codigoHabitacion}</b>.</c:when>
                            <c:otherwise>no tiene habitación asignada.</c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </section>

            <%-- 2. residentes que atiende un enfermero --%>
            <section class="tarjeta">
                <h2>¿Qué residentes atiende un enfermero?</h2>
                <form method="get">
                    <input type="hidden" name="consulta" value="residentes">
                    <div class="campos">
                        <select name="id" required>
                            <c:forEach var="e" items="${enfermeros}">
                                <option value="${e.id}"><c:out value="${e.nombreCompleto}"/> (id ${e.id})</option>
                            </c:forEach>
                        </select>
                        <button>Consultar</button>
                    </div>
                </form>
                <c:if test="${not empty enfermero}">
                    <div class="resultado">
                        <b><c:out value="${enfermero.nombreCompleto}"/></b> atiende a
                        <c:choose>
                            <c:when test="${empty residentesDeEnfermero}">ningún residente.</c:when>
                            <c:otherwise>
                                <ul>
                                    <c:forEach var="r" items="${residentesDeEnfermero}">
                                        <li><c:out value="${r.nombreCompleto}"/> <span class="dato-suave">(id ${r.id})</span></li>
                                    </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </section>

            <%-- 3. enfermero de un residente --%>
            <section class="tarjeta">
                <h2>¿Qué enfermero atiende a un residente?</h2>
                <form method="get">
                    <input type="hidden" name="consulta" value="enfermero">
                    <div class="campos">
                        <select name="id" required>
                            <c:forEach var="r" items="${residentes}">
                                <option value="${r.id}"><c:out value="${r.nombreCompleto}"/> (id ${r.id})</option>
                            </c:forEach>
                        </select>
                        <button>Consultar</button>
                    </div>
                </form>
                <c:if test="${not empty residente}">
                    <div class="resultado">
                        <b><c:out value="${residente.nombreCompleto}"/></b>
                        <c:choose>
                            <c:when test="${not empty enfermeroDeResidente}">es atendido/a por <b><c:out value="${enfermeroDeResidente.nombreCompleto}"/></b>.</c:when>
                            <c:otherwise>no tiene enfermero asignado (o no tiene habitación).</c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </section>
        </div>
    </main>
</body>
</html>
