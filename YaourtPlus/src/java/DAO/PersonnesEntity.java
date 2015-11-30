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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class PersonnesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false, length=32)
	private String nom;
	
	@Column(nullable=false, length=32)
	private String prenom;
	
	@Column
	private int age;
	
	@Column(nullable=false, length=64)
	private String mail;
	
	@Column(nullable=false, length=32)
	private String login;
	
	@Column(nullable=false, length=32)
	private String password;
	
	@Column(nullable=false)
	private Date dateInscription;
	
	@Column
	private Date dateConnexion;
	
// Relations ONE TO ONE
	
	// Imc associé à la personne
	@OneToOne
	@JoinColumn(name="IMCId")
	private IMCEntity imc;
	
// Relations ONE TO MANY	
	// Liste des notifications émises par une personne
	@OneToMany(mappedBy="notifieur")
	private List<NotificationsEntity> notificationsEmises = new ArrayList();
 	
	// Liste des statuts émis par une personne
	@OneToMany(mappedBy="auteur")
	private List<StatutsEntity> status = new ArrayList();
	
	// Liste des messages émis par une personne
	@OneToMany(mappedBy="emetteur")
	private List<MessagesEntity> messagesEmis = new ArrayList();
	
// Relations MANY TO ONE
	
// Relations MANY TO MANY
	// Gestion du lien ManyToMany sur lui même?
	// Liste des amis de la personne
	@JoinTable(
			name="Personnes_Personnes",
			joinColumns=@JoinColumn(name="FilousId"),
			inverseJoinColumns=@JoinColumn(name="PersonneId")
	)
	@ManyToMany(mappedBy="listFilous")
	private List<PersonnesEntity> listFilous = new ArrayList();

	// Liste des notifications reçu par la personne
	@JoinTable(
			name="Personnes_Messages",
			joinColumns=@JoinColumn(name="MessageId"),
			inverseJoinColumns=@JoinColumn(name="DestinataireId")
	)
	@ManyToMany
	private List<NotificationsEntity> messagesRecus = new ArrayList();
		
	// Liste des messages recu par la personne
	@JoinTable(
			name="Personnes_Notifications",
			joinColumns=@JoinColumn(name="NotificationsId"),
			inverseJoinColumns=@JoinColumn(name="DestinataireId")
	)
	@ManyToMany
	private List<MessagesEntity> notificationRecues = new ArrayList();
	
	
// Accesseurs ==================================================================
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
		if (!(object instanceof PersonnesEntity)) {
			return false;
		}
		PersonnesEntity other = (PersonnesEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.PersonnesEntity[ id=" + id + " ]";
	}
	
}
