package Controlador;

import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;

import java.util.ArrayList;

public class ControladorFerreteria {
    private static ControladorFerreteria instance = null;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Producto> productos;
    private final ArrayList<Venta> ventas;

    private ControladorFerreteria(){
        clientes = new ArrayList<>();
        productos = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public static ControladorFerreteria getInstance() {
        if (instance == null) {
            instance = new ControladorFerreteria();
        }
        return instance;
    }

    public void creaCliente(String rut, String nombre, String direccion, String telefono){
        clientes.add(new Cliente(rut,nombre,direccion,telefono));
    }

    public void creaProducto(long codigo, String marca, String descripcion, int precio){
        productos.add(new Producto(codigo,marca,descripcion,precio));
    }

    public void creaVenta(long codigoVenta, String fechaVenta, Cliente cliente, ArrayList<Producto> productos){
        ventas.add(new Venta(codigoVenta, fechaVenta, cliente, productos));
    }

    public String[] listaClientes() {
        String [] listaClientes = new String[clientes.size()];
        int i=0;
        for(Cliente cliente: clientes){
            listaClientes[i] = cliente.getRut() + ";" + cliente.getNombre() + ";" + cliente.getDireccion() + ";" + cliente.getTelefono();
            i++;
        }
        return listaClientes;
    }

    public String[] listaProductos() {
        String[] listaProductos = new String[productos.size()];
        int i=0;
        for(Producto producto: productos){
            listaProductos[i] = producto.getCodigo() + ";" + producto.getMarca() + ";" + producto.getDescripcion() + ";" + producto.getPrecio();
            i++;
        }
        return listaProductos;
    }

    public String[] listaVentas(){
        String[] listaVentas = new String[ventas.size()];
        int i=0;
        for(Venta venta : ventas){

            i++;
        }
        return listaVentas;
    }
}