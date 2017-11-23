/**
 * 
 */
package profe.hjs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * @author didgewind
 *
 */
@Entity
@Table(name="regalos")
@NamedQuery(name="regalo.xdescr",
            query="select r from Regalo r where r.descr like ? order by descr")
public class Regalo implements Serializable {

	private long id;
	private String descr;
	private Empleado para;
	
	/**
	 * 
	 */
	public Regalo() {
		// TODO Auto-generated constructor stub
	}

	public Regalo(String descr, Empleado para) {
		super();
		this.descr = descr;
		this.para = para;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="para", unique=true)
	public Empleado getPara() {
		return para;
	}

    public void setPara(Empleado para) {
		this.para = para;
	}

	@Override
	public String toString() {
		return "id=" + id + " - descr=" + descr + " - para=" + 
			para.getNombre() + " " + para.getApellidos();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Regalo other = (Regalo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
