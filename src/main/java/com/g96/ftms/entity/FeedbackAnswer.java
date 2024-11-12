package com.g96.ftms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackAnswer {
    @EmbeddedId
    private FeedbackAnswerRelationId id;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Questions question;

    @ManyToOne
    @MapsId("feedbackId")
    @JoinColumn(name = "feedback_id")
    private FeedBack feedback;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "yes_no_answer")
    private Boolean yesNoAnswer;

    @Column(name = "text_answer")
    private String textAnswer;
}
