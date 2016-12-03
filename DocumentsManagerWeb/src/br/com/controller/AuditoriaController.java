package br.com.controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.entities.Auditoria;

/**
 * 
 *  Funcionalidade: Controller da tabela de auditoria. Gerencia acesso ao banco de dados.
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class AuditoriaController implements Serializable{
	private static final long serialVersionUID = 4504272585690846103L;
	
	private EntityManagerFactory emf = null;
	
	public AuditoriaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Auditoria auditoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Auditoria> getAuditoria() throws Exception {
        EntityManager em = getEntityManager();
        
    	try {
    		Query query = em.createNamedQuery("audFind");
    		
    		return (List<Auditoria>) query.getResultList();
    	}catch(Exception e){
    		throw new Exception(e.getMessage());
    	} finally {
    		em.close();
    	}
    }
}
