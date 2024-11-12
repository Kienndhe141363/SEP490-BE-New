package com.g96.ftms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generation_id")
    private Long getGenerationId;

    @Column(name = "generation_name")
    @NotNull
    private String generationName;

    @OneToMany(mappedBy = "generation", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Settings> generations = new ArrayList<>();
}
