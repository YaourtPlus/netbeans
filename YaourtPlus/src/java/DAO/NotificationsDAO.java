/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tbenoist
 */
public interface NotificationsDAO {

// Transaction de base =========================================================
    public void save(NotificationsEntity n);

    public void update(NotificationsEntity n);

    public void delete(NotificationsEntity n);
// Transaction custom ==========================================================
// Transaction read-only =======================================================

    public NotificationsEntity find(int id);

    public List<NotificationsEntity> findAll();

    public List<NotificationsEntity> findByNotifieur(int notifieurId);

    public List<NotificationsEntity> findByDate(Date date);

    public List<NotificationsEntity> findByDestinataire(int destinataireId);
}
