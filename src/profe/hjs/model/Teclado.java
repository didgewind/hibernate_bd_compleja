/**
 * 
 */
package profe.hjs.model;

import java.io.Serializable;

/**
 * @author didgewind
 *
 */
public class Teclado implements Serializable {

	private String id;
	private int teclas = 45;
	private Ordenador ordenador;
	
	/**
	 * 
	 */
	public Teclado() {
		// TODO Auto-generated constructor stub
	}

	public Teclado(Ordenador ordenador) {
		this.ordenador = ordenador;
	}
	
	public Teclado(int teclas) {
		super();
		this.teclas = teclas;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public int getTeclas() {
		return teclas;
	}

	public void setTeclas(int teclas) {
		this.teclas = teclas;
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
		Teclado other = (Teclado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "id=" + id + " - teclas=" + teclas + " - empleado de teclado= " +
		(ordenador.getEmpleado()==null ? "Teclado solito" :
			ordenador.getEmpleado().getNombre() + " " + ordenador.getEmpleado().getApellidos());
	}

	public Ordenador getOrdenador() {
		return ordenador;
	}

	private void setOrdenador(Ordenador ordenador) {
		this.ordenador = ordenador;
	}

	
}
