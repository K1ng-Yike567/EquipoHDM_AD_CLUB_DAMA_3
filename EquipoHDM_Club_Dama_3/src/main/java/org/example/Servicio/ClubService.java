package org.example.Servicio;

import jakarta.persistence.*;
import org.example.Entidades.Pista;
import org.example.Entidades.Reserva;
import org.example.Entidades.Socio;

import java.util.List;

public class ClubService {

    private static EntityManagerFactory emf;

    public ClubService() {
        emf = Persistence.createEntityManagerFactory("club_damaPU");
    }


    /**
     * Métodos para cargar todos los datos en el DashBoard
     * Author: Hugo Garrido Rojo
     */
    public void cargarDatosDashboard() {
        cargarSocios();
        cargarPistas();
        cargarReservas();
    }

    public static List<Socio> cargarSocios() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s from Socio s");
        List<Socio> socios = q.getResultList();

        return socios;

    }

    public static List<Pista> cargarPistas() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select p from Pista p");
        List<Pista> pistas = q.getResultList();

        return pistas;

    }

    public static List<Reserva> cargarReservas() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select r from Reserva r");
        List<Reserva> reservas = q.getResultList();

        return reservas;

    }

    /**
     * Métodos que gestionan los socios
     * Author: Hugo Garrido Rojo
     */

    public String insertarSocio(Socio s) throws PersistenceException {

        EntityManager em = emf.createEntityManager();

        //Comprobación de que al menos el ID no está vacío
        if (s.getIdSocio().isEmpty()) {
            return "ID vacío";
        }


        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();

        em.close();
        return "inserción validada";


    }

    public List<String> cargarSociosComboBox() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s.id from Socio s");
        List<String> idSocios = q.getResultList();

        return idSocios;

    }


    public String darDeBajaASocio(String idSocio) throws PersistenceException {

        EntityManager em = emf.createEntityManager();

        //Excepción ningún socio seleccionado
        if (idSocio == null) {
            em.close();
            return "Socio no seleccionado";
        }

        Socio s = em.find(Socio.class, idSocio);

        //Excepción ningún socio seleccionado


        //Excepción socio tiene reservas activas
        Query q = em.createQuery("select r from Reserva r where r.idSocio.idSocio = :idRecibido");
        q.setParameter("idRecibido", idSocio);

        List<Reserva> reservasSocio = q.getResultList();

        if (!reservasSocio.isEmpty()) {
            em.close();
            return "Socio con reservas activas";
        }

        //Si ha pasado por todas las verificaciones, entonces ya podemos eliminar al socio de la base de la base de datos

        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();

        em.close();
        return "Baja validada";

    }

}



