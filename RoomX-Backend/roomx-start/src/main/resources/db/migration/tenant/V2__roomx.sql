CREATE TABLE group_users
(
    group_id VARCHAR(255) NOT NULL,
    user_id  VARCHAR(255) NOT NULL,
    CONSTRAINT group_users_pkey PRIMARY KEY (group_id, user_id)
);

CREATE TABLE groups
(
    group_id     VARCHAR(255) NOT NULL,
    pham_vi_nhom VARCHAR(255),
    loai_nhom    VARCHAR(255),
    ten_nhom     VARCHAR(255),
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE phones
(
    phone_id           VARCHAR(255) NOT NULL,
    so_dien_thoai      VARCHAR(10),
    uu_tien            SMALLINT,
    loai_so_dien_thoai VARCHAR(24),
    user_id            VARCHAR(255),
    CONSTRAINT phones_pkey PRIMARY KEY (phone_id)
);

CREATE TABLE users
(
    user_id         VARCHAR(255) NOT NULL,
    phong_ban_id    VARCHAR(32),
    email           VARCHAR(255),
    ma_nhan_vien    VARCHAR(32),
    ho              VARCHAR(255),
    gioi_tinh       BOOLEAN,
    ten             VARCHAR(255),
    trang_thai      VARCHAR(15),
    loai_nguoi_dung VARCHAR(32),
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

ALTER TABLE group_users
    ADD CONSTRAINT fk1tqlbnvol79qrsa1i4rmjfsi6 FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE NO ACTION;

ALTER TABLE group_users
    ADD CONSTRAINT fk6syyopfepdpec1ihe2v5klehr FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE NO ACTION;

ALTER TABLE phones
    ADD CONSTRAINT fkmg6d77tgqfen7n1g763nvsqe3 FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE NO ACTION;