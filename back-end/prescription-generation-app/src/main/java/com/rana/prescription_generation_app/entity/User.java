package com.rana.prescription_generation_app.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.rana.prescription_generation_app.constants.SequenceConstants;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * The user model for the application.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS")
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = SequenceConstants.USER_SEQUENCE)
    @SequenceGenerator(name = SequenceConstants.USER_SEQUENCE,  initialValue = SequenceConstants.USER_SEQUENCE_INITIAL_VALUE, allocationSize = SequenceConstants.USER_SEQUENCE_ALLOCATION)
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false, updatable = false)
    protected String publicId;

    @Column(name = "USER_NAME",unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL",unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.publicId = UUID.randomUUID().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


