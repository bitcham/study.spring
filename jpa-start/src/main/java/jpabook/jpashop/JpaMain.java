package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Movie;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Movie movie = new Movie();
            movie.setDirector("director");
            movie.setActor("actor");
            em.persist(movie);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally{
            em.close();
        }

        emf.close();

    }
}
