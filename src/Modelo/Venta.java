package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private long codigoVenta;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private LocalDate fechaVenta;


    public Venta(long codigoVenta, LocalDate fechaVenta, Cliente cliente) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }



}