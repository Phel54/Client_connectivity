package com.kepf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

@Column(columnDefinition = " boolean default true ")
private Boolean is_active;

 @OneToMany(mappedBy = "customer")
 @JsonIgnore
 private List<Orders> orders;

 public void setAccount_balance(double account_balance) {
 this.account_balance= account_balance<=0?(this.account_balance+0):(this.account_balance +account_balance);

 }
}
