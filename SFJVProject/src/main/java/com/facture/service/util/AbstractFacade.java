/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facture.service.util;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author A7-E
 */

public abstract class AbstractFacade<T> {

   

    public Long generate(String beanName, String attributeName) {
        String requete = "SELECT max(item." + attributeName + ") FROM " + beanName + " item";
        List<Long> maxId = getEntityManager().createQuery(requete).getResultList();
        if (maxId == null || maxId.isEmpty() || maxId.get(0) == null) {
            return 1L;
        }
        return maxId.get(0) + 1;
    }

    public T getUniqueResult(String query) {
        List<T> list = getEntityManager().createQuery(query).getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    public List<T> getMultipleResult(String query) {
        List<T> list = getEntityManager().createQuery(query).getResultList();
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;

    }

   

    protected  abstract EntityManager getEntityManager();

    
   

}
