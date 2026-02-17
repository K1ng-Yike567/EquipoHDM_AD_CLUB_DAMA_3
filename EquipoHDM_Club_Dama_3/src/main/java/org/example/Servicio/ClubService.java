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
    public boolean insertarReserva(String idReserva, Integer idPista, Integer idSocio, LocalDate fechaReserva, LocalTime horaReserva, Integer duracion, BigDecimal precio) {
        EntityManager em = emf.createEntityManager();
        Pista pista = em.find(Pista.class, idPista);
        Socio socio = em.find(Socio.class, idSocio);
        if (pista == null || socio == null) {
            em.close();
            return false;

        } else {
            Reserva reserva = new Reserva(idReserva, socio, pista, fechaReserva, horaReserva, duracion, precio);
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
        em.close();
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
        em.close();
        return consulta.getResultList();
    }

    /**
     * @author Milena
     * Metodo para cancelar una reserva recibiendo un id de reserva y comprobando que no sea nulo,
     * si es nulo return false. Si no es nulo se inicia la transaccion y lo elimina
     * @param idReserva
     * @return
     */
    public boolean cancelarReserva(String idReserva) {
        if (idReserva.isEmpty()) {
            return false;
        } else {
            EntityManager em = emf.createEntityManager();
            Reserva reserva = em.find(Reserva.class, idReserva);
            em.getTransaction().begin();
            em.remove(reserva);
            em.getTransaction().commit();
            em.close();
            return true;
        }
    }
}
