/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import javax.ejb.Local;

/**
 *
 * @author Olivier
 */
@Local
public interface HashServiceLocal {
    public String hash(String s);
}
