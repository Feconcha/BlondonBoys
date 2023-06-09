package Modelo;

import java.util.ArrayList;

public class DetalleVenta {
    private int cantidad;
    private ArrayList<Venta> ventas;
    private Producto producto;

    public DetalleVenta(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        ventas = new ArrayList<>();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
