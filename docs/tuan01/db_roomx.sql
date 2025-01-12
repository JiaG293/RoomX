create schema if not exists schema_roomx;

SET search_path TO schema_roomx;

drop table if exists schema_roomx.vai_tro_quyen cascade;

drop table if exists schema_roomx.quyen cascade;

drop table if exists schema_roomx.ngan_sach cascade;

drop table if exists schema_roomx.nhan_vien_vai_tro cascade;

drop table if exists schema_roomx.vai_tro cascade;

drop table if exists schema_roomx.chi_tiet_dich_vu cascade;

drop table if exists schema_roomx.thiet_bi cascade;

drop table if exists schema_roomx.loai_thiet_bi cascade;

drop table if exists schema_roomx.dich_vu cascade;

drop table if exists schema_roomx.phieu_dat_phong cascade;

drop table if exists schema_roomx.nhan_vien cascade;

drop table if exists schema_roomx.phong_ban cascade;

drop table if exists schema_roomx.phong_hop cascade;

drop table if exists schema_roomx.chi_nhanh cascade;







--VAI TRO
create table schema_roomx.vai_tro
(
    id    varchar(64) not null,
    mo_ta text        not null,
    constraint pk_vai_tro
        primary key (id)
);






--QUYEN
create table schema_roomx.quyen
(
    id    varchar(64) not null,
    mo_ta text        not null,
    constraint pk_quyen
        primary key (id)
);






--VAI TRO QUYEN
create table schema_roomx.vai_tro_quyen
(
    vai_tro_id varchar(64) not null,
    quyen_id   varchar(64) not null,
    constraint pk_vaitroid_quyenid
        primary key (vai_tro_id, quyen_id),
    constraint fk_vaitroquyen_vaitroid
        foreign key (vai_tro_id) references schema_roomx.vai_tro,
    constraint fk_vaitroquyen_quyenid
        foreign key (quyen_id) references schema_roomx.quyen
);

create index fk_vaitroquyen_vaitroid_index
    on schema_roomx.vai_tro_quyen (vai_tro_id);

create index fk_vaitroquyen_quyenid_index
    on schema_roomx.vai_tro_quyen (quyen_id);







--CHI NHANH
create table schema_roomx.chi_nhanh
(
    id            uuid         not null,
    ten           varchar(256) not null,
    dia_chi       text         not null,
    so_dien_thoai varchar(10)  not null,
    email         varchar(256) not null,
    constraint pk_chi_nhanh
        primary key (id)
);







--PHONG BAN
create table schema_roomx.phong_ban
(
    id            uuid         not null,
    ten           varchar(128) not null,
    truong_phong  bigint       not null,
    vi_tri        text         not null,
    so_luong      smallint     not null,
    so_dien_thoai varchar(10)  not null,
    chi_nhanh_id     uuid         not null,
    constraint pk_phong_ban
        primary key (id),
    constraint fk_phongban_chinhanhid
        foreign key (chi_nhanh_id) references schema_roomx.chi_nhanh
);

create index fk_phongban_chinhanhid_index
    on schema_roomx.phong_ban (chi_nhanh_id);







--NGAN SACH
create table schema_roomx.ngan_sach
(
    id           uuid        not null,
    chi_phi      numeric     not null,
    ngay_phan_bo date        not null,
    trang_thai   varchar(50) not null,
    phong_ban_id uuid        not null,
    constraint pk_ngan_sach
        primary key (id),
    constraint fk_ngansach_phongbanid
        foreign key (phong_ban_id) references schema_roomx.phong_ban
);

create index fk_ngansach_phongbanid_index
    on schema_roomx.ngan_sach (phong_ban_id);








--NHAN VIEN
create table schema_roomx.nhan_vien
(
    id             bigint       not null,
    phong_ban_id      uuid         not null,
    ho             varchar(64)  not null,
    ten            varchar(128) not null,
    so_dien_thoai  varchar(10)  not null,
    email          varchar(256) not null,
    tinh_thanh_pho varchar(64)  not null,
    quan_huyen     varchar(64)  not null,
    duong          varchar(64)  not null,
    gioi_tinh      boolean      not null,
    password       varchar(512) not null,
    constraint pk_nhan_vien
        primary key (id),
    constraint fk_nhanvien_phongbanid
        foreign key (phong_ban_id) references schema_roomx.phong_ban
);

create index fk_nhanvien_phongbanid_index
    on schema_roomx.nhan_vien (phong_ban_id);







--NHAN VIEN VAI TRO
create table schema_roomx.nhan_vien_vai_tro
(
    vai_tro_id      varchar(64) not null,
    nhan_vien_id    bigint      not null,
    constraint pk_vaitroid_nhanvienid
        primary key (vai_tro_id, nhan_vien_id),
    constraint fk_nhanvienvaitro_vaitroid
        foreign key (vai_tro_id) references schema_roomx.vai_tro,
    constraint fk_nhanvienvaitro_nhanvienid
        foreign key (nhan_vien_id) references schema_roomx.nhan_vien
);

