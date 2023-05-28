package com.humancloud.Employeemanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private  String mobile;

    private String roles;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    private List<Leaves> leaves;
    @OneToOne
    @JoinColumn(name = "rmId")
    private Employee reportingManager;


}
