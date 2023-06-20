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
    //CREAR
    public void creaCliente(String rut, String nombre, String direccion, String telefono){
        Clientes.add(new Cliente(rut,nombre,direccion,telefono));
    }
    public void creaProducto(long codigo, String marca, String descripcion, int precio, int stock){
        Productos.add(new Producto(codigo,marca,descripcion,precio,stock));
    }
    public Venta creaVenta(long codProducto, String rut, int cantidad, LocalDate fecha) throws Exception {
        Cliente cliente = buscaCliente(rut);
        long codigo = Ventas.size();

        if(cliente==null){
            throw new Exception("No existe un cliente con el rut indicado");
        }else{
            Venta venta = new Venta(codigo, fecha, cliente);
            Producto producto = buscaProducto(codProducto);
            if(producto.getStock()>=cantidad){
                Ventas.add(venta);
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
            listaVentas[i] = venta.getCliente().getRut() + ";" + venta.getCodigoVenta() + ";" + venta.getFechaVenta().format(formato) + ";" + venta.getCantidadProductos();
            i++;
        }
        return listaVentas;
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
    public String[] returnDatos(Venta venta){
        return venta.detalleVenta();
    }

    public int getTotal(Venta venta){
        return venta.getMontoTotal();
    }

    public int getCantidadProductos(Venta venta){
        return venta.getCantidadProductos();
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
    //GUARDAR Y LEER DATOS
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
    public void saveVentas() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Ventas.txt"));
        ArrayList<DetalleVenta> detalleVentas;
        for (Venta venta: Ventas){
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
        Ventas.clear();
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
            Ventas.add(venta);
        }
        sc.close();
    }

}
