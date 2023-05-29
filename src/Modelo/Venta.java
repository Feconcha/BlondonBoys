package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Venta {
    private long codigoVenta;
    private LocalDate fechaVenta;
    private Cliente cliente;
    private ArrayList<Producto>productos;

    public Venta(long codigoVenta, LocalDate fechaVenta, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        productos = new ArrayList<>();
    }

    public long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void addProductos(Producto producto){
        productos.add(producto);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getVentas(){
        return productos.size();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return codigoVenta + ";" + cliente.getRut() + ";" + fechaVenta.format(formato);
    }
}
