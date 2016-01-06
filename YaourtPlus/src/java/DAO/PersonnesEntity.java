/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private Integer age;

    @Column(nullable = false, length = 64)
    private String mail;

    @Column(nullable = false, length = 32)
    private String login;

    @Column(nullable = false, length = 32)
    private String password;

    @Column(nullable = true)
    private Date dateInscription;

    // Date de dernière connexion
    @Column
    private Date dateConnexion;

    // Quantité de notifications non lues
    // Integer => permet de stocker null dans la BD
    @Column
    private Integer notifNonLues;

// Relations ONE TO ONE
    // Imc associé à la personne
    // Relation unidirectionnelle
    @JoinColumn(name = "IMCId")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private IMCEntity imc;

// Relations ONE TO MANY	
    // Liste des notifications émises par une personne
    @OneToMany(mappedBy = "notifieur", fetch = FetchType.EAGER)
    private List<NotificationsEntity> notificationsEmises = new ArrayList<>();

    // Liste des statuts émis par une personne
    @OneToMany(mappedBy = "auteur", fetch = FetchType.EAGER)
    private List<StatutsEntity> statutsEmis = new ArrayList<>();

    // Liste des statuts dont la personne est le destinataire
    @OneToMany(mappedBy = "destinataire", fetch = FetchType.EAGER)
    private List<StatutsEntity> statutsRecu = new ArrayList<>();

    // Liste des messages émis par une personne
    @OneToMany(mappedBy = "emetteur", fetch = FetchType.EAGER)
    private List<MessagesEntity> messagesEmis = new ArrayList<>();

    // Liste des statuts sur lesquel l'utilisateur est intervenu
    @OneToMany(mappedBy = "personne", fetch = FetchType.EAGER)
    private List<PersonnesStatutsEntity> statutsActeurs = new ArrayList<>();

