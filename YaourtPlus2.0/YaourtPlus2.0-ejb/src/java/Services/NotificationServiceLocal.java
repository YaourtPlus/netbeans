/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.NotificationsEntity;
import Entities.PersonnesEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Olivier
 */
@Local
public interface NotificationServiceLocal {
    public boolean createNotificationCommentaire(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut);
    public boolean createNotificationFilou(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire);
    public boolean createNotificationLeger(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut);
    public boolean createNotificationLourd(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut);
    public boolean createNotificationMessage(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idMessage);
    public boolean createNotificationStatut(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut);
    
    public List<NotificationsEntity> getNotifs(int utilisateurId);
}
