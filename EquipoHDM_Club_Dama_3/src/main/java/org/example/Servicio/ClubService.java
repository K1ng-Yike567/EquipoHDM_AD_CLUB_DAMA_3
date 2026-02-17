package org.example.Servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.Query;
import org.example.Entidades.Pista;
import org.example.Entidades.Reserva;
import org.example.Entidades.Socio;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

public class ClubService {
    private EntityManagerFactory emf;

    public ClubService() {
        this.emf = Persistence.createEntityManagerFactory("ClubPU");
    }

    /**
     * @param idPista
     * @param idSocio
     * @return true o false
     * @author Milena
     * Metodo para insertar una reserva, si el idPista o el idSocio es null return false,
     * si no es null persistimos el objeto
     */
    public boolean insertarReserva(Integer idPista, Integer idSocio, LocalDate fechaReserva, LocalTime horaReserva, Integer duracion, BigDecimal precio) {
        EntityManager em = emf.createEntityManager();
        Pista pista = em.find(Pista.class, idPista);
        Socio socio = em.find(Socio.class, idSocio);
        if (pista == null || socio == null) {
            em.close();
            return false;

        } else {
            Reserva reserva = new Reserva(socio, pista, fechaReserva, horaReserva, duracion, precio);
            em.getTransaction().begin();
            em.persist(reserva);
            em.getTransaction().commit();
            em.close();
            return true;
        }
    }

    /**
     * @author Milena
     * Metodo para obtener las reservas activas
     * @return
     */
    public List<Reserva> reservasActivas() {
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("ReservasActivas");
        return consulta.getResultList();
    }

    /**
     * @author Milena
     * Metodo para listar las reservas por socio
     * @param idSocio
     * @return
     */
    public List<Reserva> reservasCliente(Integer idSocio) {
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createQuery("Select r from Reserva r where r.idSocio = :idSocio");
        consulta.setParameter("idSocio", idSocio);
        return consulta.getResultList();
    }
}
