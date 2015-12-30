package Enumerations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thomas
 */
public enum TypeNotifications {

    emptyNotification(0), // Default notification type
    notifFilou(1), // Notification d'ajout de Filous
    notifMessage(2), // Notification de reception de message
    notifStatut(3); // Notification de mention de statut

    private int id;

    private TypeNotifications(int id) {
        this.id = id;
    }

    // Transformation énum -> String pour la base de données
    public static TypeNotifications getType(Integer id) {
        if (id == null) {
            return null;
        }

        for (TypeNotifications n : TypeNotifications.values()) {
            if (id.equals(n.getId())) {
                return n;
            }
        }
        throw new IllegalArgumentException("Pas de type correspondant à l'id : " + id);
    }

    public int getId() {
        return id;
    }
}
