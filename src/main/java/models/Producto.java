/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author medin Modelo de la tabla producto
 */

@Entity
@Table(name="producto")
public class Producto implements Serializable{

    /**
     * Atributos que coinciden con las columnas de la tabla producto
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    private String nombre;
    private String tipo;
    private double precio;

    
    @OneToMany(mappedBy="producto", fetch=FetchType.EAGER)
    private List<Pedido> pedidos;

    /**
     * Constructor vacio
     */
    public Producto() {
    }

    /**
     * Constructor con parametros
     *
     * @param id
     * @param nombre
     * @param precio
     */
    public Producto(Long id, String nombre, String tipo, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    /**
     * Getters y setters de los campos/atributos
     *
     * @return
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Método toString para mostar un producto por consola.
     *
     * @return
     */
    @Override
    public String toString() {
        return "id[" + id + "] " + nombre + " - precio: " + precio;
    }

}
