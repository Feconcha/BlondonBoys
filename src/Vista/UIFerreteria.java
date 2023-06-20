package Vista;

import Controlador.ControladorFerreteria;
import Modelo.Cliente;
import Modelo.Producto;
import Modelo.Venta;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UIFerreteria {

    private static UIFerreteria instance = null;
    private final Scanner scan;
    private ControladorFerreteria controlador = ControladorFerreteria.getInstance();

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
        controlador.creaCliente(rut,nombre,direccion,telefono);
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
        System.out.println("Ingrese el stock del producto");
        int stock = scan.nextInt();
        controlador.creaProducto(codigo,marca,descripcion,precio,stock);
        System.out.println("Producto creado exitosamente.");
    }
    public void CrearVenta(){
        String opcion;
        long codigo;
        System.out.println("Ingrese el rut del cliente");
        String rut = scan.next();
        Venta venta =controlador.creaVenta(rut);
        do{
            System.out.println("Ingrese el código del producto");
            codigo = scan.nextLong();
            if(controlador.añadirVenta(codigo,venta)){
                System.out.println("¿Quiere comprar mas productos? s/n");
                opcion = scan.next();
            }else{
                System.out.println("No hay stock suficiente para ese producto");
                opcion= "n";
            }
        }while (opcion.equalsIgnoreCase("n"));
    }
    //LISTAS
    public void ListaClientes() {
        String [] datos;
        String [] listaClientes = controlador.listaClientes();
        System.out.println("**** LISTADO DE CLIENTES **** ");
        
        
        System.out.printf("%1$-18s%2$-30s%3$-35s%4$-12s%n", "RUT", "Nombre","Direccion","Telefono");

        for(int i=0; i<listaClientes.length;i++){
            datos = listaClientes[i].split(";");
            System.out.printf("%1$-18s%2$-30s%3$-35s%4$-12s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }

    public void ListaProductos() {
        String [] datos;
        String [] listaProductos = controlador.listaProductos();
        System.out.println("**** LISTADO DE PRODUCTOS **** ");
        System.out.printf("%1$-13s%2$-25s%3$-30s%4$-30s%5$-40s%n", "Codigo", "Marca","Descripcion","Precio","Stock");
        for(int i=0; i<listaProductos.length;i++){
            datos = listaProductos[i].split(";");
            System.out.printf("%1$-13s%2$-25s%3$-30s%4$-30s%5$-40s%n", datos[0], datos[1], datos[2], datos[3],datos[4]);
        }
    }
    public void ListaVentas(){
        String [] listaVentas = controlador.listaVentas();
        String [] datos;
        System.out.println("LISTADO DE VENTAS");
        System.out.printf("%1$-16s%2$-30s%3$-30s%4$-40s%n", "Rut cliente", "Codigo","Fecha","Cantidad de productos");

        for(int i=0; i< listaVentas.length;i++){
            datos = listaVentas[i].split(";");
            System.out.printf("%1$-16s%2$-30s%3$-30s%4$-40s%n", datos[0], datos[1], datos[2], datos[3]);
        }
    }
    public void printBoleta(Venta venta){
        String [] datos;
        String [] listaDatos = controlador.returnDatos(venta);
        System.out.println("-------------------------------------  BOLETA DE VENTA  -------------------------------------");
        System.out.println("FECHA VENTA -> " + venta.getFechaVenta());
        System.out.println("CODIGO VENTA -> " + venta.getCodigoVenta());
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%1$-10s%2$-30s%3$-20s%4$-20s%n", "Cantidad", "Descripción","Precio unitario","Valor venta");
        for(int i=0; i<listaDatos.length;i++){
            datos = listaDatos[i].split(";");
            System.out.printf("%1$-10s%2$-30s%3$-20s%4$-20s%n", datos[0], datos[1], datos[2], datos[3]);
        }
        System.out.println("MONTO TOTAL -> $" + controlador.getTotal(venta));
        System.out.println("--------------------------------------------------------------------------------------------");
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
            System.out.println("6.- Listar ventas");
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
                    break;
                case 7:
                    try {
                        controlador.saveClientes();
                        controlador.saveProductos();
                        controlador.saveVentas();
                        System.out.println("Datos guardados exitosamente");
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        controlador.readClientes();
                        controlador.readProductos();
                        controlador.readVentas();
                        System.out.println("Datos leidos exitosamente");
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
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
