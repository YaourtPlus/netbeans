/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.Date;
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
		if (!(object instanceof NotificationsEntity)) {
			return false;
		}
		NotificationsEntity other = (NotificationsEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DAO.NotificationsEntity[ id=" + id + " ]";
	}
	
}
