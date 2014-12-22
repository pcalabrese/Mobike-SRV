package persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class JPAImpl {
     public static void main(String [] args){
         EntityManagerFactory factory = Persistence.createEntityManagerFactory("EclipseLink-JPA-Installation");
         System.out.println("Is opened connection :: "+ factory.createEntityManager().isOpen());
     }
}