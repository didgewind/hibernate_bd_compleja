package profe.hjs.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Clase est�tica para gestionar el ciclo de vida del SessionFactory y
 * de las diferentes sesiones.
 * 
 * La app que utilice esta clase debe invocar primero el m�todo initialize()
 * para inicializar la factor�a a partir del fichero de configuraci�n, y
 * dispose() para liberar recursos al terminar la aplicaci�n.
 * 
 * Para gestionar las sesiones tenemos 4 m�todos, dependiendo de si queremos
 * trabajar con una sesi�n extendida (session-per-conversation) o una sesi�n por
 * petici�n.
 * 
 * openSession() devuelve una sesi�n nueva (extendida)
 * 
 * closeSession() cierra la sesi�n extendida actual
 * 
 * getCurrentSession() devuelve una sesi�n nueva (por petici�n) o reutiliza
 * esa misma sesi�n (por petici�n) si no se ha cerrado a�n.
 * 
 * getSession() va a devolver la sesi�n extendida actual si existe
 * y a�n est� abierta o la currentSession (ya sea nueva o reutilizada). Este
 * m�todo existe b�sicamente para ser invocado por los DAOS y aislar a �stos
 * de si estamos utilizando sesiones extendidas o por petici�n.
 * 
 * @author made
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session openSession;
	
	/*
	 * M�todo s�lo para la doc de cl�
	 */
	public static SessionFactory getSessionFactory() {
		return HibernateUtil.sessionFactory;
	}

	public static void initialize() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// Usamos Configuration si no hay anotaciones
			// En esta aplicaci�n hay anotaciones en Regalo
			// return new Configuration().configure().buildSessionFactory();
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public static Session openSession() {
		openSession = sessionFactory.openSession();
		return openSession;
	}
	
	public static Session getSession() {
		if (isOpenSessionActive()) {
			return openSession;
		} else {
			return getCurrentSession();
		}
	}
	
	public static void closeSession() {
		if (isOpenSessionActive()) {
			openSession.close();
		}
	}
	
	public static void dispose() {
		sessionFactory.close();
	}
	
	private static boolean isOpenSessionActive() {
		return openSession != null && openSession.isOpen();
	}

}