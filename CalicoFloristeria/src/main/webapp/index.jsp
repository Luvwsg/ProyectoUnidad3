<%-- 
    Document   : index
    Created on : Oct 26, 2026, 12:18:27 PM
    Author     : rinco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./styles.css" >
    </head>
    <body>
        <h1>Calico Floristería</h1>

        <section>
            <h2>Agregar flor</h2>
            <form action="SvFlor" method="POST">
                <div>
                    <label>Flor</label>
                    <input name="nombre" type="text" required />
                </div>
                <div>
                    <label>Cantidad</label>
                    <input name="cantidad" type="number" required />
                </div>
                <div>
                    <label>Fecha de llegada</label>
                    <input name="fecha" type="date" required />
                </div>
                <div>
                    <label>Precio</label>
                    <input name="precio" type="number" required />
                </div>
                <div>
                    <label>Precio por pieza</label>
                    <input name="precioPieza" type="number" required />
                </div>
                <button type="submit">Guardar</button>
            </form>
        </section>

        <section>
            <h2>Flores en inventario</h2>

            <c:if test="${not empty lista}">
                <table>
                    <tr>
                        <th>Flor</th>
                        <th>Cantidad</th>
                        <th>Fecha de llegada</th>
                        <th>Precio</th>
                        <th>Precio por pieza</th>
                    </tr>
                    <c:forEach var="flor" items="${lista}">
                        <tr>
                            <td>${flor.nombre}</td>
                            <td>${flor.cantidad}</td>
                            <td>${flor.fechaLlegada}</td>
                            <td>$ ${flor.precio}</td>
                            <td>$ ${flor.precioPieza}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${empty lista}">
                <p>Todavía no hay flores registradas.</p>
            </c:if>
        </section>
    </body>
</html>
