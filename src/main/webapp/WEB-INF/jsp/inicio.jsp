<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Residencia Geriátrica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos.css">
</head>
<body>
    <%@include file="encabezado.jspf"%>
    <main>
        <h1>Gestión de Residencia Geriátrica</h1>
        <p class="subtitulo">Elegí qué querés administrar.</p>

        <div class="grilla menu">
            <a class="tarjeta" href="residentes">
                <h2>Residentes</h2>
                <p>Alta, modificación y baja de los residentes.</p>
            </a>
            <a class="tarjeta" href="enfermeros">
                <h2>Enfermeros</h2>
                <p>Alta, modificación y baja del personal de enfermería.</p>
            </a>
            <a class="tarjeta" href="habitaciones">
                <h2>Habitaciones</h2>
                <p>Crear habitaciones, ver si están libres y quién las ocupa.</p>
            </a>
            <a class="tarjeta" href="reservas">
                <h2>Reservas</h2>
                <p>Ubicar a un residente en una habitación o liberarla.</p>
            </a>
            <a class="tarjeta" href="asignaciones">
                <h2>Enfermeros por habitación</h2>
                <p>Asignar qué enfermero atiende cada habitación.</p>
            </a>
            <a class="tarjeta" href="reportes">
                <h2>Consultas</h2>
                <p>Habitación de un residente, residentes de un enfermero y enfermero de un residente.</p>
            </a>
        </div>

        <p class="subtitulo nota">
            La misma aplicación también es una API REST: los mismos datos en JSON en
            <a href="${pageContext.request.contextPath}/api/residentes">/api/residentes</a>,
            <a href="${pageContext.request.contextPath}/api/habitaciones">/api/habitaciones</a>, etc.
        </p>
    </main>
</body>
</html>
