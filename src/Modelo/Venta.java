package Modelo;

public class Venta {
    private long codigoVenta;
    private String fechaVenta;

    public Venta(long codigoVenta, String fechaVenta) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
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
}