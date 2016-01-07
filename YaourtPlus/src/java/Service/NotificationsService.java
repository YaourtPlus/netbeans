/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesEntity;
import Enumerations.TypeNotifications;

/**
 *
 * @author tbenoist
 */
public interface NotificationsService {
    
    public boolean createNotification(TypeNotifications typeNotif, PersonnesEntity notifieur, PersonnesEntity destinataire, int id);

    public String afficheData(int idUtilisateur, int idNotif);
}
