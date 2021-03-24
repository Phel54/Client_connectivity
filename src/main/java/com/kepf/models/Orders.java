package com.kepf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String side;

    @Column(nullable = false)
    private String product;

    @Column(columnDefinition = "boolean  default false")
    private Boolean is_valid;

    @Column(columnDefinition = "boolean  default true")
    private Boolean is_pending;

    @Column(columnDefinition = "boolean  default false")
    private Boolean is_success;



}