// Relations MANY TO ONE
// Relations MANY TO MANY
    // Liste des amis de la personne
    // Problème de fetch eager quelque part dans la liste filous
    @JoinTable(
            name = "Personnes_Personnes",
            joinColumns = @JoinColumn(name = "FilousId"),
            inverseJoinColumns = @JoinColumn(name = "PersonneId")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PersonnesEntity> listFilous = new ArrayList<>();

    // Liste des personnes dont il est ami
    @ManyToMany(mappedBy = "listFilous", fetch = FetchType.EAGER)
    private List<PersonnesEntity> listFilousDe = new ArrayList<>();

    // Liste des messages reçues par la personne
    @JoinTable(
            name = "Personnes_Messages",
            joinColumns = @JoinColumn(name = "MessageId"),
            inverseJoinColumns = @JoinColumn(name = "DestinataireId")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<MessagesEntity> messagesRecus = new ArrayList<>();

    // Liste des notifications reçues par la personne
    @ManyToMany(mappedBy = "listeDestinataires", fetch = FetchType.EAGER)
    private List<NotificationsEntity> notificationRecues = new ArrayList<>();

// Constructeur=================================================================
    public PersonnesEntity() {
        this.nom = "";
        this.prenom = "";
        this.mail = "";
        this.login = "";
        this.password = "";
        this.notifNonLues = 0;
    }

    public PersonnesEntity(String nom, String prenom, Integer age, String mail, String login, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.notifNonLues = 0;
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

    public Integer getAge() {
        return age;
    }

    public int getNotifNonLues() {
        if (notifNonLues == null) {
            return 0;
        } else {
            return notifNonLues;
        }
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

    public List<StatutsEntity> getStatutsEmis() {
        return statutsEmis;
    }

    /**
     * Récupère les statuts qui ont été postés pendant une certaine période La
     * période est une durée de 2 semaines à partir d'aujourd'hui
     *
     * @param today la date actuelle
     * @return la liste des statuts postés pendant la période définie
     */
    public List<StatutsEntity> getStatuts(Date today) {
        ArrayList<StatutsEntity> list = new ArrayList<>();
        for (StatutsEntity s : statutsEmis) {
            // 1210000000 => 2 semaines en millisecondes
            // Test de la date à laquelle a été posté le statut
            if (s.getDate().after(new Date(today.getTime() - 1210000000))) {
                list.add(s);
            }
        }
        for (StatutsEntity s : statutsRecu) {
            if (s.getDate().after(new Date(today.getTime() - 1210000000))) {
                list.add(s);
            }
        }
        return list;
    }

    public List<MessagesEntity> getMessagesEmis() {
        return messagesEmis;
    }

    public List<PersonnesEntity> getListFilous() {
        return listFilous;
    }

    public List<MessagesEntity> getMessagesRecus() {
        return messagesRecus;
    }

    public List<NotificationsEntity> getNotificationRecues() {
        return notificationRecues;
    }

    public List<PersonnesEntity> getListFilousDe() {
        return listFilousDe;
    }

    public List<PersonnesStatutsEntity> getStatutActeurs() {
        return statutsActeurs;
    }

    public List<StatutsEntity> getStatutsRecu() {
        return statutsRecu;
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

    public void setAge(Integer age) {
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

    public void setStatutsEmis(List<StatutsEntity> statuts) {
        this.statutsEmis = statuts;
    }

    public void setMessagesEmis(List<MessagesEntity> messagesEmis) {
        this.messagesEmis = messagesEmis;
    }

    public void setListFilous(List<PersonnesEntity> listFilous) {
        this.listFilous = listFilous;
    }

    public void setMessagesRecus(List<MessagesEntity> messagesRecus) {
        this.messagesRecus = messagesRecus;
    }

    public void setNotificationRecues(List<NotificationsEntity> notificationRecues) {
        this.notificationRecues = notificationRecues;
    }

    public void setListFilousDe(List<PersonnesEntity> listFilousDe) {
        this.listFilousDe = listFilousDe;
    }

    public void setNotifNonLues(int notifNonLues) {
        this.notifNonLues = notifNonLues;
    }

    public void setStatutsActeurs(List<PersonnesStatutsEntity> statutsActeurs) {
        this.statutsActeurs = statutsActeurs;
    }

    public void setStatutsRecu(List<StatutsEntity> statutsRecu) {
        this.statutsRecu = statutsRecu;
    }

// Gestion du nombre de notifications non lues =================================
    // Incrémentation des notifications
    public void addNotif() {
        if (notifNonLues != null) {
            notifNonLues++;
        } else {
            notifNonLues = 1;
        }
    }

    // Remise à zéro du nomre de notification
    public void resetNotif() {
        notifNonLues = 0;
    }

// Gestion de filous ===========================================================
    /**
     * Ajoute un nouvel ami à la personne
     *
     * @param filous : le nouveau filou à ajouter
     * @return true si le filou a été ajouté, false sinon (il est déjà présent
     * dans la liste)
     */
    public boolean ajoutFilous(PersonnesEntity filous) {
        if (!listFilous.contains(filous)) { // Gestion des doublons
            listFilous.add(filous);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ajoute une personne ayant la personne courante en ami
     *
     * @param filous : la personne qui à ajouté
     * @return true si la personne courante à été ajoutée
     */
    public boolean ajoutFilousDe(PersonnesEntity filous) {
        if (!listFilousDe.contains(filous)) { // Gestion des doublons
            listFilousDe.add(filous);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Supprime un ami de la personne
     *
     * @param filous : le filou à supprimer
     * @return true si l'ami a été supprimé, false sinon (il est déjà présent
     * dans la liste)
     */
    public boolean suppressionFilous(PersonnesEntity filous) {
        if (listFilous.contains(filous)) { // ,p, existence
            listFilous.remove((PersonnesEntity) filous);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Supprime une personne ayant supprimée la personne courante de ses amis
     *
     * @param filous personne ayant supprimé la personne courante
     * @return true si la personne a été supprimée, false sinon (non existence)
     */
    public boolean suppressionFilousDe(PersonnesEntity filous) {
        if (listFilousDe.contains(filous)) { // non existence
            listFilousDe.remove((PersonnesEntity) filous);
            return true;
        } else {
            return false;
        }
    }
// Gestion de statuts ==========================================================

    /**
     * Ajoute un statut dans la liste des statuts postés
     *
     * @param s le nouveau statut
     * @return true si le statut est ajouté
     */
    public boolean ajoutStatutEmis(StatutsEntity s) {
        return statutsEmis.add(s);
    }

    /**
     * Ajoute un statut dans la liste des statuts postés
     *
     * @param s le nouveau statut
     * @return true si le statut est ajouté
     */
    public boolean ajoutStatutRecu(StatutsEntity s) {
        return statutsRecu.add(s);
    }

    /**
     * Ajoute une PersonnesStatutsEntity à la liste des statuts sur lesquels
     * l'utilisateur a effectué une action
     *
     * @param ps l'instance correspondant à l'action de l'utilisateur
     * @return true si l'instance à été ajoutée, false sinon (doublon)
     */
    public boolean addPersonnesStatuts(PersonnesStatutsEntity ps) {
        if (statutsActeurs.contains(ps)) { // Gestion des doublons
            return false;
        }
        return statutsActeurs.add(ps);

    }

    /**
     * Supprime une PersonnesStatutsEntity de la liste des statuts sur lesquels
     * l'utilisateur a effectué une action
     *
     * @param ps l'instance à supprimer
     * @return true si l'instance à été supprimée, false sinon (non existence)
     */
    public boolean removePersonnesStatuts(PersonnesStatutsEntity ps) {
        if (!statutsActeurs.contains(ps)) { // non existence
            return false;
        }
        return statutsActeurs.remove(ps);
    }

    /**
     * Retourne l'action qu'a fait l'utilisateur sur un statut en particulier
     *
     * @param s le statut dont on veut connaitre l'action
     * @return noAction, leger, lourd
     */
    public TypeActions getAction(StatutsEntity s) {
        for (PersonnesStatutsEntity ps : statutsActeurs) {
            if (ps.getStatut().equals(s)) {
                return ps.getTypeAction();
            }
        }
        return TypeActions.noAction;
    }
// Gestion des notifications ===================================================

    /**
     * Ajout de reception de notification
     *
     * @param notification Notification reçu
     * @return true si la notification a bien été ajoutée, false sinon
     */
    public boolean ajoutNotificationsRecu(NotificationsEntity notification) {
        return notificationRecues.add(notification);
    }

    /**
     * Ajout d'envoi de notification
     *
     * @param notification Notification envoyée
     * @return true si la notification a bien été ajoutée, false sinon
     */
    public boolean ajoutNotificationEmise(NotificationsEntity notification) {
        return notificationsEmises.add(notification);
    }
// =============================================================================

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.prenom);
        if (this.age != null) {
            hash = 47 * hash + this.age;
        }
        hash = 47 * hash + Objects.hashCode(this.mail);
        hash = 47 * hash + Objects.hashCode(this.login);
        hash = 47 * hash + Objects.hashCode(this.password);
        hash = 47 * hash + Objects.hashCode(this.dateInscription);
        hash = 47 * hash + Objects.hashCode(this.dateConnexion);
        hash = 47 * hash + Objects.hashCode(this.imc);
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
        return true;
    }

    /**
     * Affichage des amis de la personne courante
     *
     * @return les noms et prénoms des amis
     */
    public String afficheAmi() {
        String s = "";
        for (PersonnesEntity p : listFilous) {
            s += "{" + p.getNom() + " " + p.getPrenom() + "}";
        }
        return s;
    }

    @Override
    public String toString() {
        return "PersonnesEntity{" + "id=" + id + ", nom=" + nom + ", prenom="
                + prenom + ", age=" + age + ", mail=" + mail + ", imc=" + imc
                + " " + afficheAmi() + '}';
    }

}
