package org.example.Servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.Entidades.Pista;
import org.example.Entidades.Reserva;
import org.example.Entidades.Socio;

import java.util.List;

public class ClubService {

    private static EntityManagerFactory emf;

    public ClubService() {
        emf = Persistence.createEntityManagerFactory("club_damaPU");
    }

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


}
