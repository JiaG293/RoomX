CREATE SCHEMA IF NOT EXISTS "public";

CREATE  TABLE "public".chi_nhanh ( 
	chi_nhanh_id         uuid  NOT NULL  ,
	ten                  varchar(500)    ,
	so_dien_thoai        varchar(500)    ,
	email                varchar(500)    ,
	dia_chi              varchar(500)    ,
	CONSTRAINT chi_nhanh_pkey PRIMARY KEY ( chi_nhanh_id )
 );

CREATE  TABLE "public".chu_ky ( 
	chu_ky_id            varchar(500)  NOT NULL  ,
	thu                  smallint    ,
	CONSTRAINT chu_ky_pkey PRIMARY KEY ( chu_ky_id )
 );

CREATE  TABLE "public".doi_tac ( 
	doi_tac_id           uuid  NOT NULL  ,
	email                varchar(500)  NOT NULL  ,
	created_at           timestamp    ,
	updated_at           timestamp    ,
	CONSTRAINT unq_doi_tac_email UNIQUE ( email ) ,
	CONSTRAINT pk_doi_tac PRIMARY KEY ( doi_tac_id )
 );

CREATE  TABLE "public".loai_thiet_bi ( 
	loai_thiet_bi_id     uuid  NOT NULL  ,
	gia                  numeric    ,
	ten                  varchar(500)    ,
	CONSTRAINT loai_thiet_bi_pkey PRIMARY KEY ( loai_thiet_bi_id )
 );

CREATE  TABLE "public".ngay_ngoai_le ( 
	ngay_ngoai_le_id     uuid  NOT NULL  ,
	ngay                 date    ,
	thoi_gian_cho_phep   daterange    ,
	CONSTRAINT ngay_ngoai_le_pkey PRIMARY KEY ( ngay_ngoai_le_id )
 );

CREATE  TABLE "public".phong_ban ( 
	phong_ban_id         uuid  NOT NULL  ,
	ten                  varchar(500)  NOT NULL  ,
	truong_phong         varchar(500)    ,
	vi_tri               varchar(500)    ,
	so_luong             smallint    ,
	so_dien_thoai        varchar(500)    ,
	chi_nhanh_id         uuid  NOT NULL  ,
	CONSTRAINT phong_ban_pkey PRIMARY KEY ( phong_ban_id ),
	CONSTRAINT fk_phong_ban_chi_nhanh FOREIGN KEY ( chi_nhanh_id ) REFERENCES "public".chi_nhanh( chi_nhanh_id )   
 );

CREATE  TABLE "public".quyen ( 
	quyen_id             varchar(500)  NOT NULL  ,
	mo_ta                text    ,
	CONSTRAINT quyen_pkey PRIMARY KEY ( quyen_id )
 );

CREATE  TABLE "public".suc_chua ( 
	suc_chua_id          smallint  NOT NULL  ,
	gia_phong            numeric    ,
	CONSTRAINT suc_chua_pkey PRIMARY KEY ( suc_chua_id )
 );

CREATE  TABLE "public".tai_khoan ( 
	tai_khoan_id         varchar(500)  NOT NULL  ,
	email                varchar(500)    ,
	"password"           varchar(500)    ,
	trang_thai           varchar(500)    ,
	google_account_id    varchar(500)    ,
	google_secret_key    varchar(500)    ,
	CONSTRAINT tai_khoan_pkey PRIMARY KEY ( tai_khoan_id )
 );

CREATE  TABLE "public".thanh_vien ( 
	thanh_vien_id        uuid  NOT NULL  ,
	nhom_nhan_vien_id    uuid    ,
	loai                 varchar    ,
	nhom_doi_tac_id      uuid    ,
	don_yeu_cau_id       uuid  NOT NULL  ,
	CONSTRAINT pk_thanh_vien PRIMARY KEY ( thanh_vien_id )
 );

CREATE  TABLE "public".vai_tro ( 
	vai_tro_id           varchar(500)  NOT NULL  ,
	mo_ta                text    ,
	CONSTRAINT vai_tro_pkey PRIMARY KEY ( vai_tro_id )
 );

CREATE  TABLE "public".vai_tro_quyen ( 
	vai_tro_id           varchar(500)  NOT NULL  ,
	quyen_id             varchar(500)  NOT NULL  ,
	CONSTRAINT pk_vai_tro_quyen PRIMARY KEY ( vai_tro_id, quyen_id ),
	CONSTRAINT fk_vai_tro_quyen_suc_chua FOREIGN KEY ( vai_tro_id ) REFERENCES "public".vai_tro( vai_tro_id )   ,
	CONSTRAINT fk_vai_tro_quyen_quyen FOREIGN KEY ( quyen_id ) REFERENCES "public".quyen( quyen_id )   
 );

