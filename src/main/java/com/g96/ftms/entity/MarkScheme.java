package com.g96.ftms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "mark_scheme")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_scheme_id")
    private Long markSchemeId;

    @Column(name = "mark_name")
    private String markName;

    @Column(name = "version")
    private String version;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "mark_weight")
    private Double markWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "markScheme",fetch = FetchType.LAZY)
    @JsonBackReference
    List<GradeSetting> gradeSettingList;

}