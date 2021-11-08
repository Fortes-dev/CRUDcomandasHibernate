/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crudcomandashibernate;

import java.util.ArrayList;
import java.util.Scanner;
import models.Pedido;
import models.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author medin
 */
public class Main {

    static java.util.Date utilDate = new java.util.Date();
    static long lnMilisegundos = utilDate.getTime();
    private static final java.sql.Date DATE = new java.sql.Date(lnMilisegundos);

    private static Producto producto;
    private static Pedido pedido;

    private static Session s;

    static {
        s = HibernateUtil.getSessionFactory().openSession();
    }

    private static final Query LISTCARTA = s.createQuery("FROM Producto");
    private static final Query LISTPEDIDOS = s.createQuery("FROM Pedido");
    private static final Query PRODUCTOID = s.createQuery("FROM Producto pr where pr.id=:id");
    private static final Query PEDIDOID = s.createQuery("FROM Pedido p where p.id=:id");
    private static final Query PENDIENTEHOY = s.createQuery("FROM Pedido p where p.pendiente='si' and p.fecha=:fecha");

    public static void main(String[] args) {

        System.out.println("\n----------------------------------------\nBienvenido a su app de Comandas");
        menu();
    }

    /**
     * Método void para mostrar un menu de manera recursiva
     */
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        System.out.println("\nSeleccione la acción que desea realizar: \n----------------------------------------");
        System.out.println("1. Crear un nuevo pedido.\n"
                + "2. Eliminar un pedido existente.\n"
                + "3. Marcar pedido como recogido.\n"
                + "4. Mostrar pedidos pendientes de hoy.\n"
                + "5. Mostrar carta.\n"
                + "6. Salir.\n----------------------------------------");

        opcion = sc.nextInt();

        switch (opcion) {

            case 1 -> {

                LISTCARTA.list().forEach(e -> System.out.println(e));

                System.out.println("\nSeleccione el id del producto que desea pedir: ");

                Long id = sc.nextLong();

                PRODUCTOID.setParameter("id", id);

                var pedido = new Pedido();

                try {
                    producto = (Producto) PRODUCTOID.list().get(0);

                    pedido.setFecha(DATE);
                    pedido.setProducto(producto);
                    pedido.setPrecio(producto.getPrecio());
                    pedido.setPendiente("si");
                    pedido.setRecogido("no");

                    System.out.println("Ha pedido: " + producto);

                    Transaction t = s.beginTransaction();
                    s.save(pedido);
                    t.commit();
                } catch (Exception e) {
                    System.out.println("Producto no existente");
                } finally {
                    menu();
                }

            }

            case 2 -> {

                LISTPEDIDOS.list().forEach(e -> System.out.println(e));

                System.out.println("\nSeleccione el id del pedido a eliminar: ");

                Long id = sc.nextLong();

                PEDIDOID.setParameter("id", id);

                try {
                    pedido = (Pedido) PEDIDOID.list().get(0);

                    Transaction t = s.beginTransaction();
                    s.remove(pedido);
                    t.commit();

                    System.out.println("\nVa a eliminar el pedido: " + pedido);
                } catch (Exception e) {
                    System.out.println("\nPedido no existente");

                } finally {
                    menu();
                }

            }

            case 3 -> {

                LISTPEDIDOS.list().forEach(e -> System.out.println(e));

                System.out.println("\nSeleccione el id del pedido a recoger: ");

                Long id = sc.nextLong();

                PEDIDOID.setParameter("id", id);

                try {

                    pedido = (Pedido) PEDIDOID.list().get(0);

                    pedido.setPendiente("no");
                    pedido.setRecogido("si");

                    Transaction t = s.beginTransaction();
                    s.update(pedido);
                    t.commit();

                    System.out.println("\nHa recogido el pedido: " + pedido);
                } catch (Exception e) {
                    System.out.println("\nPedido no existente");

                } finally {
                    menu();
                }

            }

            case 4 -> {

                PENDIENTEHOY.setParameter("fecha", DATE);

                System.out.println("\nLas comandas pendientes de hoy son: \n");

                PENDIENTEHOY.list().forEach(e -> System.out.println(e));

                menu();
            }

            case 5 -> {

                LISTCARTA.list().forEach(e -> System.out.println(e));

                menu();
            }

            case 6 -> {

                System.out.println("https://github.com/Fortes-dev/CRUDcomandasHibernate.git");
                s.close();
                System.exit(0);

            }

            default -> {

                menu();
            }
        }
    }
}
