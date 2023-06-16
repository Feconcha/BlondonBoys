package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private long codigoVenta;
    private Cliente cliente;
    private ArrayList<DetalleVenta> detalleVentas;
    private LocalDate fechaVenta;


    public Venta(long codigoVenta, LocalDate fechaVenta, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        detalleVentas= new ArrayList<>();
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
    public ArrayList<DetalleVenta>getDetalleVentas(){
        return detalleVentas;
    }
    public void setDetalleVentas(ArrayList<DetalleVenta> detalleVentas){
        this.detalleVentas = detalleVentas;
    }
    public LocalDate getFechaVenta() {
        return fechaVenta;
    }
    public void addDetalleVetnas(DetalleVenta detalleVenta){
        detalleVentas.add(detalleVenta);
    }
    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    public String toString(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return codigoVenta + ";" + cliente.getRut() + ";" + fechaVenta.format(formato);
    }



}