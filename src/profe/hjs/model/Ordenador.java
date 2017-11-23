/**
 * 
 */
package profe.hjs.model;

import java.io.Serializable;

/**
 * @author didgewind
 *
 */
public class Ordenador implements Serializable {

	private String id;
	private Empleado empleado;
	private Teclado teclado;
	
	/**
	 * 
	 */
	public Ordenador() {
		// TODO Auto-generated constructor stub
	}

	public Ordenador(String id, Empleado empleado) {
		super();
		this.id = id;
		this.empleado = empleado;
		this.teclado = new Teclado(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "id=" + id + " - empleado=" +
			(empleado==null ? "Ordenador solito" :
			empleado.getNombre() + " " + empleado.getApellidos()) +
			"\r\n\tteclado: " + teclado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Ordenador other = (Ordenador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Teclado getTeclado() {
		return teclado;
	}

	public void setTeclado(Teclado teclado) {
		this.teclado = teclado;
	}

}
