package co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo;

import java.util.ArrayList;

public class Grupo {
    private int _id;
    private String nombre;
    private int numClientes;
    private ArrayList<Cliente> clientes;

    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public Grupo(int _id, String nombre, int numClientes) {
        this._id = _id;
        this.nombre = nombre;
        this.numClientes = numClientes;
    }

    public int get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumClientes() {
        return numClientes;
    }

    public void setNumClientes(int numClientes) {
        this.numClientes = numClientes;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "_id=" + _id +
                ", nombre='" + nombre + '\'' +
                ", numClientes=" + numClientes +
                '}';
    }
}
