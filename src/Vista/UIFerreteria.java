package Vista;

import Controlador.ControladorFerreteria;
import Modelo.Cliente;
import Modelo.Producto;

import java.util.ArrayList;
import java.util.Scanner;

public class UIFerreteria {

    private static UIFerreteria instance = null;
    private final ArrayList<Cliente> Clientes;
    private final ArrayList<Producto> Productos;
    private final Scanner scan;

    private UIFerreteria(){
        scan = new Scanner(System.in);
        Clientes = new ArrayList<>();
        Productos = new ArrayList<>();
        scan.useDelimiter("[\t|\r\n]+");
    }

    public static UIFerreteria getInstance() {
        if (instance == null) {
            instance = new UIFerreteria();
        }
        return instance;
    }

    public void CrearCliente() {

        System.out.println("Ingrese el rut del cliente");
        String rut = scan.next();

        String nombre = validarNombre();

        System.out.println("Ingrese la dirección del cliente:");
        String direccion = scan.next();

        System.out.println("Ingrese el número de teléfono del cliente:");
        String telefono = scan.next();

        Cliente cliente = new Cliente(rut, nombre, direccion, telefono);
        Clientes.add(cliente);

        ControladorFerreteria.getInstance().creaCliente(rut,nombre,direccion,telefono);
        System.out.println("Cliente creado exitosamente.");
    }


    public void CrearProducto() {
        long codigo = validarCodigo();
        System.out.println("Ingrese la marca del producto");
        String marca = scan.next();
        System.out.println("Ingrese el descripción del producto:");
        String descripcion = scan.next();
        int precio = validarPrecio();
        Producto producto = new Producto(codigo, marca, descripcion, precio);
        Productos.add(producto);
        ControladorFerreteria.getInstance().creaProducto(codigo,marca,descripcion,precio);

        System.out.println("Producto creado exitosamente.");
    }

    public void ListaClientes() {
        String [] datos;
        String [] listaClientes = ControladorFerreteria.getInstance().listaClientes();
        System.out.println("**** LISTADO DE CLIENTES **** ");


        System.out.printf("%1$-18s%2$-30s%3$-35s%4$-12s%n", "RUT", "Nombre","Direccion","Telefono");

        for(int i=0; i<listaClientes.length;i++){
            datos = listaClientes[i].split(";");
            System.out.printf("%1$-18s%2$-30s%3$-35s%4$-12s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }

    public void ListaProductos() {
        String [] datos;
        String [] listaProductos = ControladorFerreteria.getInstance().listaProductos();
        System.out.println("**** LISTADO DE PRODUCTOS **** ");
        System.out.printf("%1$-13s%2$-20s%3$-30s%4$-40s%n", "Codigo", "Marca","Descripcion","Precio");
        for(int i=0; i<listaProductos.length;i++){
            datos = listaProductos[i].split(";");
            System.out.printf("%1$-13s%2$-20s%3$-30s%4$-40s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }


    private String validarNombre(){
        do{
            System.out.println("Ingrese el nombre del cliente");
            int nombreInt;
            String nombre;
            nombre = scan.next();
            try{
                nombreInt=Integer.parseInt(nombre);
                System.out.println("El nombre posee caracteres no validos");

            } catch (NumberFormatException e){
                return nombre;
            }
        }while (true);
    }


    private long validarCodigo(){
        do{
            System.out.println("Ingrese el codigo del producto");
            String codigoStr;
            long codigo;
            codigoStr=scan.next();
            try{
                codigo=Long.parseLong(codigoStr);
                return codigo;
            } catch (NumberFormatException e){
                System.out.println("El codigo posee valores no validos");
            }
        }while (true);
    }

    private int validarPrecio(){
        do{
            System.out.println("Ingrese el precio del producto");
            String precioStr;
            int precio;
            precioStr=scan.next();
            try{
                precio= Integer.parseInt(precioStr);
                return precio;
            } catch (NumberFormatException e){
                System.out.println("El precio posee valores no validos");
            }
        }while (true);
    }

    public void menu() {
        int op;
        while (true){
            System.out.println("");
            System.out.println("***** SISTEMA DE FERRETERIA ***** ");
            System.out.println("\n*** MENÚ PRINCIPAL ***");
            System.out.println("1.- Crear nuevo cliente");
            System.out.println("2.- Crear nuevo producto");
            System.out.println("3.- Listar a todos los clientes");
            System.out.println("4.- Listar todos los productos");
            System.out.println("5.- Salir");
            System.out.println("Ingrese opción:");
            op = scan.nextInt();

            switch (op) {
                case 1:
                    CrearCliente();
                    break;
                case 2:
                    CrearProducto();
                    break;
                case 3:
                    ListaClientes();
                    break;
                case 4:
                    ListaProductos();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
            }
        }
    }
}