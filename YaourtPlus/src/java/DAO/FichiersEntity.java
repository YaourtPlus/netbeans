/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author tbenoist
 */
@Entity
public class FichiersEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length=32, nullable=false)
	private String type;

	@Column(length=64 , nullable=false)
	private String contenu;
	
	// Problème de nom
	@ManyToMany(mappedBy="listeFichiers", fetch = FetchType.EAGER)
	List<StatutsEntity> listeStatuts = new ArrayList<>();
	
	@ManyToMany(mappedBy="listeFichiers", fetch = FetchType.EAGER)
	List<MessagesEntity> listeMessages = new ArrayList<>();
	
// Accesseurs ==================================================================
	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getContenu() {
		return contenu;
	}

// Mutateurs ===================================================================
	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
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
		if (!(object instanceof FichiersEntity)) {
			return false;
		}
		FichiersEntity other = (FichiersEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.FichiersEntity[ id=" + id + " ]";
	}
	
}
