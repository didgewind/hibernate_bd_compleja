package profe.hjs.model;

import java.io.Serializable;

public class Direccion implements Serializable {
	
	private long id;
	
	private String calle;
	private int cp;
	
	public Direccion() {}
	
	/**
	 * 
	 * @param calle
	 * @param cp
	 */
	public Direccion(String calle, int cp) {
		super();
		this.calle = calle;
		this.cp = cp;
	}


	public long getId() {
		return id;
	}
	private void setId(long id) {
		this.id = id;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + cp;
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
		Direccion other = (Direccion) obj;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (cp != other.cp)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "" + id + "\t" + calle + "\t" + cp;
	}	

}
