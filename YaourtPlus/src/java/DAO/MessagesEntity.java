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
import javax.persistence.Temporal;

/**
 *
 * @author tbenoist
 */
@Entity
public class MessagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length=1024, nullable=false)
	private String texte;

	@Column(length=64)
	private String image;	

	@Column(length=64)
	private String video;
	
	@Column(nullable=true)
	private Date date;
	
// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE
	
	// Auteur du message
	@ManyToOne
	@JoinColumn(name="auteurMessage")
	private PersonnesEntity emetteur;
	
// Relations MANY TO MANY
	
	// Liste des fichiers liés au message
	@JoinTable(
			name="Messages_Fichiers",
			joinColumns=@JoinColumn(name="FichiersID"),
			inverseJoinColumns=@JoinColumn(name="MessagesID")
	)
	@ManyToMany(fetch = FetchType.EAGER)
	List<FichiersEntity> listeFichiers = new ArrayList<>();
// Accesseurs ==================================================================			
	public Integer getId() {
		return id;
	}

	public String getTexte() {
		return texte;
	}

	public String getImage() {
		return image;
	}

	public String getVideo() {
		return video;
	}

	public Date getDate() {
		return date;
	}

// Mutateurs ===================================================================
	public void setId(Integer id) {
		this.id = id;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public void setDate(Date date) {
		this.date = date;
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
		if (!(object instanceof MessagesEntity)) {
			return false;
		}
		MessagesEntity other = (MessagesEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.MessagesEntity[ id=" + id + " ]";
	}
	
}
