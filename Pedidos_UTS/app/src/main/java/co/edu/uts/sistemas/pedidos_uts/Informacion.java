package co.edu.uts.sistemas.pedidos_uts;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class Informacion {
    public final static String user="123";
    public final static String passws = "123";
    public final static String URL_IMG = "http://i.imgur.com/DvpvklR.png";

    public final static ArrayList<Producto> productos = new ArrayList<>();

    public final static boolean existeProducto(String codigo) {
        boolean flag=false;
        for (Producto p:productos) {
            if (p.getCodigo().equals(codigo)) {
                flag=true;
                break;
            }
        }
        return flag;
    }

    public final static boolean existeProducto(String codigo, int posicion) {
        boolean flag=false;
        for (int i=0; i<productos.size(); i++) {
            Producto p=productos.get(i);
            if (p.getCodigo().equals(codigo) && posicion!=i) {
                flag=true;
                break;
            }
        }
        return flag;
    }

    public final static void cargarProductos() {
        Producto producto = new Producto("PROD001","Computador Escritorio", 1800000);
        productos.add(producto);
        producto = new Producto("PROD002","Portatil", 2500000);
        productos.add(producto);
        producto = new Producto("PROD003","Tableta", 1200000);
        productos.add(producto);
    }
}
