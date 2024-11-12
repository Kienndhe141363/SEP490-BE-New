package com.g96.ftms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(nullable = false)
    private String subjectCode;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private String documentLink;

    @Column(nullable = false)
    private boolean status = true;

    private String descriptions;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    @JsonBackReference
    List<CurriculumSubjectRelation> curriculumSubjectRelationList;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    @JsonBackReference
    List<MarkScheme> markSchemeList;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    @JsonBackReference
    List<Schedule> schedules;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    @JsonBackReference
    List<GradeSetting> gradeSettingList;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Grade> grades;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    @JsonBackReference
    List<FeedBack> feedBackList;
}