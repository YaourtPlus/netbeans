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
    
    private final int id;

    private TypeActions(int id) {
        this.id = id;
    }

    // Transformation énum -> String pour la base de données
    public static TypeActions getType(Integer id) {
        if (id == null) {
            return null;
        }

        for (TypeActions n : TypeActions.values()) {
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
