package com.kepf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;

@Column(nullable = false, length = 50)
private String first_name;
@Column(nullable = false, length = 50)
private String last_name;
@Column(nullable = false, unique = true)
@NotBlank(message = "email can not be empty")
@Email(message = "provide a valid email")
 private String email;

@Column(nullable = false, length = 255)
@Size(min = 8, message = "password must be at least 8  characters long")
private String password;
@Column(columnDefinition = " DOUBLE PRECISION default 0.0 ")
private Double account_balance;

 @OneToMany(mappedBy = "customer")
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonIgnore
 private List<Orders> orders;

 @OneToMany(mappedBy = "customer")
 @ToString.Exclude
 @EqualsAndHashCode.Exclude
 @JsonIgnore
 private List<Portfolio> portfolios;

 @CreationTimestamp
 private LocalDateTime created_at;
 @UpdateTimestamp
 private LocalDateTime updated_at;


// public void setAccount_balance(double account_balance) {
// this.account_balance= account_balance<=0?(this.account_balance+0):(this.account_balance +account_balance);
//
// }
}
