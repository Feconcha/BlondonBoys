package Modelo;

public class Producto {
    private long codigo;
    private String marca;
    private String descripcion;
    private int precio;

    public Producto(long codigo, String marca, String descripcion, int precio) {
        this.codigo = codigo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
}
