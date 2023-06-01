package Controlador;

import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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
    public void creaProducto(long codigo, String marca, String descripcion, int precio, int stock){
        Productos.add(new Producto(codigo,marca,descripcion,precio,stock));
    }
    public Venta creaVenta(String rut){
        LocalDate fechaHoy = LocalDate.now();
        Cliente cliente = buscaCliente(rut);
        long codigo = Ventas.size();

        Venta venta = new Venta(codigo,fechaHoy,cliente);
        Ventas.add(venta);
        return venta;
    }
    //LISTAS
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
            listaProductos[i] = producto.getCodigo() + ";" + producto.getMarca() + ";" + producto.getDescripcion() + ";" + producto.getPrecio() + ";" + producto.getStock();
            i++;
        }
        return listaProductos;
    }
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
    public boolean añadirVenta(Long codigo, Venta venta){
        Producto producto = buscaProducto(codigo);
        if(producto.getStock()>0){
            venta.addProductos(producto);
            producto.setStock(producto.getStock()-1);
            return true;
        }
        return false;
    }
    //BUSQUEDAS
    public Cliente buscaCliente(String rut){
        for(Cliente cliente: Clientes){
            if(cliente.getRut().equals(rut)){
                return cliente;
            }
        }
        return null;
    }
    public Producto buscaProducto(long codigo){
        for(Producto producto: Productos){
            if(producto.getCodigo()==codigo){
                return producto;
            }
        }
        return null;
    }
    public void saveClientes() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Clientes.txt"));
        for (Cliente cliente : Clientes){
            pop.println(cliente);

        }
        pop.close();
    }
    public void readClientes() throws FileNotFoundException {
        Scanner sc= new Scanner(new File("Clientes.txt"));
        String rut,nombre,direccion,telefono;
        Cliente cliente;
        sc.useDelimiter("\r\n|;");
        sc.useLocale(Locale.UK);
        while(sc.hasNext()){
            rut= sc.next();
            nombre = sc.next();
            direccion = sc.next();
            telefono= sc.next();
            cliente= new Cliente(rut,nombre,direccion,telefono);
            Clientes.add(cliente);
        }
        sc.close();
    }
    public void saveProductos() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Productos.txt"));
        for (Producto producto : Productos){
            pop.println(producto);

        }
        pop.close();
    }
    public void readProductos() throws FileNotFoundException{
        Scanner sc= new Scanner(new File("Productos.txt"));
        long codigo;
        String marca,descripcion;
        int precio,stock ;
        Producto producto;
        sc.useDelimiter("\r\n|;");
        sc.useLocale(Locale.UK);
        while(sc.hasNext()){
            codigo= sc.nextLong();
            marca = sc.next();
            descripcion = sc.next();
            precio= sc.nextInt();
            stock= sc.nextInt();
            producto= new Producto(codigo,marca,descripcion,precio,stock);
            Productos.add(producto);
        }
        sc.close();
    }

}
