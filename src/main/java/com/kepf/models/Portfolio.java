package com.kepf.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name = "portfolio")
@Table(name = "portfolio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String product;
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

}
