package Controlador;

import Modelo.Cliente;
import Modelo.Producto;

import java.util.ArrayList;

public class ControladorFerreteria {
    private static ControladorFerreteria instance = null;
    private final ArrayList<Cliente> Clientes;
    private final ArrayList<Producto> Productos;

    private ControladorFerreteria(){
        Clientes = new ArrayList<>();
        Productos = new ArrayList<>();
    }


    public static ControladorFerreteria getInstance() {
        if (instance == null) {
            instance =new ControladorFerreteria();
        }
        return instance;
    }
    public void creaCliente(String rut, String nombre, String direccion, String telefono){
        Clientes.add(new Cliente(rut,nombre,direccion,telefono));
    }
    public void creaProducto(long codigo, String marca, String descripcion, int precio){
        Productos.add(new Producto(codigo,marca,descripcion,precio));
    }
    public String[] listaClientes() {
        String [] listaClientes = new String[Clientes.size()];
        int i=0;
        for(Cliente cliente: Clientes){
            listaClientes[i] = cliente.getRut() + ";" + cliente.getNombre() + ";" + cliente.getDireccion() + ";" + cliente.getTelefono();
            i++;
        }
        return listaClientes;
    }
    public String[] listaProductos() {
        String[] listaProductos = new String[Productos.size()];
        int i=0;
        for(Producto producto: Productos){
            listaProductos[i] = producto.getCodigo() + ";" + producto.getMarca() + ";" + producto.getDescripcion() + ";" + producto.getPrecio();
            i++;
        }
        return listaProductos;
    }

}
