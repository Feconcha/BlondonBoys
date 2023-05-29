package Modelo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venta {
    private long codigoVenta;
    private String fechaVenta;
    private Cliente cliente;
    private ArrayList<Producto>productos;

    public Venta(long codigoVenta, String fechaVenta, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        productos= new ArrayList<>();
    }

    public long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    public void addProductos(Producto producto){
        productos.add(producto);
    }
    public int getVentas(){
        return productos.size();
    }
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return codigoVenta + ";" + cliente.getRut() + ";" + fechaVenta.format(formato);
    }

}