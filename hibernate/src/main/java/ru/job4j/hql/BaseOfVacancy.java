package ru.job4j.hql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 31.08.2020.
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bases")
public class BaseOfVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> listOfVacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        this.listOfVacancies.add(vacancy);
    }

    public static BaseOfVacancy of() {
        BaseOfVacancy base = new BaseOfVacancy();
        return base;
    }

}
