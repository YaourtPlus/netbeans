/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Column(nullable = false, length = 32)
    private String nom;

    @Column(nullable = false, length = 32)
    private String prenom;

    @Column
    private int age;

    @Column(nullable = false, length = 64)
    private String mail;

    @Column(nullable = false, length = 32)
    private String login;

    @Column(nullable = false, length = 32)
    private String password;

    @Column(nullable = true)
    private Date dateInscription;

    @Column
    private Date dateConnexion;

// Relations ONE TO ONE
    // Imc associé à la personne
    @JoinColumn(name = "IMCId")
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private IMCEntity imc;

// Relations ONE TO MANY	
    // Liste des notifications émises par une personne
    @OneToMany(mappedBy = "notifieur")
    private List<NotificationsEntity> notificationsEmises = new ArrayList();

    // Liste des statuts émis par une personne
    @OneToMany(mappedBy = "auteur")
    private List<StatutsEntity> status = new ArrayList();

    // Liste des messages émis par une personne
    @OneToMany(mappedBy = "emetteur")
    private List<MessagesEntity> messagesEmis = new ArrayList();

// Relations MANY TO ONE
// Relations MANY TO MANY
    // Liste des amis de la personne
    @JoinTable(
            name = "Personnes_Personnes",
            joinColumns = @JoinColumn(name = "FilousId"),
            inverseJoinColumns = @JoinColumn(name = "PersonneId")
    )
    @ManyToMany
    private List<PersonnesEntity> listFilous = new ArrayList();
	
    @ManyToMany(mappedBy = "listFilous")
    private List<PersonnesEntity> listFilous2 = new ArrayList();

    // Liste des notifications reçu par la personne
    @JoinTable(
            name = "Personnes_Messages",
            joinColumns = @JoinColumn(name = "MessageId"),
            inverseJoinColumns = @JoinColumn(name = "DestinataireId")
    )
    @ManyToMany
    private List<NotificationsEntity> messagesRecus = new ArrayList();

    // Liste des messages recu par la personne
    @JoinTable(
            name = "Personnes_Notifications",
            joinColumns = @JoinColumn(name = "NotificationsId"),
            inverseJoinColumns = @JoinColumn(name = "DestinataireId")
    )
    @ManyToMany
    private List<MessagesEntity> notificationRecues = new ArrayList();

// Constructeur=================================================================

    public PersonnesEntity() {
        this.nom = "";
        this.prenom = "";
        this.mail = "";
        this.login = "";
        this.password = "";
    }
    
    
    public PersonnesEntity(String nom, String prenom, int age, String mail, String login, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.login = login;
        this.password = password;
    }

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public Date getDateConnexion() {
        return dateConnexion;
    }

    public IMCEntity getImc() {
        return imc;
    }

    public List<NotificationsEntity> getNotificationsEmises() {
        return notificationsEmises;
    }

    public List<StatutsEntity> getStatus() {
        return status;
    }

    public List<MessagesEntity> getMessagesEmis() {
        return messagesEmis;
    }

    public List<PersonnesEntity> getListFilous() {
        return listFilous;
    }

    public List<NotificationsEntity> getMessagesRecus() {
        return messagesRecus;
    }

    public List<MessagesEntity> getNotificationRecues() {
        return notificationRecues;
    }

    public List<PersonnesEntity> getListFilous2() {
        return listFilous2;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setDateConnexion(Date dateConnexion) {
        this.dateConnexion = dateConnexion;
    }

    public void setImc(IMCEntity imc) {
        this.imc = imc;
    }

    public void setNotificationsEmises(List<NotificationsEntity> notificationsEmises) {
        this.notificationsEmises = notificationsEmises;
    }

    public void setStatus(List<StatutsEntity> status) {
        this.status = status;
    }

    public void setMessagesEmis(List<MessagesEntity> messagesEmis) {
        this.messagesEmis = messagesEmis;
    }

    public void setListFilous(List<PersonnesEntity> listFilous) {
        this.listFilous = listFilous;
    }

    public void setMessagesRecus(List<NotificationsEntity> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }

    public void setNotificationRecues(List<MessagesEntity> notificationRecues) {
        this.notificationRecues = notificationRecues;
    }

    public void setListFilous2(List<PersonnesEntity> listFilous2) {
        this.listFilous2 = listFilous2;
    }

// =============================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.prenom);
        hash = 47 * hash + this.age;
        hash = 47 * hash + Objects.hashCode(this.mail);
        hash = 47 * hash + Objects.hashCode(this.login);
        hash = 47 * hash + Objects.hashCode(this.password);
        hash = 47 * hash + Objects.hashCode(this.dateInscription);
        hash = 47 * hash + Objects.hashCode(this.dateConnexion);
        hash = 47 * hash + Objects.hashCode(this.imc);
        hash = 47 * hash + Objects.hashCode(this.notificationsEmises);
        hash = 47 * hash + Objects.hashCode(this.status);
        hash = 47 * hash + Objects.hashCode(this.messagesEmis);
        hash = 47 * hash + Objects.hashCode(this.listFilous);
        hash = 47 * hash + Objects.hashCode(this.messagesRecus);
        hash = 47 * hash + Objects.hashCode(this.notificationRecues);
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
        final PersonnesEntity other = (PersonnesEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.dateInscription, other.dateInscription)) {
            return false;
        }
        if (!Objects.equals(this.dateConnexion, other.dateConnexion)) {
            return false;
        }
        if (!Objects.equals(this.imc, other.imc)) {
            return false;
        }
        if (!Objects.equals(this.notificationsEmises, other.notificationsEmises)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.messagesEmis, other.messagesEmis)) {
            return false;
        }
        if (!Objects.equals(this.listFilous, other.listFilous)) {
            return false;
        }
        if (!Objects.equals(this.messagesRecus, other.messagesRecus)) {
            return false;
        }
        if (!Objects.equals(this.notificationRecues, other.notificationRecues)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonnesEntity{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", mail=" + mail + ", login=" + login + ", password=" + password + ", dateInscription=" + dateInscription + ", dateConnexion=" + dateConnexion + ", imc=" + imc + ", notificationsEmises=" + notificationsEmises + ", status=" + status + ", messagesEmis=" + messagesEmis + ", listFilous=" + listFilous + ", messagesRecus=" + messagesRecus + ", notificationRecues=" + notificationRecues + '}';
    }

}
