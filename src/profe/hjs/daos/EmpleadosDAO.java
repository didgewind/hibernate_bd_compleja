/**
 * 
 */
package profe.hjs.daos;

import java.util.List;
import java.util.Set;

import profe.hjs.model.Direccion;
import profe.hjs.model.Empleado;
import profe.hjs.model.Evento;
import profe.hjs.model.Nomina;
import profe.hjs.model.Ordenador;
import profe.hjs.model.Regalo;
import profe.hjs.model.Sala;

/**
 * Interfaz DAO de acceso a la bd mazarredos
 * @author didgewind
 *
 */
public interface EmpleadosDAO {
	
	/**
	 * Devuelve el Empleado con cif cif con su tutor y a quien tutela inicializados.
	 * El tutor y a quién tutela no tienen sus tutores y a quienes tutelan inicializados.
	 * @param cif
	 * @return El Empleado con cif cif con su tutor y a quien tutela inicializados.
	 * @throws EmpDAOException Si se produce algún error de acceso a la bd
	 */
	public abstract Empleado getEmpleado(String cif)
		throws EmpDAOException;
	
	/**
	 * Devuelve el conjunto de empleados
	 * @return Cjto de empleados
	 * @throws EmpDAOException Si se produce algún error de acceso a la bd
	 */
	public abstract Set<Empleado> getAllEmpleados()
		throws EmpDAOException;
	
	public abstract void insertaEmpleado(Empleado emp)
		throws EmpDAOException;
	
	public abstract  void modificaEmpleado(Empleado emp)
		throws EmpDAOException;
	
	public abstract void eliminaEmpleado(String cif)
		throws EmpDAOException;
	
	public abstract Evento getEvento(long idEvento)
		throws EmpDAOException;
	
	/**
	 * Devuelve una lista de eventos ordenados for fecha de antiguedad
	 * @return Lista de Events
	 * @throws EmpDAOException Si se produce algún error de acceso a la bd
	 */
	public abstract List<Evento> getEventos()
		throws EmpDAOException;
	
	public abstract void insertaEvento(Evento ev)
		throws EmpDAOException;
	
	public abstract void modificaEvento(Evento ev)
		throws EmpDAOException;
	
	public abstract void eliminaEvento(long idEvento)
		throws EmpDAOException;
	
	public abstract void addEmpleado2Evento(long idEvento, Empleado emp)
		throws EmpDAOException;
	
	public abstract Nomina getNomina(long idNomina)
		throws EmpDAOException;
	
	/**
	 * Devuelve el Empleado con cif cif con su tutor, a quien tutela
	 * y a su conjunto de nóminas inicializados.
	 * El tutor y a quién tutela no tienen sus tutores y a quienes tutelan inicializados.
	 * @param cif
	 * @return El Empleado con cif cif con su tutor, a quien tutela
	 * y a su conjunto de nóminas inicializados.
	 * @throws EmpDAOException Si se produce algún error de acceso a la bd
	 */
	public abstract Empleado getEmpleadoConNominas(String cif)
		throws EmpDAOException;
	
	public abstract Direccion getDireccion(long idDireccion)
		throws EmpDAOException;
	
	public abstract Sala getSala(String idSala)
		throws EmpDAOException;
	
	public abstract List<Evento> getEventosDeSala(String idSala)
		throws EmpDAOException;
	
	public abstract Set<Regalo> getRegalos()
		throws EmpDAOException;
	
	public abstract List<Ordenador> getOrdenadores()
		throws EmpDAOException;
	
	public abstract void eliminaOrdenador(String idOrdenador)
		throws EmpDAOException;
	
	public abstract void insertaOrdenador(Ordenador ordenador)
		throws EmpDAOException;
	
	/**
	 * Devuelve una lista de regalos cuya descripción coincide con descr (al menos
	 * parte de la descripción)
	 * @param descr
	 * @return
	 * @throws EmpDAOException
	 */
	public abstract List<Regalo> getRegalosXDescr(String descr)
		throws EmpDAOException;
	
	/**
	 * Devuelve los eventos a los que va a asistir el empleado con cif cif
	 * @param cif
	 * @return
	 */
	public abstract Set<Evento> getEventosDeEmpleado(String cif);
	
}
