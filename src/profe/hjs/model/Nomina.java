/**
 * 
 */
package profe.hjs.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author didgewind
 *
 */
public class Nomina implements Serializable {

	private long id;
	private Date fecha;
	private float bruto;
	
	private Empleado empleado;
	
	/**
	 * 
	 */
	public Nomina() {
		// TODO Auto-generated constructor stub
	}

	public Nomina(Date fecha, float bruto, Empleado empleado) {
		super();
		this.fecha = fecha;
		this.bruto = bruto;
		this.empleado = empleado;
	}

	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getBruto() {
		return bruto;
	}

	public void setBruto(float bruto) {
		this.bruto = bruto;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "id=" + id + " - fecha=" + fecha + " - bruto=" + bruto
				+ " - empleado=" + empleado.getNombre() + " " + empleado.getApellidos();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nomina other = (Nomina) obj;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		return true;
	}

	
}