CREATE  TABLE "public".ngan_sach ( 
	ngan_sach_id         uuid  NOT NULL  ,
	chi_phi              numeric    ,
	thang_phan_bo        smallint    ,
	trang_thai           varchar(500)    ,
	phong_ban_id         uuid  NOT NULL  ,
	CONSTRAINT ngan_sach_pkey PRIMARY KEY ( ngan_sach_id ),
	CONSTRAINT fk_ngan_sach_phong_ban FOREIGN KEY ( phong_ban_id ) REFERENCES "public".phong_ban( phong_ban_id )   
 );

CREATE  TABLE "public".nhan_vien ( 
	nhan_vien_id         uuid  NOT NULL  ,
	ma_nhan_vien         varchar(500)  NOT NULL  ,
	phong_ban_id         uuid  NOT NULL  ,
	ho                   varchar(500)    ,
	ten                  varchar(500)    ,
	so_dien_thoai        varchar(500)    ,
	email                varchar(500)    ,
	gioi_tinh            boolean    ,
	chuc_vu              varchar(500)    ,
	CONSTRAINT nhan_vien_pkey PRIMARY KEY ( nhan_vien_id ),
	CONSTRAINT unq_nhan_vien_ma_nhan_vien UNIQUE ( ma_nhan_vien ) ,
	CONSTRAINT fk_nhan_vien_nhan_vien FOREIGN KEY ( nhan_vien_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_nhan_vien_tai_khoan FOREIGN KEY ( ma_nhan_vien ) REFERENCES "public".tai_khoan( tai_khoan_id )   ,
	CONSTRAINT fk_nhan_vien_phong_ban FOREIGN KEY ( phong_ban_id ) REFERENCES "public".phong_ban( phong_ban_id )   
 );

CREATE  TABLE "public".nhom ( 
	nhom_id              uuid  NOT NULL  ,
	thanh_vien_id        uuid  NOT NULL  ,
	ten                  varchar(500)    ,
	loai                 varchar(500)    ,
	CONSTRAINT nhom_pkey PRIMARY KEY ( nhom_id ),
	CONSTRAINT fk_nhom_nhan_vien FOREIGN KEY ( thanh_vien_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_nhom_doi_tac FOREIGN KEY ( thanh_vien_id ) REFERENCES "public".doi_tac( doi_tac_id )   
 );

CREATE  TABLE "public".phong_hop ( 
	phong_hop_id         uuid  NOT NULL  ,
	toa_nha              varchar(500)    ,
	tang                 varchar(500)    ,
	trang_thai           varchar(500)    ,
	mo_ta                text    ,
	chi_nhanh_id         uuid    ,
	suc_chua_id          smallint  NOT NULL  ,
	CONSTRAINT phong_hop_pkey PRIMARY KEY ( phong_hop_id ),
	CONSTRAINT fk_phong_hop_suc_chua FOREIGN KEY ( suc_chua_id ) REFERENCES "public".suc_chua( suc_chua_id )   
 );

CREATE  TABLE "public".tai_khoan_vai_tro ( 
	tai_khoan_id         varchar(500)  NOT NULL  ,
	vai_tro_id           varchar(500)  NOT NULL  ,
	CONSTRAINT pk_tai_khoan_vai_tro PRIMARY KEY ( vai_tro_id, tai_khoan_id ),
	CONSTRAINT fk_tai_khoan_vai_tro_chi_nhanh FOREIGN KEY ( tai_khoan_id ) REFERENCES "public".tai_khoan( tai_khoan_id )   ,
	CONSTRAINT fk_tai_khoan_vai_tro_vai_tro FOREIGN KEY ( vai_tro_id ) REFERENCES "public".vai_tro( vai_tro_id )   
 );

CREATE  TABLE "public".thiet_bi ( 
	thiet_bi_id          uuid  NOT NULL  ,
	ma_san_pham          varchar(500)  NOT NULL  ,
	ten                  varchar(500)  NOT NULL  ,
	trang_thai           varchar(500)  NOT NULL  ,
	tinh_trang           varchar(500)  NOT NULL  ,
	phong_hop_id         uuid  NOT NULL  ,
	loai_thiet_bi_id     uuid  NOT NULL  ,
	CONSTRAINT thiet_bi_pkey PRIMARY KEY ( thiet_bi_id ),
	CONSTRAINT fk_thiet_bi_phong_hop FOREIGN KEY ( phong_hop_id ) REFERENCES "public".phong_hop( phong_hop_id )   ,
	CONSTRAINT fk_thiet_bi_loai_thiet_bi FOREIGN KEY ( loai_thiet_bi_id ) REFERENCES "public".loai_thiet_bi( loai_thiet_bi_id )   
 );

CREATE  TABLE "public".don_yeu_cau ( 
	don_yeu_cau_id       uuid  NOT NULL  ,
	nhan_vien_id         uuid  NOT NULL  ,
	ma_nhan_vien_duyet_id varchar(500)    ,
	ma_phong_hop         uuid  NOT NULL  ,
	chu_ky_id            varchar(500)    ,
	ngay_dat             date    ,
	ngay_ket_thuc        date    ,
	thoi_luong           integer    ,
	trang_thai_duyet     varchar(500)    ,
	created_at           timestamp    ,
	updated_at           timestamp    ,
	thanh_vien_id        uuid    ,
	CONSTRAINT don_yeu_cau_pkey PRIMARY KEY ( don_yeu_cau_id ),
	CONSTRAINT fk_don_yeu_cau_nhan_vien FOREIGN KEY ( nhan_vien_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_don_yeu_cau_nhan_vien_0 FOREIGN KEY ( ma_nhan_vien_duyet_id ) REFERENCES "public".nhan_vien( ma_nhan_vien )   ,
	CONSTRAINT fk_don_yeu_cau_phong_hop FOREIGN KEY ( ma_phong_hop ) REFERENCES "public".phong_hop( phong_hop_id )   
 );

CREATE  TABLE "public".nhom_don_yeu_cau ( 
	don_yeu_cau_id       uuid  NOT NULL  ,
	nhom_id              uuid  NOT NULL  ,
	CONSTRAINT pk_nhom_don_yeu_cau PRIMARY KEY ( nhom_id, don_yeu_cau_id ),
	CONSTRAINT fk_nhom_don_yeu_cau_nhom FOREIGN KEY ( nhom_id ) REFERENCES "public".nhom( nhom_id )   ,
	CONSTRAINT fk_nhom_don_yeu_cau_don_yeu_cau FOREIGN KEY ( don_yeu_cau_id ) REFERENCES "public".don_yeu_cau( don_yeu_cau_id )   
 );

CREATE  TABLE "public".dich_vu ( 
	dich_vu_id           uuid  NOT NULL  ,
	ten                  varchar(500)    ,
	mo_ta                varchar(500)    ,
	ghi_chu              varchar(500)    ,
	don_yeu_cau_id       uuid    ,
	CONSTRAINT dich_vu_pkey PRIMARY KEY ( dich_vu_id ),
	CONSTRAINT fk_dich_vu_don_yeu_cau FOREIGN KEY ( don_yeu_cau_id ) REFERENCES "public".don_yeu_cau( don_yeu_cau_id )   
 );

CREATE  TABLE "public".dich_vu_thiet_bi ( 
	thiet_bi_id          uuid  NOT NULL  ,
	dich_vu_id           uuid  NOT NULL  ,
	CONSTRAINT pk_dich_vu_thiet_bi PRIMARY KEY ( thiet_bi_id, dich_vu_id ),
	CONSTRAINT fk_chi_tiet_dich_vu_thiet_bi FOREIGN KEY ( thiet_bi_id ) REFERENCES "public".thiet_bi( thiet_bi_id )   ,
	CONSTRAINT fk_chi_tiet_dich_vu_dich_vu FOREIGN KEY ( dich_vu_id ) REFERENCES "public".dich_vu( dich_vu_id )   
 );

CREATE  TABLE "public".don_dat_phong ( 
	don_dat_phong_id     uuid  NOT NULL  ,
	phong_hop_id         uuid  NOT NULL  ,
	don_yeu_cau_id       uuid  NOT NULL  ,
	thoi_gian_dat        timestamptz    ,
	trang_thai           varchar(500)    ,
	created_at           timestamptz    ,
	updated_at           timestamptz    ,
	loai                 varchar(9999999)    ,
	CONSTRAINT don_dat_phong_pkey PRIMARY KEY ( don_dat_phong_id ),
	CONSTRAINT fk_don_dat_phong_phong_hop FOREIGN KEY ( phong_hop_id ) REFERENCES "public".phong_hop( phong_hop_id )   ,
	CONSTRAINT fk_don_dat_phong_don_yeu_cau FOREIGN KEY ( don_yeu_cau_id ) REFERENCES "public".don_yeu_cau( don_yeu_cau_id )   
 );

