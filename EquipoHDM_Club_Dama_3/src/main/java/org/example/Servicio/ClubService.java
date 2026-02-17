package org.example.Servicio;

import jakarta.persistence.*;
import org.example.Entidades.Pista;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubService {
    private EntityManagerFactory emf;

    public ClubService() {
        this.emf = Persistence.createEntityManagerFactory("club_damaPU");
    }


    /**
     * Obtiene todas las pistas almacenadas en la base de datos
     * Utiliza JPQL para que peuda recuperar las entidades Pista sin necesidad de SQL manual.
     *
     * @returnv devuelve una lista de objetos tipo Pista
     * @author Daniel
     */
    public List<Pista> cargarPistas() throws PersistenceException {
        EntityManager em = emf.createEntityManager();

        List<Pista> pistas = new ArrayList<>();

        //consulta para obtener las instancias de la entidad Pista
        pistas = em.createQuery("SELECT p FROM Pista p", Pista.class).getResultList();

        em.close();
        return pistas;
    }

    /**
     * Inserta una nueva pista en la base de datos.
     * Primero comprueba si ya existe una pista con el mismo id.
     * Si no existe inicia transaccion y persiste
     *
     * @param pista Objeto Pista a registrar.
     * @return true si se ha insertado correctamente, false si no.
     * @author Daniel
     */
    public boolean insertarPista(Pista pista) throws PersistenceException {
        EntityManager em = emf.createEntityManager();

        try {
            //si la pista ya existe no la inserta
            if (em.find(Pista.class, pista.getIdPista()) != null) {
                em.close();
                return false;
            }

            //si no existe la inserta
            em.getTransaction().begin();
            em.persist(pista);
            em.getTransaction().commit();
            em.close();
            return true;

        } catch (Exception e) {
            em.close();
            return false;
        }

    }

    /**
     * Obtiene una lista pista para cargar en un combobox.
     * @return devuelve una lista de Strings con los ids de las pistas.
     * @author Daniel
     */
    public List<String> cargarPistasCombobox() {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select p.id from Pista p");
        List<String> pistas = q.getResultList();

        return pistas;
    }


    /**
     * Modifica el estado de la disponibilidad de la pista
     *
     * @param idPista      El id de la pista a modificar.
     * @param seleccionado El nuevo estado de disponibilidad.
     * @return Devuelve true si se modifico correctamente, false si no.
     * @Author Daniel
     */
    public boolean cambiarDisponibilidadPista(String idPista, Boolean seleccionado) throws PersistenceException {
        EntityManager em = emf.createEntityManager();

        if (idPista == null) {
            return false; //si el id es nulo se cancela
        }

        try {

            Pista pista = em.find(Pista.class, idPista); //buscamos la pista por su id

            if (pista != null) {
                em.getTransaction().begin();
                pista.setDisponible(seleccionado);
                em.getTransaction().commit(); //comiteamos la transaccion
                em.close();
                return true;
            } else {

                em.close();
                return false; //si no se encuentra la pista para

            }


        } catch (Exception e) {
            return false;
        }
    }


}
