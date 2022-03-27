package com.ariel.BooksApi.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_description")
    private String bookDescription;

    @ManyToOne
    private Users author;

    @Column(name = "book_publisher")
    private String bookPublisher;

    @Column(name = "book_price")
    private Double bookPrice;

    @Column(name = "state")
    private boolean state;

    public boolean isEmpty(){
        return this.bookName == null || this.bookDescription == null || this.bookPrice == null || this.bookPublisher == null;
    }
}
