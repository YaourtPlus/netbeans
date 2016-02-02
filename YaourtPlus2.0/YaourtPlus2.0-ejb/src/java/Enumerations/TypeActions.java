/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enumerations;

/**
 *
 * @author Thomas
 */
public enum TypeActions {

    noAction(0), // Pas d'action sur le statut
    leger(1), // Leger sur le statut
    lourd(2); // Lourd sur le statut
    
    // Id stocké dans la BD
    private final int id;

    // Constructeur
    private TypeActions(int id) {
        this.id = id;
    }

    /**
     * Transformation d'un id en valeur de l'énumération
     * @param id valeur à transformer
     * @return null, noAction, leger, lourd     
     * @throws IllegalArgumentException si aucune valeur ne correspond
     */
    public static TypeActions getType(Integer id) {
        if (id == null) {
            return null;
        }

        // Parcours des valeurs
        for (TypeActions n : TypeActions.values()) {
            if (id.equals(n.getId())) {
                return n;
            }
        }
        throw new IllegalArgumentException("Pas de type correspondant à l'id : " + id);
    }

    /**
     * @return l'id correspndant au type de l'action
     */
    public int getId() {
        return id;
    }
}
