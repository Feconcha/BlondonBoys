package Modelo;

import javax.lang.model.type.DeclaredType;
import java.util.ArrayList;

public class Venta {
    private long codigoVenta;
    private String fechaVenta;
    private Cliente cliente;
    private ArrayList<DetalleVenta> DetalleVenta;

    public Venta(long codigoVenta, String fechaVenta, Cliente cliente, ArrayList<DetalleVenta> detalleVenta) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        DetalleVenta = detalleVenta;
    }

    public void dibujaFactura(){
        System.out.println("Codigo de factira: "+codigoVenta);
        System.out.println("Nombre del cliente: "+cliente.toString());
        System.out.println("Fecha de venta: "+fechaVenta);
        System.out.println("El detalle de lo vendido es: ");
        int acumulado=0;

        for (DetalleVenta detalle : DetalleVenta){
            Producto aux=detalle.getProducto();
            int totalLinea = aux.getPrecio()*detalle.getCantidad();
            System.out.println("Cantidad: "+detalle.getCantidad()+" "+aux.getDescripcion()+"total: "+totalLinea);
            acumulado=acumulado+totalLinea;
        }
        System.out.println("Total NETO: "+acumulado);
        double iva=acumulado*0.19;
        System.out.println("IVA: "+iva);
        System.out.println("Valor total: "+(acumulado+iva));
    }
}