package com.g96.ftms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "grade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Grade {
    @EmbeddedId
    private GradeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private Class classs;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @MapsId("markSchemeId")
    @JoinColumn(name = "mark_scheme_id")
    private MarkScheme markScheme;

    private Double grade;
    @Column(updatable = false)
    private LocalDateTime gradeDate = LocalDateTime.now();
}
