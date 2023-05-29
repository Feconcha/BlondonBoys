package Controlador;

import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorFerreteria {
    private static ControladorFerreteria instance = null;
    private final ArrayList<Cliente> Clientes;
    private final ArrayList<Producto> Productos;
    private final ArrayList<Venta> Ventas;

    private ControladorFerreteria(){
        Clientes = new ArrayList<>();
        Productos = new ArrayList<>();
        Ventas = new ArrayList<>();
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
    public Venta creaVenta(String rut){
        LocalDate fechaHoy = LocalDate.now();
        Cliente cliente = buscaCliente(rut);
        long codigo = Ventas.size();

        Venta venta = new Venta(codigo,fechaHoy,cliente);
        Ventas.add(venta);
        return venta;
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
    //PRODUC
    public String[] listaVentas() {
        String[] listaVentas = new String[Ventas.size()];
        int i=0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Venta venta: Ventas){
            listaVentas[i] = venta.getCliente().getRut() + ";" + venta.getCodigoVenta() + ";" + venta.getFechaVenta().format(formato) + ";" + venta.getVentas();
            i++;
        }
        return listaVentas;
    }
    private String [] consultaRutCliente(String rutCliente){
        Cliente clientito = buscaCliente(rutCliente);
        String[] datosCliente = new String[4];
        if (clientito == null) {
            return datosCliente;
        }
        datosCliente[0] = clientito.getRut();
        datosCliente[1] = clientito.getNombre();
        datosCliente[2] = clientito.getDireccion();
        datosCliente[3] = clientito.getTelefono();
        return datosCliente;
    }
    private String[] consultaCodProducto(long codProducto){
        Producto productos = buscaProducto(codProducto);
        String [] datosProductos = new String[4];
        if(productos == null){
            return datosProductos;
        }
        datosProductos[0] = String.valueOf(productos.getCodigo());
        datosProductos[1] = productos.getMarca();
        datosProductos[2]= productos.getDescripcion();
        datosProductos[3]= String.valueOf(productos.getPrecio());
        return datosProductos;
    }
    public boolean añadirVenta(Long codigo, Venta venta){
        Producto producto = buscaProducto(codigo);
        if(producto.getStock()>0){
            venta.addProductos(producto);
            producto.setStock(producto.getStock()-1);
            return true;
        }
        return false;

    }
    public Cliente buscaCliente(String rut) {
        for (Cliente cliente : Clientes) {
            if (cliente.getRut().equals(rut)) {
                return cliente;
            }
        }
        return null;
    }
    public Producto buscaProducto(long codigo){
        for(Producto producto : Productos){
            if(producto.getCodigo()==codigo){
                return producto;
            }
        }
        return null;
    }


}
