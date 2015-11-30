/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class StatutsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=1024)
	private String texte;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false)
	private Integer nbLeger;
	
	@Column(nullable=false)
	private Integer nbLourd;
	
// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE
	
	// Auteur du statut
	@ManyToOne
	@JoinColumn(name="auteurId")
	private PersonnesEntity auteur;
	
// Relations MANY TO MANY
	
	// Liste des fichiers liés au statut
	@JoinTable(
			name="Statuts_Fichiers",
			joinColumns=@JoinColumn(name="FichiersID"),
			inverseJoinColumns=@JoinColumn(name="StatutsID")
	)
	@ManyToMany(fetch = FetchType.EAGER)
	List<FichiersEntity> listeFichiers = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof StatutsEntity)) {
			return false;
		}
		StatutsEntity other = (StatutsEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.StatusEntity[ id=" + id + " ]";
	}
	
}
