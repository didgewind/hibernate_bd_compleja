package profe.hjs.model;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Administrador
 *
 */
@SuppressWarnings("serial")
public class Empleado implements java.io.Serializable {

	private String cif;
	private String nombre;
	private String apellidos;
	private int edad;
	private Direccion direccion;
	private Empleado tutor;
	private Empleado tuteloA;
	
    private Set<Nomina> nominas = new HashSet<Nomina>();
    
    private Set<String> dirEmails;

	
	public Empleado() {}

	public Empleado(String cif, String nombre, String apellidos, int edad) {
		super();
		this.cif = cif;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return cif + "\t" + nombre + "\t\t" + apellidos + "\t\t" + edad
			+ "\t" + (direccion == null ? "Sin dirección conocida" : direccion.getCalle());
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Empleado getTutor() {
		return tutor;
	}

	public void setTutor(Empleado tutor) {
		this.tutor = tutor;
	}

	public Set<Nomina> getNominas() {
		return nominas;
	}

	public void setNominas(Set<Nomina> nominas) {
		this.nominas = nominas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cif == null) ? 0 : cif.hashCode());
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
		Empleado other = (Empleado) obj;
		if (cif == null) {
			if (other.cif != null)
				return false;
		} else if (!cif.equals(other.cif))
			return false;
		return true;
	}

	public Empleado getTuteloA() {
		return tuteloA;
	}

	public void setTuteloA(Empleado tuteloA) {
		this.tuteloA = tuteloA;
	}

	public Set<String> getDirEmails() {
		return dirEmails;
	}

	public void setDirEmails(Set<String> dirEmails) {
		this.dirEmails = dirEmails;
	}
	
	
}
