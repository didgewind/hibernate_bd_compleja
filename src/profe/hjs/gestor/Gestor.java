package profe.hjs.gestor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.criterion.Restrictions;

import profe.hjs.daos.EmpHBDAO;
import profe.hjs.daos.EmpleadosDAO;
import profe.hjs.model.Direccion;
import profe.hjs.model.Empleado;
import profe.hjs.model.Evento;
import profe.hjs.model.Nomina;
import profe.hjs.model.Ordenador;
import profe.hjs.model.Regalo;
import profe.hjs.model.Sala;
import profe.hjs.util.HibernateUtil;
import profe.hjs.util.Teclado;


public class Gestor {
	
	private static final Logger logger = Logger.getLogger(Gestor.class);
	private EmpleadosDAO dao = new EmpHBDAO();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *  Elegiremos go(), pruebas() o pruebasOpenSession en función de que queramos ejecutar 
		 *  la aplicación o las pruebas.
		 *  OJO: en ambos casos tenemos que configurar bien el fichero de mapeo de empleado en su
		 *  relación con nóminas, porque los cascades lanzan excepciones en las pruebas
		 */
		new Gestor().pruebas();
	}
	
	/*
	 * Para pruebas con una sesión extendida.
	 * 
	 * Abrimos la sesión, en una transacción recuperamos un empleado y le cambiamos la edad (en memoria),
	 * en la siguiente transacción (con la misma sesión) modificamos los apellidos de ese empleado
	 * (con lo que se comprueba que ese objeto sigue attached a la misma sesión).
	 * 
	 * Si cambiamos la llamada a openSession por getCurrentSession se lanza una excepción al comenzar la
	 * segunda transacción porque la sesión, al ser per-request, se ha cerrado al hacer commit de la
	 * transacción en la línea anterior.
	 */
	private void pruebasOpenSession() {
		HibernateUtil.initialize();
		try {
			Session session = HibernateUtil.openSession();
			session.beginTransaction();
			Empleado emp = dao.getEmpleado("34334789H");
			System.out.println(emp);
			emp.setEdad(20);
			session.getTransaction().commit();
			session.beginTransaction();
			emp.setApellidos("Julieta");
			session.getTransaction().commit();
			System.out.println(emp);
		} catch (SessionException te) {
			logger.error("Error en pruebasOpenSession", te);
		} catch (Exception e) {
			logger.error("Error en pruebasOpenSession", e);
			HibernateUtil.getSession().getTransaction().rollback();
		}
		HibernateUtil.closeSession();
		HibernateUtil.dispose();		
	}
	
	private void pruebas() {
		HibernateUtil.initialize();
		try {
			HibernateUtil.getCurrentSession().beginTransaction();
			//ejemploInverse();
			//criteriaYAlias();
			//ejemploNavegabilidadInversa();
			pruebaModifEmp();
			HibernateUtil.getCurrentSession().getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error en pruebas", e);
			HibernateUtil.getCurrentSession().getTransaction().rollback();
		}
		HibernateUtil.dispose();
	}
	
	private void pruebaModifEmp() {
		Empleado emp = new Empleado("01293474E", "yo", "tú", 30);
		dao.modificaEmpleado(emp);
	}
	
	/*
	 * Recuperar los eventos a los que asiste el empleado con cif 34334789H usando criteria y
	 * alias. Imprimir nombre de ese empleado
	 */
	private void criteriaYAlias() {
		Session sess = HibernateUtil.getCurrentSession();
		List<Evento> eventos = sess.createCriteria(Evento.class)
			    .createAlias("asistentes", "asis")
			    .add( Restrictions.eq("asis.cif", "34334789H") )
			    .list();
		listaEventos(eventos);
	}
	
	/*
	 * Recupero los eventos a los que asiste un empleado. La asociación no es navegable de empleado
	 * a evento
	 */
	private void ejemploNavegabilidadInversa() {
		for (Evento ev : dao.getEventosDeEmpleado("01293474E")) {
			System.out.println(ev);
		}
	}
	
	private void ejemploInverse() {
		inverseNominaEmpleado();
	}
	
	/*
	 * Saco una nómina de un empleado y se la pongo a otro. Jugar con el inverse del mapeo de la
	 * relación en el xml de empleado
	 */
	private void inverseNominaEmpleado() {
		// Recupero un par de empleados
		Empleado emp1, emp2;
		emp2 = dao.getEmpleado("01293474E");
		emp1 = dao.getEmpleado("21094387T");
		// Saco una nomina de uno de ellos
		Set<Nomina> nominas = emp1.getNominas();
		Iterator<Nomina> iNominas = nominas.iterator();
		Nomina nom1 = iNominas.next();
		nominas.remove(nom1);
		// La meto en el otro
		nominas = emp2.getNominas();
		nominas.add(nom1);
		// Si no hacemos esto, el cambio no se refleja en la bd cuando inverse=true
		nom1.setEmpleado(emp2);
	}
	
	private void go() {
		HibernateUtil.initialize(); // Para que se cargue hibernate antes de mostrar el menú
		boolean bSalir = false;
		while (!bSalir) {
			menu();
			int option = Teclado.leeNumero();
			try {
				HibernateUtil.getCurrentSession().beginTransaction();
				switch (option) {
				
				case 0:
					bSalir=true;
					break;
					
				case 1:
					insertaEmpleado();
					break;
					
				case 2:
					eliminaEmpleado();
					break;
					
				case 3:
					modificaEmpleado();
					break;

				case 4:
					listaEmpleados();
					break;
					
				case 8:
					listaEventos();
					break;
					
				case 11:
					listaAsistentesAEvento();
					break;
					
				case 12:
					listaNominasDeEmpleado();
					break;
					
				case 13:
					listaNomina();
					break;
					
				case 14:
					listaSalaYSusEventos();
					break;
					
				case 15:
					listaTutorYTutelado();
					break;
					
				case 16:
					muestraRegaloDeEmpleado();
					break;
					
				case 18:
					listaOrdenadores();
					break;
					
				case 19:
					insertaOrdenador();
					break;
					
				case 20:
					eliminaOrdenador();
					break;
					
				case 21:
					getRegaloXDesc();
					break;
					
	/*
					
				case 5:
					pideEInsertaEvento();
					break;
	
				case 9:
					pideEInsertaEmpleadoEvento();
					break;
	
				case 10:
					listaEventosDeEmpleado();
					break;
					
	*/				
				}
				HibernateUtil.getCurrentSession().getTransaction().commit();
			} catch (Exception e) {
				logger.error("Error en el menú", e);
				HibernateUtil.getCurrentSession().getTransaction().rollback();
			}
		}
        HibernateUtil.dispose();
	}
	
	private void menu() {
		System.out.println("Opciones:");
		System.out.println("0 - Salir");
		System.out.println("1 - Insertar nuevo empleado");
		System.out.println("2 - Eliminar empleado");
		System.out.println("*3 - Modificar empleado");
		System.out.println("4 - Listar empleados");
		System.out.println("*5 - Insertar nuevo evento");
		System.out.println("*6 - Eliminar evento");
		System.out.println("*7 - Modificar evento");
		System.out.println("8 - Listar eventos");
		System.out.println("*9 - Asociar evento a empleado");
		System.out.println("10 - Listar eventos de un empleado");
		System.out.println("11 - Listar empleados de un evento");
		System.out.println("12 - Listar nóminas de un empleado");
		System.out.println("13 - Listar nómina");
		System.out.println("14 - Listar sala y sus eventos");
		System.out.println("15 - Listar tutor y tutelado de empleado");
		System.out.println("16 - Regalo de...");
		System.out.println("18 - Lista ordenadores");
		System.out.println("19 - Inserta ordenador");
		System.out.println("20 - Elimina ordenadores");
		System.out.println("21 - Busca regalos");
	}

	private void eliminaEmpleado() {
		System.out.println("cif del empleado a eliminar");
		dao.eliminaEmpleado(Teclado.leeString());
	}
	
	private void listaNomina() {
		System.out.println("id de la nómina a listar");
		long idNomina = Teclado.leeNumero();
		Nomina nomina = dao.getNomina(idNomina);
		System.out.println(nomina);
	}
	
	private void listaNominasDeEmpleado() {
		System.out.println("cif: ");
		String cif = Teclado.leeString();
		Empleado emp = dao.getEmpleadoConNominas(cif);
		Set<Nomina> listaNominas = emp.getNominas();
		for (Nomina nomina : listaNominas) {
        	System.out.println(nomina.toString());
        }
	}
	
	private void listaEmpleados() {
		Set<Empleado> listaEmpleados = dao.getAllEmpleados();
		for (Empleado empleado : listaEmpleados) {
        	System.out.println(empleado.toString());
        }
	}
	
	private void listaEventos() {
        listaEventos(dao.getEventos());
	}
	
	private void listaEventos(List<Evento> listaEventos) {
        for (Evento evento : listaEventos) {
        	System.out.println(evento.toString());
        }		
	}
	
	private void listaAsistentesAEvento() {
		System.out.println("id del evento cuyos asistentes se quieren mostrar: ");
		long id = (long) Teclado.leeNumero();
		Evento ev = dao.getEvento(id);
		for (Empleado emp : ev.getAsistentes()) {
			System.out.println(emp.toString());
		}
	}
		
	private Empleado pideEmpleado() {
		Empleado eReturn = null;
		System.out.print("Cif: ");
		String cif = Teclado.leeString();
		System.out.print("Nombre: ");
		String nombre = Teclado.leeString();
		System.out.print("Apellidos: ");
		String apellidos = Teclado.leeString();
		System.out.print("Edad: ");
		int edad = Teclado.leeNumero();
		eReturn = new Empleado(cif, nombre, apellidos, edad);
		return eReturn;
	}
	
	private Direccion pideDatosDireccion() {
		Direccion dReturn = null;
		System.out.print("Calle:");
		String calle = Teclado.leeString();
		System.out.print("Cp:");
		int cp = Teclado.leeNumero();
		dReturn = new Direccion(calle,cp);
		return dReturn;
	}
	
	private void insertaEmpleado() {
		logger.debug("Inserto un empleado nuevo");
		Empleado emp = pideEmpleado();
		System.out.print("Â¿DirecciÃ³n ya en bd (y/n)?");
		String sDirEnBd = Teclado.leeString();
		boolean bDirEnBd = sDirEnBd.equalsIgnoreCase("y");
		Direccion dir = null;
		if (bDirEnBd) {	// La dir está ya en la bd, la recuperamos y la asignamos
			System.out.print("Id de la dirección:");
			long idDir = Teclado.leeNumero();
			dir = dao.getDireccion(idDir);
		} else {
			dir = pideDatosDireccion();
		}
		emp.setDireccion(dir);
		dao.insertaEmpleado(emp);
		logger.debug("Empleado insertado");

	}

	private void modificaEmpleado() {
		logger.debug("Modifico un empleado antiguo");
		Empleado emp = pideEmpleado();
		System.out.print("Â¿DirecciÃ³n ya en bd (y/n)?");
		String sDirEnBd = Teclado.leeString();
		boolean bDirEnBd = sDirEnBd.equalsIgnoreCase("y");
		Direccion dir = null;
		if (bDirEnBd) {	// La dir está ya en la bd, la recuperamos y la asignamos
			System.out.print("Id de la dirección:");
			long idDir = Teclado.leeNumero();
			dir = dao.getDireccion(idDir);
		} else {
			dir = pideDatosDireccion();
		}
		emp.setDireccion(dir);
		dao.modificaEmpleado(emp);
		logger.debug("Empleado insertado");

	}

	
	private void listaSalaYSusEventos() {
		// Recupero Sala
		System.out.print("id de la sala:");
		String idSala = Teclado.leeString();
		Sala sala = dao.getSala(idSala);
		// Recupero eventos de sala
		List<Evento> listaEventos = dao.getEventosDeSala(idSala);
		// Lo imprimo tÃ³
		System.out.println(sala);
        for (Evento evento : listaEventos) {
        	System.out.println("\t" + evento.toString());
        }
	}
	
	private void listaTutorYTutelado() {
		System.out.println("cif: ");
		String cif = Teclado.leeString();
		Empleado emp = dao.getEmpleado(cif);
		System.out.println(emp);
		System.out.println("Tutor: " + (emp.getTutor() == null 
				? "sin tutor asignado" : emp.getTutor()));
//		System.out.println("Tutor de tutor: " + (emp.getTutor() == null 
//				? "sin tutor asignado" : emp.getTutor().getTutor()));
		System.out.println("Tutelado: " + (emp.getTuteloA() == null 
				? "sin tutelado asignado" : emp.getTuteloA()));
//		System.out.println("Tutelado de tutelado: " + (emp.getTuteloA() == null 
//				? "sin tutelado asignado" : emp.getTuteloA().getTuteloA()));
	}
	
	private void muestraRegaloDeEmpleado() {
		System.out.println("cif: ");
		String cif = Teclado.leeString();
		Empleado emp = dao.getEmpleado(cif);
		System.out.println(emp);
		Set<Regalo> regalos = dao.getRegalos();
		Regalo regaloAsignado = null;
		for (Regalo regalo : regalos) {
			if (regalo.getPara().equals(emp)) {
				regaloAsignado = regalo;
				break;
			}
		}
		System.out.println(regaloAsignado);

	}
	
	private void listaOrdenadores() {
		List<Ordenador> listaOrdenadores = dao.getOrdenadores();
		for (Ordenador ordenador : listaOrdenadores) {
			System.out.println(ordenador);
		}
	}
	
	private void insertaOrdenador() {
		System.out.println("id: ");
		String id = Teclado.leeString();
		System.out.println("cif: ");
		Empleado emp = null;
		String cif = Teclado.leeString();
		if (!cif.equals("")) {
			emp = dao.getEmpleado(cif);
		}
		Ordenador ordenador = new Ordenador(id, emp);
		dao.insertaOrdenador(ordenador);
	}
	
	private void eliminaOrdenador() {
		System.out.println("id: ");
		String id = Teclado.leeString();
		dao.eliminaOrdenador(id);
	}
	
	private void getRegaloXDesc() {
		System.out.println("Describe el regalo: ");
		String descr = Teclado.leeString();
		List<Regalo> listaRegalos = dao.getRegalosXDescr(descr);
		for (Regalo regalo : listaRegalos) {
			System.out.println(regalo);
		}
	}
