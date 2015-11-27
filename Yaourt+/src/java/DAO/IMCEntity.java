/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class IMCEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, precision=5, scale=3)
	private double valeur;
	
	@Column(nullable=false)
	private int QteLourd;
	
	@Column(nullable=false)
	private int QteLeger;
	
	@OneToOne(mappedBy="imc")
	private PersonnesEntity personne;

// Accesseurs ==================================================================
	public Integer getId() {
		return id;
	}

	public double getValeur() {
		return valeur;
	}

	public int getQteLourd() {
		return QteLourd;
	}

	public int getQteLeger() {
		return QteLeger;
	}

// Mutateurs ===================================================================
	public void setId(Integer id) {
		this.id = id;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public void setQteLourd(int QteLourd) {
		this.QteLourd = QteLourd;
	}

	public void setQteLeger(int QteLeger) {
		this.QteLeger = QteLeger;
	}

// =============================================================================
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof IMCEntity)) {
			return false;
		}
		IMCEntity other = (IMCEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.IMCEntity[ id=" + id + " ]";
	}
	
}
