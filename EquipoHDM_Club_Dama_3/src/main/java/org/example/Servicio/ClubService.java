package org.example.Servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.Entidades.Pista;

import java.sql.SQLException;
import java.util.List;

public class ClubService {
    EntityManagerFactory emf;

    public ClubService() {
        this.emf = Persistence.createEntityManagerFactory("club_damaPU");
    }


    /**
     * Obtiene todas las pistas almacenadas en la base de datos
     * Utiliza JPQL para que peuda recuperar las entidades Pista sin necesidad de SQL manual.
     * @returnv devuelve una lista de objetos tipo Pista
     * @author Daniel
     */
    public List<Pista> cargarPistas() throws SQLException {
        EntityManager em = emf.createEntityManager();

        List<Pista> pistas = em.createQuery("SELECT p FROM Pista p", Pista.class).getResultList();
        em.close();
        return pistas;
    }


}
