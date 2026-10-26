package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import models.Flor;

@WebServlet(name = "SvFlor", urlPatterns = {"/SvFlor"})
public class SvFlor extends HttpServlet {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=BDFlores;encrypt=true;trustServerCertificate=true";
    private final String user = "Sarai";
    private final String password = "1234";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver JDBC no encontrado", e);
        }

        List<Flor> listaFlores = new ArrayList<>();
        String sql = "SELECT ID, Flor, Cantidad, FechaLlegada, Precio, PrecioPieza "
                   + "FROM Flores ORDER BY FechaLlegada DESC";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaFlores.add(new Flor(
                        rs.getInt("ID"),
                        rs.getString("Flor"),
                        rs.getInt("Cantidad"),
                        rs.getString("FechaLlegada"),
                        rs.getInt("Precio"),
                        rs.getInt("PrecioPieza")));
            }

            request.setAttribute("lista", listaFlores);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver JDBC no encontrado", e);
        }

        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");
        int cantidad;
        int precio;
        int precioPieza;
        LocalDate fechaLlegada;

        try {
            cantidad = Integer.parseInt(request.getParameter("cantidad"));
            precio = Integer.parseInt(request.getParameter("precio"));
            precioPieza = Integer.parseInt(request.getParameter("precioPieza"));
            fechaLlegada = LocalDate.parse(request.getParameter("fecha"));
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new ServletException("Cantidad, precios o fecha con formato invalido", e);
        }

        // Validación: no se aceptan números negativos
        if (cantidad < 0 || precio < 0 || precioPieza < 0) {
            request.setAttribute("error", "La cantidad y los precios no pueden ser negativos");
            doGet(request, response);
            return;
        }

        String sql = "INSERT INTO Flores(Flor, Cantidad, FechaLlegada, Precio, PrecioPieza) "
                   + "VALUES(?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setDate(3, java.sql.Date.valueOf(fechaLlegada));
            ps.setInt(4, precio);
            ps.setInt(5, precioPieza);
            ps.executeUpdate();

            response.sendRedirect("SvFlor");

        } catch (SQLException e) {
            throw new ServletException("Error al guardar en la base de datos", e);
        }
    }
}