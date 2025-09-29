package org.integrador.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("arqui-web-i2");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
