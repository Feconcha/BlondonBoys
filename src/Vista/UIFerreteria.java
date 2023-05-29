package Vista;

import Controlador.ControladorFerreteria;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;

import java.util.ArrayList;
import java.util.Scanner;

public class UIFerreteria {

    private static UIFerreteria instance = null;
    private final Scanner scan;

    private UIFerreteria(){
        scan = new Scanner(System.in);
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

        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scan.next();

        System.out.println("Ingrese la dirección del cliente:");
        String direccion = scan.next();

        System.out.println("Ingrese el número de teléfono del cliente:");
        String telefono = scan.next();
        ControladorFerreteria.getInstance().creaCliente(rut,nombre,direccion,telefono);
        System.out.println("Cliente creado exitosamente.");
    }


    public void CrearProducto() {
        System.out.println("Ingrese el codigo del producto");
        long codigo = scan.nextLong();
        System.out.println("Ingrese la marca del producto");
        String marca = scan.next();
        System.out.println("Ingrese el descripción del producto:");
        String descripcion = scan.next();
        System.out.println("Ingrese el precio del producto:");
        int precio = scan.nextInt();
        ControladorFerreteria.getInstance().creaProducto(codigo,marca,descripcion,precio);
        System.out.println("Producto creado exitosamente.");
    }
    public void CrearVenta(){//cambiar, primero se pide rut, fecha, codventa y codproductos (y su cantidad de productos)
        System.out.println("Ingrese el codigo de la venta");
        String codVenta = scan.next();
        System.out.println("Ingrese la fecha de la venta");
        String fechaVenta = scan.next();
        System.out.println("Ingrese el rut del cliente");
        String rutCliente =scan.next();
        System.out.println("Ingrese el codigo del producto");
        long codProducto = scan.nextLong();
        ControladorFerreteria.getInstance().agregaVenta(codVenta,fechaVenta,codProducto,rutCliente);
        System.out.println("Venta creada exitosamente.");
    }
    //LISTAS
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
        System.out.printf("%1$-13s%2$-25s%3$-30s%4$-40s%n", "Codigo", "Marca","Descripcion","Precio");
        for(int i=0; i<listaProductos.length;i++){
            datos = listaProductos[i].split(";");
            System.out.printf("%1$-13s%2$-25s%3$-30s%4$-40s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }
    public void ListaVentas(){
        String [] listaVentas = ControladorFerreteria.getInstance().listaVentas();
        String [] datos;
        System.out.println("LISTADO DE VENTAS");
        System.out.printf("%1$-16s%2$-30s%3$-30s%4$-40s%n", "Codigo de venta", "Rut Cliente","Código producto","Fecha");

        for(int i=0; i< listaVentas.length;i++){
            datos = listaVentas[i].split(";");
            System.out.printf("%1$-16s%2$-30s%3$-30s%4$-40s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }
    public void menu() {
        int op;
        while (true){
            System.out.println("");
            System.out.println("***** SISTEMA DE FERRETERIA ***** ");
            System.out.println("\n*** MENÚ PRINCIPAL ***");
            System.out.println("1.- Crear nuevo cliente");
            System.out.println("2.- Crear nuevo producto");
            System.out.println("3.- Crear Venta");
            System.out.println("4.- Listar a todos los clientes");
            System.out.println("5.- Listar todos los productos");
            System.out.println("6.- Listar todas las ventas");
            System.out.println("7.- Salir");
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
                    CrearVenta();
                    break;
                case 4:
                    ListaClientes();
                    break;
                case 5:
                    ListaProductos();
                    break;
                case 6:
                    ListaVentas();
                case 7:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Los datos ingresados son incorrectos");

            }
        }
    }
}
