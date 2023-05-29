package Vista;

import Controlador.ControladorFerreteria;
import Modelo.Cliente;
import Modelo.Producto;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UIFerreteria {

    private static UIFerreteria instance = null;

    private final Scanner scan;

    private UIFerreteria(){
        scan = new Scanner(System.in);
        scan.useDelimiter("[\t|\r\n]+");

    }
    private ControladorFerreteria controlador = ControladorFerreteria.getInstance();

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

    public void menu() {
        int op;
        while (true){
            System.out.println("");
            System.out.println("***** SISTEMA DE FERRETERIA ***** ");
            System.out.println("\n*** MENÚ PRINCIPAL ***");
            System.out.println("1.- Crear nuevo cliente");
            System.out.println("2.- Crear nuevo producto");
            System.out.println("3.- Crear venta");
            System.out.println("4.- Listar a todos los clientes");
            System.out.println("5.- Listar todos los productos");
            System.out.println("6.- Listar todas las ventas");
            System.out.println("7.- Guardar Datos");
            System.out.println("8.- Leer Datos");
            System.out.println("9.- Salir");
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

                    break;
                case 4:
                    ListaClientes();
                    break;
                case 5:
                    ListaProductos();

                    break;
                case 6:

                    break;
                case 7:
                    try {
                        controlador.saveClientes();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        controlador.saveProductos();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 8:
                    try {
                        controlador.readClientes();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        controlador.readProductos();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;

                case 9:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
            }
        }
    }
}