create index fk_nhanvienvaitro_vaitroid_index
    on schema_roomx.nhan_vien_vai_tro (vai_tro_id);

create index fk_nhanvienvaitro_nhanvie_index
    on schema_roomx.nhan_vien_vai_tro (nhan_vien_id);







--LOAI THIET BI
create table schema_roomx.loai_thiet_bi
(
    id      uuid        not null,
    chi_phi numeric     not null,
    ten     varchar(50) not null,
    constraint pk_loaithietbi
        primary key (id)
);







--PHONG HOP
create table schema_roomx.phong_hop
(
    id           uuid        not null,
    loai         varchar(32) not null,
    suc_chua     integer     not null,
    toa_nha      varchar(64) not null,
    tang         smallint    not null,
    thiet_bi     uuid        not null,
    trang_thai   varchar(50) not null,
    mo_ta        text        not null,
    chi_phi      numeric     not null,
    chi_nhanh_id uuid        not null,
    constraint pk_phonghop
        primary key (id),
    constraint fk_phonghop_chinhanhid
        foreign key (chi_nhanh_id) references schema_roomx.chi_nhanh
);

create index fk_phonghop_chinhanhid_index
    on schema_roomx.phong_hop (chi_nhanh_id);







--THIET BI
create table schema_roomx.thiet_bi
(
    id                 uuid                     not null,
    ma_san_pham        varchar(128)             not null,
    ten                varchar(128)             not null,
    ngay_lap_dat       timestamp with time zone not null,
    trang_thai_su_dung varchar(32)              not null,
    tinh_trang         varchar(32)              not null,
    vi_tri_id          uuid                     not null,
    loai_thiet_bi_id   uuid                     not null,
    constraint pk_thietbi
        primary key (id),
    constraint fk_thietbi_vitriid
        foreign key (vi_tri_id) references schema_roomx.phong_hop,
    constraint fk_thietbi_loaithietbiid
        foreign key (loai_thiet_bi_id) references schema_roomx.loai_thiet_bi
);

create index fk_thietbi_vitriid_index
    on schema_roomx.thiet_bi (vi_tri_id);

create index fk_thietbi_loaithietbiid_index
    on schema_roomx.thiet_bi (loai_thiet_bi_id);






--PHIEU DAT PHONG
create table schema_roomx.phieu_dat_phong
(
    id             uuid                     not null,
    phong_hop_id   uuid                     not null,
    nhan_vien_id   bigint                   not null,
    ngay_dat       timestamp with time zone not null,
    ngay_ket_thuc  timestamp with time zone not null,
    thoi_luong_dat integer                  not null,
    trang_thai     varchar(32)              not null,
    loai           varchar(50)              not null,
    tong_chi_phi   numeric                  not null,
    constraint pk_phieudatphong
        primary key (id),
    constraint fk_phieudatphong_nhanvienid
        foreign key (nhan_vien_id) references schema_roomx.nhan_vien,
    constraint fk_phieudatphong_phonghopid
        foreign key (phong_hop_id) references schema_roomx.phong_hop
);

create index fk_phieudatphong_nhanvienid_index
    on schema_roomx.phieu_dat_phong (nhan_vien_id);

create index fk_phieudatphong_phonghopid
    on schema_roomx.phieu_dat_phong (phong_hop_id);






--DICH VU
create table schema_roomx.dich_vu
(
    id                 uuid         not null,
    ten                varchar(256) not null,
    gia                numeric      not null,
    so_luong           integer      not null,
    mo_ta              text         not null,
    ghi_chu            text         not null,
    phieu_dat_phong_id uuid         not null,
    constraint pk_dichvu
        primary key (id),
    constraint fk_dichvu_phieudatphongid
        foreign key (phieu_dat_phong_id) references schema_roomx.phieu_dat_phong
);

create index fk_dichvu_phieudatphongid_index
    on schema_roomx.dich_vu (phieu_dat_phong_id);







--CHI TIET DICH VU
create table schema_roomx.chi_tiet_dich_vu
(
    dich_vu_id  uuid not null,
    thiet_bi_id uuid not null,
    mo_ta       text not null,
    constraint pk_chitietdichvu
        primary key (dich_vu_id, thiet_bi_id),
    constraint fk_chitietdichvu_dichvuid
        foreign key (dich_vu_id) references schema_roomx.dich_vu,
    constraint fk_chitietdichvu_thietbiid
        foreign key (thiet_bi_id) references schema_roomx.thiet_bi
);

create index fk_chitietdichvu_dichvuid_index
    on schema_roomx.chi_tiet_dich_vu (dich_vu_id);

create index fk_chitietdichvu_thietbiid_index
    on schema_roomx.chi_tiet_dich_vu (thiet_bi_id);
