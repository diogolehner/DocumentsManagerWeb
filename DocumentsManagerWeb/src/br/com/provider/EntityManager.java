package br.com.provider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 *  Funcionalidade: Manager das entidades
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public final class EntityManager {
	private static EntityManagerFactory factory;
	
	private EntityManager(){
		
	}
	
	public static void createEntityManagerFactory(){
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("DocumentsManagerWeb");
		}
	}
	
	public static void destroyEntityManagerFactory(){
		if (factory != null) {
			factory.close();
		}
	}

	public static EntityManagerFactory getFactory() {
		return factory;
	}
	
}
