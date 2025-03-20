package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
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
@Table(name = BranchEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_branch_code", columnNames = {"branch_code"})
})
public class BranchEntity {
    public static final String TABLE_NAME = "branch";
    public static final String COLUMN_ID_NAME = "branch_id";
    public static final String COLUMN_BRANCH_CODE_NAME = "branch_code";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_PHONENUMBER_NAME = "phone_number";
    public static final String COLUMN_EMAIL_NAME = "email";
    public static final String COLUMN_ADDRESS_NAME = "address";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";
    public static final String COLUMN_STATUS_NAME = "status";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 32)
    @Column(name = COLUMN_BRANCH_CODE_NAME, length = 32)
    private String branchCode;

    @Size(max = 500)
    @Column(name = COLUMN_PHONENUMBER_NAME, length = 500)
    private String phoneNumber;

    @Size(max = 500)
    @Column(name = COLUMN_EMAIL_NAME, length = 500)
    private String email;

    @Size(max = 500)
    @Column(name = COLUMN_ADDRESS_NAME, length = 500)
    private String address;

    @Column(name = COLUMN_STATUS_NAME, length = Integer.MAX_VALUE)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}