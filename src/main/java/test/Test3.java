package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Libro;

public class Test3 {

	 public static void main(String[] arg) {
		 
		 EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Persistencia");
		 EntityManager emanager = emfactory.createEntityManager();
		 
		 Libro l1 = new Libro("100","autor1","titulo1", (byte) 0);
		 Libro l2 = new Libro("200","autor2","titulo2", (byte) 0);
		 Libro l3 = new Libro("300","autor3","titulo3", (byte) 0);
		 Libro l4 = new Libro("400","autor4","titulo4", (byte) 0);
		 
		 emanager.getTransaction().begin();
		 emanager.persist(l1);
		 emanager.persist(l2);
		 emanager.persist(l3);
		 emanager.persist(l4); 
		 emanager.getTransaction().commit();
		 
		 System.out.println("Consultas");
		 
		 TypedQuery<Libro> query1 = emanager.createQuery("Select e from Libro e", Libro.class);
		 List<Libro> list = query1.getResultList();
		 System.out.println(list);

		 Query query2 = emanager.createQuery("Select MAX(e.isbn) from Libro e");
		 Object result = query2.getSingleResult();
		 System.out.println("El Mayor código de libro es: "+result);
		 
		 
		 
		 emanager.close();
		 emfactory.close();
		 
	 }
	
}
