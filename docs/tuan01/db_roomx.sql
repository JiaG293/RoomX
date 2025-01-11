CREATE TABLE schema_roomx.chi_nhanh
(
    "id"          uuid NOT NULL,
    ten           varchar(256) NOT NULL,
    dia_chi       uuid NOT NULL,
    so_dien_thoai varchar(10) NOT NULL,
    email         varchar(256) NOT NULL,
    CONSTRAINT PK_4 PRIMARY KEY ( "id" )
);

CREATE TABLE schema_roomx.dich_vu
(
    "id"     uuid NOT NULL,
    ten      varchar(256) NOT NULL,
    gia      bigdecimal NOT NULL,
    so_luong int4 NOT NULL,
    mo_ta    text NOT NULL,
    ghi_chu  text NOT NULL,
    CONSTRAINT PK_6 PRIMARY KEY ( "id" )
);

CREATE TABLE schema_roomx.lich_su_bao_tri
(
    "id"            uuid NOT NULL,
    ngay_bao_tri    date NOT NULL,
    nguoi_phu_trach uuid NOT NULL,
    ghi_chu         text NOT NULL,
    CONSTRAINT PK_7 PRIMARY KEY ( "id" )
);

CREATE TABLE schema_roomx.ngan_sach
(
    "id"         uuid NOT NULL,
    chi_phi      bigdecimal NOT NULL,
    ngay_phan_bo date NOT NULL,
    CONSTRAINT PK_9 PRIMARY KEY ( "id" )
);

CREATE TABLE schema_roomx.nhan_vien
(
    "id"          bigint NOT NULL,
    phong_ban     uuid NOT NULL,
    ho            varchar(64) NOT NULL,
    ten           varchar(128) NOT NULL,
    so_dien_thoai varchar(10) NOT NULL,
    email         varchar(256) NOT NULL,
    CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_nhan_vien_phong_ban FOREIGN KEY ( phong_ban ) REFERENCES schema_roomx.phong_ban ( "id" )
);

CREATE INDEX FK_1 ON schema_roomx.nhan_vien
    (
     phong_ban
        );

CREATE TABLE schema_roomx.phieu_dat_phong
(
    "id"           uuid NOT NULL,
    phong_hop_id   uuid NOT NULL,
    nhan_vien_id   bigint NOT NULL,
    ngay_dat       timestamptz NOT NULL,
    ngay_ket_thuc  timestamptz NOT NULL,
    thoi_luong_dat int NOT NULL,
    dich_vu_them   uuid NOT NULL,
    trang_thai     varchar(32) NOT NULL,
    loai           varchar(50) NOT NULL,
    tong_chi_phi   bigdecimal NOT NULL,
    CONSTRAINT PK_9_1 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_nhan_vien_phieu_dat_phong FOREIGN KEY ( nhan_vien_id ) REFERENCES schema_roomx.nhan_vien ( "id" ),
    CONSTRAINT FK_phong_hop_phieu_dat_phong FOREIGN KEY ( phong_hop_id ) REFERENCES schema_roomx.phong_hop ( "id" )
);

CREATE INDEX FK_1 ON schema_roomx.phieu_dat_phong
    (
     nhan_vien_id
        );

CREATE INDEX FK_2 ON schema_roomx.phieu_dat_phong
    (
     phong_hop_id
        );


CREATE TABLE schema_roomx.phong_ban
(
    "id"          uuid NOT NULL,
    ten           varchar(128) NOT NULL,
    truong_phong  bigint NOT NULL,
    vi_tri        text NOT NULL,
    so_luong      int2 NOT NULL,
    ngan_sach     uuid NOT NULL,
    so_dien_thoai varchar(10) NOT NULL,
    chi_nhanh     uuid NOT NULL,
    CONSTRAINT PK_2 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_chi_nhanh_phong_ban FOREIGN KEY ( chi_nhanh ) REFERENCES schema_roomx.chi_nhanh ( "id" ),
    CONSTRAINT FK_phong_ban_ngan_sach FOREIGN KEY ( ngan_sach ) REFERENCES schema_roomx.ngan_sach ( "id" )
);

CREATE INDEX FK_1 ON schema_roomx.phong_ban
    (
     chi_nhanh
        );

CREATE INDEX FK_2 ON schema_roomx.phong_ban
    (
     ngan_sach
        );

CREATE TABLE schema_roomx.phong_hop
(
    "id"       uuid NOT NULL,
    loai       varchar(32) NOT NULL,
    suc_chua   int NOT NULL,
    toa_nha    varchar(64) NOT NULL,
    tang       int2 NOT NULL,
    chi_nhanh  uuid NOT NULL,
    thiet_bi   uuid NOT NULL,
    trang_thai varchar(50) NOT NULL,
    mo_ta      text NOT NULL,
    chi_phi    bigdecimal NOT NULL,
    CONSTRAINT PK_3 PRIMARY KEY ( "id" )
);

CREATE TABLE schema_roomx.thiet_bi
(
    "id"               uuid NOT NULL,
    ma_san_pham        varchar(128) NOT NULL,
    ten                varchar(128) NOT NULL,
    loai               varchar(64) NOT NULL,
    vi_tri             uuid NOT NULL,
    chi_phi            bigdecimal NOT NULL,
    han_bao_hanh       timestamptz NOT NULL,
    ngay_lap_dat       timestamptz NOT NULL,
    trang_thai_su_dung varchar(32) NOT NULL,
    lich_su_bao_tri    uuid NOT NULL,
    tinh_trang         varchar(32) NOT NULL,
    CONSTRAINT PK_5 PRIMARY KEY ( "id" ),
    CONSTRAINT FK_lich_su_bao_tri_thiet_bi FOREIGN KEY ( lich_su_bao_tri ) REFERENCES schema_roomx.lich_su_bao_tri ( "id" )
);

CREATE INDEX FK_1 ON schema_roomx.thiet_bi
    (
     lich_su_bao_tri
        );

CREATE TABLE schema_roomx.thiet_bi_phong
(
    thiet_bi_id uuid NOT NULL,
    dich_vu_id  uuid NOT NULL,
    CONSTRAINT PK_10 PRIMARY KEY ( thiet_bi_id, dich_vu_id )
);
