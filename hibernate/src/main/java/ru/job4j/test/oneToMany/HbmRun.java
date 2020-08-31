package ru.job4j.test.oneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 22.08.2020.
 */
public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarsForOneToMany one = CarsForOneToMany.of("Opel_1");
            CarsForOneToMany two = CarsForOneToMany.of("Opel_2");

            session.save(one);
            session.save(two);


            ModelsForOneToMany model = ModelsForOneToMany.of("Car");
            model.addCar(session.load(CarsForOneToMany.class, one.getId()));
            model.addCar(session.load(CarsForOneToMany.class, two.getId()));

            session.save(model);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

//        try {
//            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//            Session session = sf.openSession();
//            session.beginTransaction();
//
//            Iterator<ModelsForOneToMany> test = session.createQuery("from ModelsForOneToMany").list().iterator();
//            ModelsForOneToMany result = test.next();
//
//            System.out.println(result.getCars());
//
//            session.getTransaction().commit();
//            session.close();
//        }  catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
    }
}
