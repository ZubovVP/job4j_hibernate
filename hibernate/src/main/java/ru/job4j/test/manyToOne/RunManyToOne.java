package ru.job4j.test.manyToOne;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 21.08.2020.
 */
public class RunManyToOne {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            ItemForOne role = create(ItemForOne.of("Item"), sf);
            create(UserForOne.of("Zubov", role), sf);
            for (UserForOne user : findAll(UserForOne.class, sf)) {
                System.out.println(user.getName() + " " + user.getItem().getName());
                delete(user, sf);
                delete(user.getItem(), sf);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static <T> List<T> delete(T cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(cl);
        session.getTransaction().commit();
        session.close();
        return null;
    }
}
