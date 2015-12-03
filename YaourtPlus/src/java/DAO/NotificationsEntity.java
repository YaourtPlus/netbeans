/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class NotificationsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false, length=32)
	private String type;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE

	// Auteur de la notification
	@Column(nullable=false)
	@ManyToOne
	@JoinColumn(name="notifieurId")
	private PersonnesEntity notifieur;
// Relations MANY TO MANY
	
// Accesseurs ==================================================================
	
	public Integer getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public PersonnesEntity getNotifieur() {
		return notifieur;
	}

// Mutateurs ===================================================================

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setNotifieur(PersonnesEntity notifieur) {
		this.notifieur = notifieur;
	}
	
// =============================================================================

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + Objects.hashCode(this.id);
		hash = 43 * hash + Objects.hashCode(this.date);
		hash = 43 * hash + Objects.hashCode(this.type);
		hash = 43 * hash + Objects.hashCode(this.notifieur);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final NotificationsEntity other = (NotificationsEntity) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.date, other.date)) {
			return false;
		}
		if (!Objects.equals(this.type, other.type)) {
			return false;
		}
		if (!Objects.equals(this.notifieur, other.notifieur)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NotificationsEntity{" + "id=" + id + ", date=" + date + ", type=" + type + ", notifieur=" + notifieur + '}';
	}


}
