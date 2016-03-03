/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Services.elementaires.PersonnesServiceLocal;
import Services.elementaires.StatutServiceLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Thomas
 */
@Stateful
public class AfficheStatutsService implements AfficheStatutsServiceLocal {

    @EJB
    StatutServiceLocal statutService;

    @EJB
    PersonnesServiceLocal personneService;

    
    /**
     * Récupération des statuts à afficher sur la page d'accueil selon leur IMC
     * 
     * @param idUtilisateur id de la personne détentrice de la page d'accueil.
     * Cet id permet de récupérer les statuts des filous à afficher ainsi que ses propres statuts
     * 
     * @return la liste des statuts à afficher triés selon leur imc (quantité de Léger/Lourd)
     */
    @Override
    public List<StatutsEntity> afficheMurStatuts(int idUtilisateur) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);

        // Parcours des filous de l'utilisateur
        List<StatutsEntity> statutsFilous = new ArrayList<>();

        for (PersonnesEntity p : user.getListFilous()) {
            // Parcours des statuts des filous
            // /!\ Récupération des statuts dans DAO selon la date /!\
            for (StatutsEntity s : p.getStatuts(Calendar.getInstance().getTime())) {
                statutsFilous.add(s);
            } // Fin parcours statuts
        } // Fin parcours filous
        statutsFilous.addAll(user.getStatutsEmis());
        sortListe(statutsFilous);

        return statutsFilous;
    }

    
    /**
     * Récupération des statuts qu'une personne à émis pour itération à l'affichage.
     * 
     * @param idPersonne id de la personne dont on veut afficher les statuts émis
     * @return la liste des statuts émis du plus récent au plus vieux
     */
    @Override
    public List<StatutsEntity> afficheStatutsEmis(int idPersonne) {
        PersonnesEntity p = personneService.getPersonne(idPersonne);
        List<StatutsEntity> list = new ArrayList();

        // Parcours des statuts émis par la personne
        for (StatutsEntity s : p.getStatutsEmis()) {
            // On recherche les statuts que la personne n'a pas posté sur un mur
            if (s.getDestinataire().equals(p)) {
                list.add(s);
            }
        }
        // Inversion pour aller du plus récent au plus vieux
        reverse(list);
        return list;
    }

    
    /**
     * Récupération des statuts qu'une personne à reçu pour itération à l'affichage.
     * 
     * @param idPersonne id de la personne dont on veut afficher les statuts reçus
     * @return la liste des statuts reçus du plus récent au plus vieux
     */
    @Override
    public List<StatutsEntity> afficheStatutsRecus(int idPersonne) {
        PersonnesEntity p = personneService.getPersonne(idPersonne);
        List<StatutsEntity> list = new ArrayList();

        // Parcours des statuts émis par la personne
        for (StatutsEntity s : p.getStatutsRecu()) {
            // On recherche les statuts que la personne a reçu et qui ne viennent pas d'elle
            if (!s.getAuteur().equals(p)) {
                list.add(s);
            }
        }
        // Inversion pour aller du plus récent au plus vieux
        reverse(list);
        return list;
    }

    
    /**
     * Trie une liste de StatutsEntity en utilisant la méthode Compare de
     * Collections
     *
     * @param l la liste à trier
     * @return la liste triée selon l'imc
     */
    public List<StatutsEntity> sortListe(List<StatutsEntity> l) {
        Collections.sort(l, new Comparator<StatutsEntity>() {
            /**
             * Compare le statut o1 avec le statut o2
             *
             * @param o1 un statut à comparer
             * @param o2 un statut à comparer
             * @return un entier représentant le résultat de la comparaison : -1
             * si o1 est moins léger que o2 0 si ils sont égaux 1 si o1 est plus
             * léger que o2 Si un statut est plus léger qu'un autre, il sera
             * plus haut
             */
            @Override
            public int compare(StatutsEntity o1, StatutsEntity o2) {
                int o1Leger = o1.getNbLeger();
                int o1Lourd = o1.getNbLourd();
                int o2Leger = o2.getNbLeger();
                int o2Lourd = o2.getNbLourd();

                if (o1Leger - o1Lourd > o2Leger - o2Lourd) {
                    return -1;
                } else if (o1Leger - o1Lourd < o2Leger - o2Lourd) {
                    return 1;
                } else { // Egalité des différences
                    if (o1Lourd == 0 && o2Lourd == 0) {
                        return o1Leger >= o2Leger ? -1 : 1;
                    } else if (o1Lourd == 0) { // o2Lourd != 0
                        return -1;
                    } else if (o2Lourd == 0) { // o1Lourd != 0
                        return 1;
                    } else { // o1Lourd et o2Leger != 0
                        return (o1Leger / o1Lourd) >= (o2Leger / o2Lourd) ? -1 : 1;
                    }
                }
            }
        });
        return l;
    }

    /**
     * Inverse l'ordre d'une liste
     * @param l la liste à inverser
     * @return la liste inversée
     */
    public List<StatutsEntity> reverse(List<StatutsEntity> l) {
        Collections.reverse(l);
        return l;
    }
}
