package profe.hjs.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Clase estática para gestionar el ciclo de vida del SessionFactory y
 * de las diferentes sesiones.
 * 
 * La app que utilice esta clase debe invocar primero el método initialize()
 * para inicializar la factoría a partir del fichero de configuración, y
 * dispose() para liberar recursos al terminar la aplicación.
 * 
 * Para gestionar las sesiones tenemos 4 métodos, dependiendo de si queremos
 * trabajar con una sesión extendida (session-per-conversation) o una sesión por
 * petición.
 * 
 * openSession() devuelve una sesión nueva (extendida)
 * 
 * closeSession() cierra la sesión extendida actual
 * 
 * getCurrentSession() devuelve una sesión nueva (por petición) o reutiliza
 * esa misma sesión (por petición) si no se ha cerrado aún.
 * 
 * getSession() va a devolver la sesión extendida actual si existe
 * y aún está abierta o la currentSession (ya sea nueva o reutilizada). Este
 * método existe básicamente para ser invocado por los DAOS y aislar a éstos
 * de si estamos utilizando sesiones extendidas o por petición.
 * 
 * @author made
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session openSession;
	
	/*
	 * Método sólo para la doc de clé
	 */
	public static SessionFactory getSessionFactory() {
		return HibernateUtil.sessionFactory;
	}

	public static void initialize() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// Usamos Configuration si no hay anotaciones
			// En esta aplicación hay anotaciones en Regalo
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