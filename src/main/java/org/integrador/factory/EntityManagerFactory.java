package org.integrador.factory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
    private static final javax.persistence.EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("arqui-web-i2");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
