package util;
import javax.persistence.EntityManager;

import javax.persistence.Persistence;

public class UtilJPA {

	//El concepto de persistencia implica el hecho de almacenar nuestras entidades
	//(objetos Java de tipo POJO) en un sistema de almacenamiento, normalmente una base de datos relacional
	//JPA maneja todas las operaciones CRUD a través de la interface EntityManager. 
	    private static final EntityManager em;
	    
	   static{
	        try{
	            em=Persistence.createEntityManagerFactory("Persistencia").createEntityManager();
	        }catch(Throwable t){
	            System.out.println("Error a inicializar el Entity Manager  "+t);
	            t.printStackTrace();
	            throw new ExceptionInInitializerError();
	        }
	    }

	    public static  EntityManager getEntityManager(){
	            return em;
	    }
	
}