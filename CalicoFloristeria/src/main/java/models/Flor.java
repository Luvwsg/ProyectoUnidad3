package models;

/**
 *
 * @author rinco
 */
public class Flor {
    int id;
    String nombre;
    int cantidad;
    String fechaLlegada;
    int precio;
    int precioPieza;

    public Flor(int id, String nombre, int cantidad, String fechaLlegada, int precio, int precioPieza) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaLlegada = fechaLlegada;
        this.precio = precio;
        this.precioPieza = precioPieza;
    }

    public int getId() { 
        return id; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public int getCantidad() { 
        return cantidad; 
    }
    
    public String getFechaLlegada() { 
        return fechaLlegada; 
    }
    
    public int getPrecio() { 
        return precio; 
    }
    
    public int getPrecioPieza() { 
            return precioPieza; 
    }
}
