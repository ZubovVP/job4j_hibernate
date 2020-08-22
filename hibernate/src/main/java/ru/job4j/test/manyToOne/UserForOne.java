package ru.job4j.test.manyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 20.08.2020.
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usersForOneToOne")
public class UserForOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemForOne item;

    public static UserForOne of(String name, ItemForOne item) {
        UserForOne user = new UserForOne();
        user.name = name;
        user.item = item;
        return user;
    }
}
