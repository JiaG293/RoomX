package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = BranchEntity.TABLE_NAME)
public class BranchEntity {
    public static final String TABLE_NAME = "branch";
    public static final String COLUMN_ID_NAME = "branch_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_PHONENUMBER_NAME = "phone_number";
    public static final String COLUMN_EMAIL_NAME = "email";
    public static final String COLUMN_ADDRESS_NAME = "address";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 500)
    @Column(name = COLUMN_PHONENUMBER_NAME, length = 500)
    private String phoneNumber;

    @Size(max = 500)
    @Column(name = COLUMN_EMAIL_NAME, length = 500)
    private String email;

    @Size(max = 500)
    @Column(name = COLUMN_ADDRESS_NAME, length = 500)
    private String address;

}