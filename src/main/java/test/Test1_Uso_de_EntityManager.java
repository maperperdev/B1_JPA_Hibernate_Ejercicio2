package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidades.Libro;

public class Test1_Uso_de_EntityManager {

	 public static void main(String[] arg) {
		 //conexion a traves de EntityManager sin usar patron Facade
		 
		 EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Persistencia");
		 
		 EntityManager emanager = emfactory.createEntityManager();
		 
		 //Seria como un insert
		 //emanager.persist();
		 
		 //find, merge, refresh, remove... Son los que nos interesa
		 
		 Libro l1 = new Libro("100","autor1","titulo1", (byte) 0);
		 Libro l2 = new Libro("200","autor2","titulo2", (byte) 0);
		 Libro l3 = new Libro("300","autor3","titulo3", (byte) 0);
		 Libro l4 = new Libro("400","autor4","titulo4", (byte) 0);
		 
		 //Como realizar un insert. Primero debemos comenzar la transaccion y posteriormente
		 //realizar el "insert" de cada objeto mediante persist
		 emanager.getTransaction().begin();
		 
		 emanager.persist(l1);
		 l1.setTitulo("JPA e Hibernate");
		 
		 //Guarda automaticamente los cambios en la base de datos actualizando con los cambios
		 //realziados en la entidad
		 emanager.flush();
		 
		 emanager.persist(l2);
		 emanager.persist(l3);
		 emanager.persist(l4);
		 
		 emanager.getTransaction().commit();
		 
		 //1.Comprobamos si la entidad esta gestionada todavia o no
		 boolean gestionada = emanager.contains(l1);
		 System.out.println("l1 está gestionada"+gestionada);
		 
		 //boolean gestionada2 = emanager.contains(l4);
		 //System.out.println("l4 está gestionada"+gestionada2);		 
		 
		 //2.Compruebo creando otra transaccion si l1 sigue o no gestionada
		 emanager.getTransaction().begin();
		 l1.setTitulo("nuevo cambio");
		 boolean gestionadaNueva = emanager.contains(l1);
		 System.out.println("l1 está en una nueva transacción, ¿sigue gestionada? "+gestionadaNueva);
		 emanager.getTransaction().commit();
		 
		 //3.Buscar un libro
		 emanager.getTransaction().begin();
		 Libro libroBuscado = emanager.find(Libro.class, "200");
		 System.out.println(libroBuscado);
		 
		 
		 libroBuscado.setTitulo("el árbol de la vida");
		 libroBuscado = emanager.find(Libro.class, "200");
		 System.out.println(libroBuscado);
		 emanager.getTransaction().commit();
		 
		 //4.Consultas
		 TypedQuery<Libro> query = emanager.createQuery("Select e from Libro e", Libro.class);
		 List<Libro> list = query.getResultList();
		 System.out.println(list);
		 
		 //5.Recuperar el estado de una entidad despues de modificarla
		 
		 emanager.getTransaction().begin();
		 //emanager.persist(libroBuscado);
		 libroBuscado.setTitulo("hoy es viernes");
		 emanager.refresh(libroBuscado);
		 //emanager.getTransaction().commit();
		 System.out.println("**Compruebo si ha modificado el titulo l200 o ha revertido los cambios**");
		 System.out.println("**Gracias al refresh ha revertido los cambios**");
		 List<Libro> list2 = query.getResultList();
		 System.out.println(list2);
		 emanager.getTransaction().commit();
		 
		 //6.Actualizar un libro:si la entidad esta persistida se actualiza solo
		 emanager.getTransaction().begin();
		 libroBuscado.setAutor("pepito");
		 //emanager.merge(libroBuscado);
		 emanager.getTransaction().commit();
		 
		 //7.Actualizar un libro: si la entidad esta en modo Detach (separada)
		 emanager.getTransaction().begin();
		 //Si despues de ejecutar el detach no hay manipulacion de la entidad no se guarda
		 emanager.detach(l4);
		 l4.setAutor("Juanito");
		 emanager.getTransaction().commit();
		 System.out.println(query.getResultList());
		 
		 emanager.getTransaction().begin();
		 l4.setAutor("Luisita");
		 emanager.merge(l4);
		 System.out.println(query.getResultList());
		 System.out.println("l4 pasa a estado manage "+emanager.contains(l4));
		 
		 Libro LibroNuevo = emanager.find(Libro.class, "400");
		 System.out.println("LibroNuevo pasa a estado manage "+emanager.contains(LibroNuevo));
		 
		 System.out.println(emanager.find(Libro.class, "400"));
		 System.out.println("l4 pasa a estado manage "+emanager.contains(l4));
		 //Si queremos que persistan los cambios despues del merge se debe de hacer un
		 //persist
		 emanager.getTransaction().commit();
		 
		 
		 
		 emanager.close();
		 emfactory.close();
		 
	 }
	
}