/*	
	
	private void listaEventosDeEmpleado() {
		System.out.println("id del evento cuyos asistentes se quiere mostrar: ");
		long id = Teclado.leeNumero();
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Empleado emp where " +
        		":evento member of emp.eventos");
        query.setLong("evento", id);
        List<Empleado> listaEmpleados = query.list();
        for (Empleado empleado : listaEmpleados) {
        	System.out.println(empleado.toString());
        }
        // Bidireccional
/*        Evento evento = (Evento) session.load(Evento.class, id);
		for (Empleado emp : evento.getAsistentes()) {
			System.out.println(emp.toString());
		}*/
/*        session.getTransaction().commit();
	}
	
	private void modificaEmpleado() {
		System.out.println("Datos del empleado a modificar");
		Empleado emp = pideEmpleado();
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();

/*        Empleado empMod = (Empleado) session.load(Empleado.class, emp.getCif());
		try {
			BeanUtils.copyProperties(empMod, emp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
*/
		
/*        session.update(emp);
        session.getTransaction().commit();
	}
	
	private void pideEInsertaEmp() {
		System.out.println("Datos del nuevo empleado");
		insertaEmpleado(pideEmpleado());
	}
	
	private void insertaEmpleado(Empleado empleado) {
		logger.debug("Inserto un empleado nuevo");
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();

        session.save(empleado);

        session.getTransaction().commit();
		logger.debug("Empleado insertado");

	}

	private void pideEInsertaEvento() {
		System.out.println("Datos del nuevo evento");
		System.out.print("TÃ­tulo: ");
		String tÃ­tulo = Teclado.leeString();
		System.out.print("Fecha (dd/mm/yyyy): ");
		String fecha = Teclado.leeString();
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		Date dFecha;
		try {
			dFecha = date.parse(fecha);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		insertaEvento(tÃ­tulo, dFecha);
	}
	
	private void insertaEvento(String tÃ­tulo, Date fecha) {
		logger.debug("Inserto un evento nuevo");
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();

        Evento evento = new Evento(tÃ­tulo, fecha);
        session.save(evento);

        session.getTransaction().commit();
		logger.debug("Evento insertado");

	}
	
	private void pideEInsertaEmpleadoEvento() {
		List<Object> lEmpleadoEvento = pideEmpleadoEvento();
		logger.debug("Inserto un empleado_evento nuevo");
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
		Empleado emp = (Empleado) session.load(Empleado.class, (String)lEmpleadoEvento.get(0));
		Evento evento = (Evento) session.load(Evento.class, (Long)lEmpleadoEvento.get(1));
		evento.getAsistentes().add(emp);
		session.getTransaction().commit();
		logger.debug("Empleado_evento insertado");
	}
	
	private List<Object> pideEmpleadoEvento() {
		List<Object> lReturn = new ArrayList<Object>();
		System.out.println("Datos del nuevo empleado_evento");
		System.out.print("Cif: ");
		lReturn.add(Teclado.leeString());
		System.out.print("Id del evento: ");
		lReturn.add(new Long(Teclado.leeNumero()));
		
		return lReturn;
	}
	
/*	private void annadeEmailsAEmpleado() {
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
		Empleado emp = (Empleado) session.load(Empleado.class, "23948745F");
		emp.getDirEmails().add("yo@yo");
		emp.getDirEmails().add("yo@tu");
        session.getTransaction().commit();
        session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
		emp = (Empleado) session.load(Empleado.class, "40948574G");
		emp.getDirEmails().add("yo@yo");
		emp.getDirEmails().add("yo@tu");
        session.getTransaction().commit();
		
	}*/
/*	
	private void annadeDireccionAEmpleado() {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Empleado emp = (Empleado) session.load(Empleado.class, "23948745F");
		Direccion dir = new Direccion("Calatrava 5", 23234);
		session.save(dir);
		emp.setDireccion(dir);
		session.getTransaction().commit();
	}
*/
	public void setDao(EmpleadosDAO dao) {
		this.dao = dao;
	}
	
	private void kk() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Empleado emp = new Empleado("43675229U", "Richie", "Sambora", 58);
		session.persist(emp);
		emp.setEdad(20);
		session.getTransaction().commit();
	}

}
