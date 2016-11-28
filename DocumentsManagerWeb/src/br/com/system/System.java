package br.com.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.constantes.SystemConstants;
import br.com.io.GerenciaArquivo;
import br.com.provider.EntityManager;

public class System implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		EntityManager.destroyEntityManagerFactory();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		EntityManager.createEntityManagerFactory();
		SystemConstants.setChaveMestre(GerenciaArquivo.getKeyMaster());
	}
}

