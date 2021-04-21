package co.edu.uts.sistemas.pedidos_uts.model;

import java.util.ArrayList;

public class Pedido {
    private int _id;
    private int numero;
    private String fecha;
    private float valor;
    private Cliente cliente;
    private ArrayList<Producto> productos;

    public Pedido(String fecha, Cliente cliente) {
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public Pedido(int _id, int numero, String fecha, float valor) {
        this._id = _id;
        this.numero = numero;
        this.fecha = fecha;
        this.valor = valor;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto p) {
        productos.add(p);
    }

    public void deleteProducto(Producto p) {
        productos.remove(p);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numero=" + numero +
                ", fecha='" + fecha + '\'' +
                ", valor=" + valor +
                ", cliente=" + ((cliente!=null)?cliente.getName():"No Cliente") +
                '}';
    }
}
