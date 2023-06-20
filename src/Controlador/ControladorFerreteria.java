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
import java.util.*;

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
    //PRODUC
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
    //LEER Y GUARDAR DATOS
    public void saveVentas() throws FileNotFoundException {
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
    public void saveClientes() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Clientes.txt"));
        for (Cliente cliente : Clientes){
            pop.println(cliente);

        }
        pop.close();
    }
    public void readClientes() throws FileNotFoundException {
        Clientes.clear();

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


}
