package Controlador;

import Modelo.Cliente;
import Modelo.DetalleVenta;
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
            instance =new ControladorFerreteria();
        }
        return instance;
    }
    public void creaCliente(String rut, String nombre, String direccion, String telefono) throws Exception {
        Cliente cliente = buscaCliente(rut);
        if(!clientes.contains(cliente)){
            clientes.add(new Cliente(rut,nombre,direccion,telefono));
        }else{
            throw new Exception("Ya existe un cliente con el rut dado");
        }
    }
    public void creaProducto(long codigo, String marca, String descripcion, int precio, int stock) throws Exception {
        Producto producto = buscaProducto(codigo);
        if(!productos.contains(producto)){
            productos.add(new Producto(codigo,marca,descripcion,precio,stock));
        }else{
            throw new Exception("Ya existe un producto con el codigo ingresado");
        }
    }
    public Venta creaVenta(long codProducto, String rut, int cantidad, LocalDate fecha) throws Exception {
        Cliente cliente = buscaCliente(rut);
        long codigo = ventas.size();

        if(cliente==null){
            throw new Exception("No existe un cliente con el rut indicado");
        }else{
            Venta venta = new Venta(codigo, fecha, cliente);
            Producto producto = buscaProducto(codProducto);
            if(producto.getStock()>=cantidad){
                ventas.add(venta);
                venta.addProducto(producto, cantidad);
                producto.setStock(producto.getStock()-cantidad);
                return venta;
            }else{
                throw new Exception("No hay suficiente stock del producto");
            }
        }
    }

    //LISTAS
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
            listaProductos[i] = producto.getCodigo() + ";" + producto.getMarca() + ";" + producto.getDescripcion() + ";" + producto.getPrecio() + ";" + producto.getStock();
            i++;
        }
        return listaProductos;
    }
    public String[] listaVentas() {
        String[] listaVentas = new String[ventas.size()];
        int i=0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Venta venta: ventas){
            listaVentas[i] = venta.getCliente().getRut() + ";" + venta.getCodigoVenta() + ";" + venta.getFechaVenta().format(formato) + ";" + venta.getCantidadProductos();
            i++;
        }
        return listaVentas;
    }

    public String[] returnDatos(Venta venta){
        return venta.detalleVenta();
    }

    public int getTotal(Venta venta){
        return venta.getMontoTotal();
    }

    public int getCantidadProductos(Venta venta){
        return venta.getCantidadProductos();
    }

    public boolean añadirAVenta(long codigo, Venta venta, int cantidad){
        Producto producto = buscaProducto(codigo);
        if(producto!=null){
            if(producto.getStock()>=cantidad){
                venta.addProducto(producto, cantidad);
                producto.setStock(producto.getStock()-cantidad);
                return true;
            }
        }
        return false;
    }
    //BUSQUEDAS
    public Cliente buscaCliente(String rut){
        for(Cliente cliente: clientes){
            if(cliente.getRut().equals(rut)){
                return cliente;
            }
        }
        return null;
    }
    public Producto buscaProducto(long codigo){
        for(Producto producto: productos){
            if(producto.getCodigo()==codigo){
                return producto;
            }
        }
        return null;
    }

    public void saveVentas() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Ventas.txt"));
        ArrayList<DetalleVenta> detalleVentas;
        for (Venta venta: ventas){
            pop.println(venta);
            detalleVentas = venta.getDetalleVentas();
            if(detalleVentas.size()>0){
                for(DetalleVenta detalleVenta: detalleVentas){
                    pop.println(detalleVenta);
                }
                pop.println("*");
            }
        }
        pop.close();
    }

    public void readVentas() throws FileNotFoundException {
        ventas.clear();
        Scanner sc= new Scanner(new File("Ventas.txt"));
        String codigoVenta, fechaVenta, rutCliente, codigoProducto;
        int cantidad;
        Cliente cliente; Producto producto;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        sc.useDelimiter("\r\n|;");
        sc.useLocale(Locale.UK);
        while(sc.hasNext()){
            codigoVenta = sc.next();
            rutCliente = sc.next();
            fechaVenta = sc.next();
            cliente = buscaCliente(rutCliente);
            Venta venta = new Venta(Long.parseLong(codigoVenta),LocalDate.parse(fechaVenta,formato),cliente);
            codigoProducto = sc.next();
            while(!codigoProducto.equals("*")){
                cantidad = sc.nextInt();
                producto = buscaProducto(Long.parseLong(codigoProducto));
                venta.addProducto(producto,cantidad);
                codigoProducto = sc.next();
            }
            ventas.add(venta);
        }
        sc.close();
    }
    public void saveClientes() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Clientes.txt"));
        for (Cliente cliente : clientes){
            pop.println(cliente);

        }
        pop.close();
    }
    public void readClientes() throws FileNotFoundException {
        clientes.clear();

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
            clientes.add(cliente);
        }
        sc.close();
    }
    public void saveProductos() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Productos.txt"));
        for (Producto producto : productos){
            pop.println(producto);

        }
        pop.close();
    }
    public void readProductos() throws FileNotFoundException{
        productos.clear();

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
            productos.add(producto);
        }
        sc.close();
    }

}
