package com.g96.ftms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserClassRelation {
    @EmbeddedId
    UserClassRelationId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private Class classs;
}