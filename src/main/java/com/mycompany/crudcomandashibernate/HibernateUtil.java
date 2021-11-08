/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crudcomandashibernate;

/**
 *
 * @author medin
 */
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author FranciscoRomeroGuill
 */
public class HibernateUtil {
    
    private static final SessionFactory sf;
    
    static{
        try{
            sf = new Configuration().configure().buildSessionFactory();
        } catch(HibernateException ex){
            System.out.println("Error al crear SessionFactory");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }
    
}
