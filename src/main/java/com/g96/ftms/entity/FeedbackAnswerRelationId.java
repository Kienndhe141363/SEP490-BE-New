package com.g96.ftms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeedbackAnswerRelationId implements Serializable {
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "feedback_id")
    private Long feedbackId;
}
