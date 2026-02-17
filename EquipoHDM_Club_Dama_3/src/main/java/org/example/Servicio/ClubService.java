package org.example.Servicio;

import jakarta.persistence.*;
import org.example.Entidades.Pista;
import org.example.Entidades.Reserva;
import org.example.Entidades.Socio;

import java.util.ArrayList;
import java.util.List;

public class ClubService {
    private static EntityManagerFactory emf;

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
     * Modifica el estado de la disponibilidad de la pista
     *
     * @param idPista El id de la pista a modificar.
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
    
    // Métodos para cargar todos los datos en el DashBoard

    /**
     * Método que carga los datos de todos los ComboBox
     * @Author Hugo Garrido Rojo
     */
    public void cargarDatosDashboard() {
        cargarSociosDashBoard();
        cargarPistasDashBoard();
        cargarReservasDashBoard();
    }

    /**
     * Método que carga los socios en el DashBoard
     * @return Lista de socios
     * @Author Hugo Garrido Rojo
     */
    public static List<Socio> cargarSociosDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s from Socio s");
        List<Socio> socios = q.getResultList();

        return socios;

    }

    /**
     * Método que carga las pistas en el DashBoard
     * @return Lista de pistas
     * @Author Hugo Garrido Rojo
     */
    public static List<Pista> cargarPistasDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select p from Pista p");
        List<Pista> pistas = q.getResultList();

        return pistas;

    }

    /**
     * Método que carga las reservas en el DashBoard
     * @return Lista de reservas
     * @Author Hugo Garrido Rojo
     */
    public static List<Reserva> cargarReservasDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select r from Reserva r");
        List<Reserva> reservas = q.getResultList();

        return reservas;

    }

    //Métodos que gestionan los socios

    /**
     * Método que inserta los socios en la BD
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @Author Hugo Garrido Rojo
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

    /**
     * Método carga todos los ID de socios en el ComboBox de la ventana de baja para seleccionarlos
     * @return lista de ID
     * @Author Hugo Garrido Rojo
     */
    public List<String> cargarSociosComboBox() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s.id from Socio s");
        List<String> idSocios = q.getResultList();

        return idSocios;

    }


    /**
     * Método que da de baja (elimina) un socio determinado en la BD
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @Author Hugo Garrido Rojo
     */
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



