package Modelo;

import java.util.ArrayList;

public class DetalleVenta {
    private int cantidad;
    private Venta venta;
    private Producto producto;

    public DetalleVenta (int cantidad, Venta venta, Producto producto){
        this.cantidad = cantidad;
        this.venta = venta;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public String toString(){
        return producto.getCodigo()+";"+cantidad;
    }
}
