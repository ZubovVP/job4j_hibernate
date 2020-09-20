package ru.carSales.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 16.06.2020.
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars1")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private String mark;

    @Column(name = "year_of_issue")
    private int yearOfIssue;

    @Column(name = "type_body")
    private String typeBody;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "photos")
    private String dir_photos;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserForSales user;
}