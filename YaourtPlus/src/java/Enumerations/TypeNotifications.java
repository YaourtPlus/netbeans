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
    notifMessage(2), // Notification de reception de 
    notifLeger(3), // Notification de léger sur un statut
    notifLourd(4), // Notification de lourd sur un statut
    notifCommentaire(5), // Notifications de commentaire sur un statut
    notifStatut(6); // Notifications de commentaire sur un statut
    
    // Id stocké dans la BD
    private final int id;

    // Constructeur
    private TypeNotifications(int id) {
        this.id = id;
    }

    /**
     * Transformation d'un id en valeur de l'énumération
     * @param id valeur à transformer
     * @return null, notifFilou, notifMessage, notifStatut
     * @throws IllegalArgumentException si aucune valeur ne correspond
     */
    public static TypeNotifications getType(Integer id) {
        if (id == null) {
            return null;
        }

        // Parcours des valeurs
        for (TypeNotifications n : TypeNotifications.values()) {
            if (id.equals(n.getId())) {
                return n;
            }
        }
        throw new IllegalArgumentException("Pas de type correspondant à l'id : " + id);
    }

    /**
     * @return l'id correspndant au type de la notification
     */
    public int getId() {
        return id;
    }
}
