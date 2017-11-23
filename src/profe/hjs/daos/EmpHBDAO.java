/**
 * 
 */
package profe.hjs.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import profe.hjs.model.Direccion;
import profe.hjs.model.Empleado;
import profe.hjs.model.Evento;
import profe.hjs.model.Nomina;
import profe.hjs.model.Ordenador;
import profe.hjs.model.Regalo;
import profe.hjs.model.Sala;
import profe.hjs.util.HibernateUtil;

/**
 * @author didgewind
 *
 */
public class EmpHBDAO implements EmpleadosDAO {

	interface Constantes {
		String REGALOXDESC = "regalo.xdescr";
	}
	
	private static final Logger logger = Logger.getLogger(EmpHBDAO.class);
	
	/*
	 * Devuelve la sesión activa para ser utilizada dentro del DAO
	 */
	private Session getSession() {
		return HibernateUtil.getSession();
	}
	
	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#getEmpleado(java.lang.String)
	 */
	@Override
	public Empleado getEmpleado(String cif) throws EmpDAOException {
		Empleado eReturn = null;
		try {
			eReturn  = (Empleado) getSession().
					get(Empleado.class, cif);
		/* 
		 * Estos dos initialize son redundantes, ya que en el gestor actual accedemos a las relaciones
		 * dentro de la sesión. Es conveniente dejarlos por si usamos este dao en otro tipo de entorno.
		 */
			Hibernate.initialize(eReturn.getTuteloA());
			Hibernate.initialize(eReturn.getTutor());
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar el empleado con cif " + cif, de);
			throw new EmpDAOException(de);
		}
        return eReturn;
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#getEmpleados()
	 * En este método utilizamos HQL para devolver todos los empleados, en contraste con getRegalos(),
	 * donde utilizamos un objeto Criteria
	 */
	@Override
	public Set<Empleado> getAllEmpleados() throws EmpDAOException {
		List<Empleado> listaEmpleados = null;
		try {
			listaEmpleados = getSession().createQuery("from Empleado").list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los empleados", de);
			throw new EmpDAOException(de);
		}
        return new HashSet<Empleado>(listaEmpleados);
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#getEvento(long)
	 */
	@Override
	public Evento getEvento(long idEvento) throws EmpDAOException {
		Evento eReturn = null;
		try {
			eReturn  = (Evento) getSession().get(Evento.class, idEvento);
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar el evento con id " + idEvento, de);
			throw new EmpDAOException(de);
		}
        return eReturn;
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#getEventos()
	 */
	@Override
	public List<Evento> getEventos() throws EmpDAOException {
		List<Evento> listaEventos = null;
		try {
        	listaEventos = getSession().createQuery("from Evento order " +
        			"by fecha asc").list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los eventos", de);
			throw new EmpDAOException(de);
		}
        return listaEventos;
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#insertaEmpleado(profe.hjs.model.Empleado)
	 */
	@Override
	public void insertaEmpleado(Empleado emp) throws EmpDAOException {
		try {
			getSession().save(emp);
		} catch (HibernateException de) {
			logger.error("Error al intentar insertar el empleado " + emp, de);
			throw new EmpDAOException(de);
		}
	}

	@Override
	public Nomina getNomina(long idNomina) throws EmpDAOException {
		Nomina nReturn = null;
		try {
			nReturn  = (Nomina) getSession().
					get(Nomina.class, idNomina);
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar la nÃ³mina con id " + idNomina, de);
			throw new EmpDAOException(de);
		}
        return nReturn;
	}

	@Override
	public Empleado getEmpleadoConNominas(String cif) throws EmpDAOException {
		Empleado eReturn = null;
		try {
			eReturn  = getEmpleado(cif);
			Set<Nomina> nominas = eReturn.getNominas();
			Hibernate.initialize(nominas);
			for (Nomina nomina : nominas) {
				Hibernate.initialize(nomina);
			}
		} catch (HibernateException he) {
			logger.error("Error al inicializar las nóminas del empleado con cif " + cif, he);
			throw new EmpDAOException(he);
		}
        return eReturn;
	}

	@Override
	public Direccion getDireccion(long idDireccion) throws EmpDAOException {
		Direccion dReturn = null;
		try {
			dReturn  = (Direccion) getSession().
					get(Direccion.class, idDireccion);
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar la dirección con id " + idDireccion, de);
			throw new EmpDAOException(de);
		}
        return dReturn;
	}

	@Override
	public List<Evento> getEventosDeSala(String idSala) throws EmpDAOException {
		List<Evento> listaEventos = null;
		try {
			listaEventos = getSession().createQuery("from Evento evento where " +
    			"evento.sala = ?").setString(0, idSala).list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los eventos para la sala " + idSala, de);
			throw new EmpDAOException(de);
		}
        return listaEventos;
	}

	@Override
	public Sala getSala(String idSala) throws EmpDAOException {
		Sala sReturn = null;
		try {
			sReturn  = (Sala) getSession().
					get(Sala.class, idSala);
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar la sala con id " + idSala, de);
			throw new EmpDAOException(de);
		}
        return sReturn;
	}

	/*
	 * (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#getRegalos()
	 * En este método utilizamos un objeto Criteria para devolver todos los regalos,
	 * en contraste con getEmpleados(), donde utilizamos HQL
	 */
	@Override
	public Set<Regalo> getRegalos() throws EmpDAOException {
		List<Regalo> listaRegalos = null;
		try {
			listaRegalos = getSession().createCriteria(Regalo.class).list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los regalos", de);
			throw new EmpDAOException(de);
		}
        return new HashSet<Regalo>(listaRegalos);
	}

	private Ordenador getOrdenador(String idOrdenador) throws EmpDAOException {
		Ordenador oReturn = null;
		try {
			oReturn  = (Ordenador) getSession().
					get(Ordenador.class, idOrdenador);
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar el ordenador con id " + idOrdenador, de);
			throw new EmpDAOException(de);
		}
        return oReturn;
	}
	
	@Override
	public void eliminaOrdenador(String idOrdenador) throws EmpDAOException {
		try {
			Ordenador ordenador = getOrdenador(idOrdenador);
			if (ordenador == null) {
				// Lanzar excepciÃ³n adecuada o devolver false
			} else {
				getSession().delete(ordenador);
			}
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los regalos", de);
			throw new EmpDAOException(de);
		}
	}

	@Override
	public List<Ordenador> getOrdenadores() throws EmpDAOException {
		List<Ordenador> listaOrdenadores = null;
		try {
			listaOrdenadores = getSession().createQuery("from Ordenador order by id").list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los ordenadores", de);
			throw new EmpDAOException(de);
		}
        return listaOrdenadores;
	}

	@Override
	public void insertaOrdenador(Ordenador ordenador) throws EmpDAOException {
		try {
			getSession().save(ordenador);
		} catch (HibernateException de) {
			logger.error("Error al intentar insertar el ordenador con id" + ordenador.getId(), de);
			throw new EmpDAOException(de);
		}
	}

	@Override
	public List<Regalo> getRegalosXDescr(String descr) throws EmpDAOException {
		List<Regalo> listaRegalos = null;
		try {
			Criteria crit = getSession().createCriteria(Regalo.class);

			crit.add(Restrictions.eq(Constantes.REGALOXDESC, "%" + descr + "%"));

			crit.setMaxResults(10);

			listaRegalos = crit.list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los regalos con descr " + descr, de);
			throw new EmpDAOException(de);
		}
        return listaRegalos;
	}

	private void limpiaTutelados(String cif) {
		// Recuperar todos los empleados cuyo tutor sea cif
		Query query = getSession().createQuery("from Empleado where tutor = :ciftutor");
		query.setString("ciftutor", cif);
		List<Empleado> lEmpleadosConTutorAEliminar = query.list();
		// Modificarlos poniendo tutor a null
		for (Empleado empleado : lEmpleadosConTutorAEliminar) {
			empleado.setTutor(null);
		}
	}

	private void limpiaEventos(String cif) {
		// HQL directa para eliminar de empleado_evento las entradas cuyo cif sea cif
		Query query = getSession().createSQLQuery("DELETE FROM empleado_evento WHERE cif = ?");
		query.setString(0, cif);
		query.executeUpdate();
	}
	
	private void limpiaOrdenador(String cif) {
		// HQL directa para eliminar de ordenador_empleado las entradas cuyo cif sea cif
		Query query = getSession().createSQLQuery("DELETE FROM ordenador_empleado WHERE cif = ?");
		query.setString(0, cif);
		query.executeUpdate();
	}
	
	private void limpiaRegalo(String cif) {
		Query query = getSession().createQuery("FROM Regalo regalo WHERE regalo.para = ?");
		query.setString(0, cif);
		Regalo regalo = (Regalo) query.uniqueResult();
		if (regalo != null) {
			regalo.setPara(null);
		}
	}
	
	/*
	 * Limpia las asociaciones del empleado cif en la bd (normalmente para poder borrarlo)
	 * Las nóminas no hay que borrarlas porque tenemos un cascade all
	 */
	private void limpiaEmpleado(String cif) {
		limpiaTutelados(cif);
		limpiaEventos(cif);
		limpiaOrdenador(cif);
		limpiaRegalo(cif);
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#eliminaEmpleado(java.lang.String)
	 */
	@Override
	public void eliminaEmpleado(String cif) {
		try {
			limpiaEmpleado(cif);
			getSession().delete(getEmpleado(cif));
		} catch (HibernateException de) {
			logger.error("Error al intentar eliminar el empleado con cif " + cif, de);
			throw new EmpDAOException(de);
		}
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#insertaEvento(profe.hjs.model.Evento)
	 */
	@Override
	public void insertaEvento(Evento ev) throws EmpDAOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#modificaEmpleado(profe.hjs.model.Empleado)
	 */
	@Override
	public void modificaEmpleado(Empleado emp) throws EmpDAOException {
		try {
			getSession().update(emp);
		} catch (HibernateException de) {
			logger.error("Error al intentar modificar el empleado con cif " + emp.getCif(), de);
			throw new EmpDAOException(de);
		}
	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#modificaEvento(profe.hjs.model.Evento)
	 */
	@Override
	public void modificaEvento(Evento ev) throws EmpDAOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#addEmpleado2Evento(long, profe.hjs.model.Empleado)
	 */
	@Override
	public void addEmpleado2Evento(long idEvento, Empleado emp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see profe.hjs.daos.EmpleadosDAO#eliminaEvento(long)
	 */
	@Override
	public void eliminaEvento(long idEvento) {
		// TODO Auto-generated method stub

	}

	/*
	 * En este ejemplo vemos cómo recuperar datos de una asociación que 
	 * no es navegable
	 */
	@Override
	public Set<Evento> getEventosDeEmpleado(String cif) {
		List<Evento> listaEventos = null;
		try {
	        Query query = getSession().createQuery
	        	("select evento from Evento evento where " +
	        	":cif member of evento.asistentes");
	        query.setString("cif", cif);
	        listaEventos = query.list();
		} catch (HibernateException de) {
			logger.error("Error al intentar recuperar los eventos del empleado " 
					+ cif, de);
			throw new EmpDAOException(de);
		}
        return new HashSet<Evento>(listaEventos);
	}
}
