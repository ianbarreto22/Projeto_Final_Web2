package com.projeto.filmes.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();
	
	
	
	public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}



	private static SessionFactory criarFabricaDeSessoes() {
		try {
			Configuration config = new Configuration();
			config.configure("hibernate.cfg.xml");
			
			return config.buildSessionFactory();
		} catch(Throwable e) {
			System.err.println("Erro na criação da fábrica de sessões: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

}
