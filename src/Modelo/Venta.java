package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Venta {
    private long codigoVenta;
    private LocalDate fechaVenta;
    private Cliente cliente;
    private ArrayList<DetalleVenta>detalleVentas;

    public Venta(long codigoVenta, LocalDate fechaVenta, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        detalleVentas = new ArrayList<>();
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

    public void addProducto(Producto producto, int cantidad){
        DetalleVenta venta = new DetalleVenta(cantidad,this,producto);
        detalleVentas.add(venta);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<DetalleVenta> getDetalleVentas(){
        return detalleVentas;
    }

    public int getMontoTotal(){
        int monto=0;
        for(DetalleVenta detalleVenta : detalleVentas){
            monto+= detalleVenta.getProducto().getPrecio()*detalleVenta.getCantidad();
        }
        return monto;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCantidadProductos(){
        int cont=0;
        for(int i=0; i<detalleVentas.size(); i++){
            cont+=detalleVentas.get(i).getCantidad();
        }
        return cont;
    }

    //
    public Producto[] getProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        for(DetalleVenta detalleVenta: detalleVentas){
            if(detalleVenta.getProducto()!=null){
                productos.add(detalleVenta.getProducto());
            }
        }
        return productos.toArray(new Producto[0]);
    }

    public String[] detalleVenta(){
        String[] listaVenta = new String[detalleVentas.size()];
        for(int i=0; i<listaVenta.length; i++){
            Producto producto = detalleVentas.get(i).getProducto();
            listaVenta[i] = detalleVentas.get(i).getCantidad() + ";" + producto.getDescripcion() + ";" + producto.getPrecio() + ";" + (detalleVentas.get(i).getCantidad()*producto.getPrecio());
        }
        return listaVenta;
    }

    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return codigoVenta + ";" + cliente.getRut() + ";" + fechaVenta.format(formato);
    }
}
