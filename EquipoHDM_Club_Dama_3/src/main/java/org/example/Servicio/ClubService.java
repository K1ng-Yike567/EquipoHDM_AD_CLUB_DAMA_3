package org.example.Servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.example.Entidades.Pista;
import org.example.Entidades.Reserva;
import org.example.Entidades.Socio;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Reserva> cargarReservas() {

    }
}
