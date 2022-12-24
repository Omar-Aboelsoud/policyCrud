package com.example.policycrud.models.entites;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "hlth_policy")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@Builder
public class Policy extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "policy_id", nullable = false)
    private String policyId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "policy", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<InsuredPerson> insuredPersons;


}
