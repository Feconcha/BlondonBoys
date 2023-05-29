package Controlador;

import Modelo.Cliente;
import Modelo.Producto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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
    public void saveClientes() throws FileNotFoundException{
        PrintStream pop= new PrintStream(new File("Clientes.txt"));
        for (Cliente cliente : Clientes){
            pop.println(cliente);

        }
        pop.close();
    }
    public void readClientes() throws FileNotFoundException{
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
