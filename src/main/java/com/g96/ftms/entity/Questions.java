package com.g96.ftms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.g96.ftms.util.constants.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "question_text")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", columnDefinition = "ENUM('rating','yes_no','text')")
    private QuestionType questionType;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @JsonBackReference
    List<FeedbackAnswer> feedbackAnswerList;
}
