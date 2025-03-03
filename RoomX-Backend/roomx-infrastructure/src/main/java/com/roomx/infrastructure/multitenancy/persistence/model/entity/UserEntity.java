package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = UserEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_nguoi_dung", columnNames = {"user_code", "email", "phone_number"})
})
public class UserEntity {
    public static final String TABLE_NAME = "\"user\"";
    public static final String COLUMN_ID_NAME = "user_id";
    public static final String COLUMN_FIRSTNAME_NAME = "first_name";
    public static final String COLUMN_LASTNAME_NAME = "last_name";
    public static final String COLUMN_PHONENUMBER_NAME = "phone_number";
    public static final String COLUMN_EMAIL_NAME = "email";
    public static final String COLUMN_GENDER_NAME = "gender";
    public static final String COLUMN_AVATARIMAGE_NAME = "avatar_image";
    public static final String COLUMN_USERTYPE_NAME = "user_type";
    public static final String COLUMN_USERCODE_NAME = "user_code";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 128)
    @Column(name = COLUMN_FIRSTNAME_NAME, length = 128)
    private String firstName;

    @Size(max = 64)
    @Column(name = COLUMN_LASTNAME_NAME, length = 64)
    private String lastName;

    @Size(max = 10)
    @Column(name = COLUMN_PHONENUMBER_NAME, length = 10)
    private String phoneNumber;

    @Size(max = 500)
    @NotNull
    @Column(name = COLUMN_EMAIL_NAME, nullable = false, length = 500)
    private String email;

    @Column(name = COLUMN_GENDER_NAME)
    private Boolean gender;

    @Column(name = COLUMN_AVATARIMAGE_NAME, length = Integer.MAX_VALUE)
    private String avatarImage;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_USERTYPE_NAME, nullable = false, length = 32)
    private String userType;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_USERCODE_NAME, nullable = false, length = 32)
    private String userCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}