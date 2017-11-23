/**
 * 
 */
package profe.hjs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author didgewind
 *
 */
public class Evento implements Serializable {
	
	private long id;
	
	private String title;
	private Date fecha;
	
	private Set<Empleado> asistentes;
	private Sala sala;
	
	public Evento() {}

	/**
	 * 
	 * @param title
	 * @param fecha
	 */
	public Evento(String title, Date fecha) {
		super();
		this.title = title;
		this.fecha = fecha;
	}

	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return title;
	}

	public void setTitulo(String title) {
		this.title = title;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "" + id + "\t" + title + "\t" + fecha + "\t" + 
			(sala==null ? "Sin sala asignada" : sala.getNombre());
	}

	public Set<Empleado> getAsistentes() {
		return asistentes;
	}

	public void setAsistentes(Set<Empleado> asistentes) {
		this.asistentes = asistentes;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Evento other = (Evento) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	

}
