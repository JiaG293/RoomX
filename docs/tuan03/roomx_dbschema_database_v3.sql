CREATE SCHEMA IF NOT EXISTS "public";

CREATE  TABLE "public".chi_nhanh ( 
	chi_nhanh_id         uuid  NOT NULL  ,
	ten                  varchar(500)    ,
	so_dien_thoai        varchar(500)    ,
	email                varchar(500)    ,
	dia_chi              varchar(500)    ,
	CONSTRAINT pk_chi_nhanh PRIMARY KEY ( chi_nhanh_id )
 );

CREATE  TABLE "public".dich_vu ( 
	dich_vu_id           uuid  NOT NULL  ,
	ten                  varchar(500)    ,
	mo_ta                varchar(500)    ,
	ghi_chu              varchar(500)    ,
	so_luong             smallint    ,
	CONSTRAINT pk_dich_vu PRIMARY KEY ( dich_vu_id ),
	CONSTRAINT pk_dich_vu PRIMARY KEY ( dich_vu_id )
 );

CREATE  TABLE "public".doi_tac ( 
	email                varchar(500)  NOT NULL  ,
	created_at           timestamp    ,
	updated_at           timestamp    ,
	doi_tac_id           uuid  NOT NULL  ,
	CONSTRAINT pk_doi_tac PRIMARY KEY ( email )
 );

CREATE  TABLE "public".loai_chu_ky ( 
	loai_chu_ky_id       varchar(32)  NOT NULL  ,
	ten                  varchar(128)    ,
	mo_ta                text    ,
	CONSTRAINT pk_loai_chu_ky PRIMARY KEY ( loai_chu_ky_id )
 );

CREATE  TABLE "public".loai_thiet_bi ( 
	loai_thiet_bi_id     uuid  NOT NULL  ,
	gia                  numeric  NOT NULL  ,
	ten                  varchar(500)  NOT NULL  ,
	CONSTRAINT pk_loai_thiet_bi PRIMARY KEY ( loai_thiet_bi_id )
 );

CREATE  TABLE "public".loai_vi_tri ( 
	loai_vi_tri_id       varchar  NOT NULL  ,
	ten                  varchar(500)    ,
	CONSTRAINT pk_loai_vi_tri PRIMARY KEY ( loai_vi_tri_id )
 );

CREATE  TABLE "public".ngay_ngoai_le ( 
	ngay_ngoai_le_id     uuid  NOT NULL  ,
	ngay                 date    ,
	thoi_gian_cho_phep   daterange    
 );

CREATE  TABLE "public".phong_ban ( 
	phong_ban_id         uuid  NOT NULL  ,
	ten                  varchar(500)  NOT NULL  ,
	truong_phong         varchar(500)    ,
	vi_tri               varchar(500)    ,
	so_luong             smallint    ,
	so_dien_thoai        varchar(500)    ,
	chi_nhanh_id         uuid    ,
	CONSTRAINT pk_phong_ban PRIMARY KEY ( phong_ban_id ),
	CONSTRAINT pk_phong_ban PRIMARY KEY ( phong_ban_id ),
	CONSTRAINT fk_phong_ban_chi_nhanh FOREIGN KEY ( chi_nhanh_id ) REFERENCES "public".chi_nhanh( chi_nhanh_id )   
 );

CREATE  TABLE "public".quyen ( 
	quyen_id             varchar(32)  NOT NULL  ,
	mo_ta                text    ,
	CONSTRAINT pk_quyen PRIMARY KEY ( quyen_id )
 );

CREATE  TABLE "public".suc_chua ( 
	suc_chua_id          smallint  NOT NULL  ,
	gia_phong            numeric    ,
	CONSTRAINT pk_suc_chua PRIMARY KEY ( suc_chua_id )
 );

CREATE  TABLE "public".tai_khoan ( 
	tai_khoan_id         varchar(32)  NOT NULL  ,
	email                varchar(500)    ,
	"password"           varchar(500)    ,
	trang_thai           varchar(500)    ,
	google_account_id    varchar(500)    ,
	google_secret_key    varchar(500)    ,
	CONSTRAINT pk_tai_khoan PRIMARY KEY ( tai_khoan_id )
 );

CREATE  TABLE "public".thanh_vien ( 
	thanh_vien_id        uuid  NOT NULL  ,
	nhom_nhan_vien_id    uuid    ,
	loai                 varchar    ,
	nhom_doi_tac_id      uuid    ,
	don_yeu_cau_id       uuid  NOT NULL  ,
	CONSTRAINT pk_thanh_vien_nhom PRIMARY KEY ( thanh_vien_id )
 );

CREATE  TABLE "public".trang_thai_phong ( 
	trang_thai_phong_id  varchar(32)  NOT NULL  ,
	ten                  varchar(500)    ,
	CONSTRAINT pk_trang_thai_phong PRIMARY KEY ( trang_thai_phong_id )
 );

CREATE  TABLE "public".vai_tro ( 
	vai_tro_id           varchar(32)  NOT NULL  ,
	mo_ta                text    ,
	CONSTRAINT pk_vai_tro PRIMARY KEY ( vai_tro_id )
 );

CREATE  TABLE "public".vai_tro_quyen ( 
	vai_tro_id           varchar(32)  NOT NULL  ,
	quyen_id             varchar(32)  NOT NULL  ,
	CONSTRAINT fk_vai_tro_quyen_vai_tro FOREIGN KEY ( vai_tro_id ) REFERENCES "public".vai_tro( vai_tro_id )   ,
	CONSTRAINT fk_vai_tro_quyen_quyen FOREIGN KEY ( quyen_id ) REFERENCES "public".quyen( quyen_id )   
 );

CREATE  TABLE "public".vi_tri ( 
	vi_tri_id            uuid  NOT NULL  ,
	loai_vi_tri_id       varchar(32)  NOT NULL  ,
	chi_nhanh_id         uuid    ,
	slug                 varchar    ,
	tang                 smallint    ,
	toa_nha              smallint    ,
	phong                uuid    ,
	so_do                varchar[]    ,
	CONSTRAINT pk_vi_tri PRIMARY KEY ( vi_tri_id ),
	CONSTRAINT fk_vi_tri_chi_nhanh FOREIGN KEY ( chi_nhanh_id ) REFERENCES "public".chi_nhanh( chi_nhanh_id )   ,
	CONSTRAINT fk_vi_tri_loai_vi_tri FOREIGN KEY ( loai_vi_tri_id ) REFERENCES "public".loai_vi_tri( loai_vi_tri_id )   
 );

CREATE  TABLE "public".ngan_sach ( 
	ngan_sach_id         uuid  NOT NULL  ,
	phong_ban_id         uuid  NOT NULL  ,
	chi_phi              numeric    ,
	thang                smallint    ,
	nam                  smallint    ,
	trang_thai           varchar(32)    ,
	CONSTRAINT pk_ngan_sach PRIMARY KEY ( ngan_sach_id ),
	CONSTRAINT fk_ngan_sach_phong_ban FOREIGN KEY ( phong_ban_id ) REFERENCES "public".phong_ban( phong_ban_id )   
 );

CREATE  TABLE "public".nhan_vien ( 
	nhan_vien_id         varchar(32)  NOT NULL  ,
	phong_ban_id         uuid  NOT NULL  ,
	chuc_vu_id           varchar(32)  NOT NULL  ,
	ho                   varchar(128)    ,
	ten                  varchar(32)    ,
	so_dien_thoai        varchar(10)    ,
	email                varchar(500)  NOT NULL  ,
	gioi_tinh            boolean    ,
	anh_dai_dien         varchar    ,
	CONSTRAINT pk_nhan_vien PRIMARY KEY ( nhan_vien_id ),
	CONSTRAINT fk_nhan_vien_phong_ban FOREIGN KEY ( phong_ban_id ) REFERENCES "public".phong_ban( phong_ban_id )   
 );

CREATE  TABLE "public".nhom ( 
	nhom_id              uuid  NOT NULL  ,
	nhan_vien_id         varchar(32)    ,
	ten                  varchar(500)    ,
	loai                 varchar(32)    ,
	CONSTRAINT pk_nhom PRIMARY KEY ( nhom_id ),
	CONSTRAINT fk_nhom_nhan_vien FOREIGN KEY ( nhan_vien_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   
 );

CREATE  TABLE "public".nhom_don_yeu_cau ( 
	thanh_vien_id        uuid  NOT NULL  ,
	nhom_id              uuid  NOT NULL  ,
	CONSTRAINT pk_nhom_don_yeu_cau PRIMARY KEY ( thanh_vien_id, nhom_id ),
	CONSTRAINT fk_nhom_don_yeu_cau_nhom FOREIGN KEY ( nhom_id ) REFERENCES "public".nhom( nhom_id )   ,
	CONSTRAINT fk_nhom_don_yeu_cau_thanh_vien FOREIGN KEY ( thanh_vien_id ) REFERENCES "public".thanh_vien( thanh_vien_id )   
 );

CREATE  TABLE "public".phong_hop ( 
	phong_hop_id         uuid  NOT NULL  ,
	vi_tri_id            uuid  NOT NULL  ,
	suc_chua_id          smallint  NOT NULL  ,
	trang_thai           varchar(500)    ,
	mo_ta                text    ,
	CONSTRAINT pk_phong_hop PRIMARY KEY ( phong_hop_id ),
	CONSTRAINT fk_phong_hop_vi_tri FOREIGN KEY ( vi_tri_id ) REFERENCES "public".vi_tri( vi_tri_id )   ,
	CONSTRAINT fk_phong_hop_suc_chua FOREIGN KEY ( suc_chua_id ) REFERENCES "public".suc_chua( suc_chua_id )   
 );

CREATE  TABLE "public".tai_khoan_vai_tro ( 
	tai_khoan_id         varchar(32)  NOT NULL  ,
	vai_tro_id           varchar(32)  NOT NULL  ,
	CONSTRAINT pk_tai_khoan_vai_tro PRIMARY KEY ( tai_khoan_id, vai_tro_id ),
	CONSTRAINT fk_tai_khoan_vai_tro_tai_khoan FOREIGN KEY ( tai_khoan_id ) REFERENCES "public".tai_khoan( tai_khoan_id )   ,
	CONSTRAINT fk_tai_khoan_vai_tro_vai_tro FOREIGN KEY ( vai_tro_id ) REFERENCES "public".vai_tro( vai_tro_id )   
 );

CREATE  TABLE "public".thanh_vien_nhom ( 
	thanh_vien_nhom_id   uuid  NOT NULL  ,
	nhan_vien_id         varchar(32)  NOT NULL  ,
	nhom_id              uuid  NOT NULL  ,
	CONSTRAINT pk_thanh_vien_nhom_0 PRIMARY KEY ( thanh_vien_nhom_id ),
	CONSTRAINT fk_thanh_vien_nhom_nhan_vien FOREIGN KEY ( nhan_vien_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_thanh_vien_nhom_nhom FOREIGN KEY ( nhom_id ) REFERENCES "public".nhom( nhom_id )   
 );

CREATE  TABLE "public".thiet_bi ( 
	thiet_bi_id          uuid  NOT NULL  ,
	ten                  varchar(500)  NOT NULL  ,
	trang_thai           varchar(500)  NOT NULL  ,
	phong_hop_id         uuid    ,
	loai_thiet_bi_id     uuid  NOT NULL  ,
	thuong_hieu          varchar    ,
	hinh_anh_mo_ta       varchar[]    ,
	CONSTRAINT pk_thiet_bi PRIMARY KEY ( thiet_bi_id ),
	CONSTRAINT fk_thiet_bi_phong_hop FOREIGN KEY ( phong_hop_id ) REFERENCES "public".phong_hop( phong_hop_id )   ,
	CONSTRAINT fk_thiet_bi_loai_thiet_bi FOREIGN KEY ( loai_thiet_bi_id ) REFERENCES "public".loai_thiet_bi( loai_thiet_bi_id )   
 );

CREATE  TABLE "public".yeu_cau_dat_phong ( 
	yeu_cau_dat_phong_id uuid  NOT NULL  ,
	phong_hop_id         uuid  NOT NULL  ,
	chu_ky_id            uuid  NOT NULL  ,
	nhan_vien_dat_id     varchar(32)  NOT NULL  ,
	nhan_vien_duyet_id   varchar    ,
	lap_lai              boolean    ,
	trang_thai_duyet     varchar(500)    ,
	created_at           timestamptz    ,
	updated_at           timestamptz    ,
	thanh_vien_id        uuid    ,
	dich_vu_id           uuid    ,
	CONSTRAINT pk_yeu_cau_dat_phong PRIMARY KEY ( yeu_cau_dat_phong_id ),
	CONSTRAINT fk_yeu_cau_dat_phong_nhan_vien FOREIGN KEY ( nhan_vien_dat_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_yeu_cau_dat_phong_nhan_vien_0 FOREIGN KEY ( nhan_vien_duyet_id ) REFERENCES "public".nhan_vien( nhan_vien_id )   ,
	CONSTRAINT fk_yeu_cau_dat_phong_phong_hop FOREIGN KEY ( phong_hop_id ) REFERENCES "public".phong_hop( phong_hop_id )   ,
	CONSTRAINT fk_yeu_cau_dat_phong_thanh_vien FOREIGN KEY ( thanh_vien_id ) REFERENCES "public".thanh_vien( thanh_vien_id )   ,
	CONSTRAINT fk_yeu_cau_dat_phong_dich_vu FOREIGN KEY ( dich_vu_id ) REFERENCES "public".dich_vu( dich_vu_id )   
 );

CREATE  TABLE "public".chu_ky ( 
	chu_ky_id            uuid  NOT NULL  ,
	yeu_cau_dat_phong_id uuid  NOT NULL  ,
	loai_chu_ky_id       varchar(32)  NOT NULL  ,
	trang_thai_phong_id  varchar(32)  NOT NULL  ,
	ngay_cua_thang       smallint    ,
	ngay_trong_tuan      smallint    ,
	thang_trong_nam      smallint    ,
	so_lan_lap           smallint    ,
	trang_thai_id        varchar(32)  NOT NULL  ,
	CONSTRAINT pk_chu_ky PRIMARY KEY ( chu_ky_id ),
	CONSTRAINT fk_chu_ky_yeu_cau_dat_phong FOREIGN KEY ( yeu_cau_dat_phong_id ) REFERENCES "public".yeu_cau_dat_phong( yeu_cau_dat_phong_id )   ,
	CONSTRAINT fk_chu_ky_loai_chu_ky FOREIGN KEY ( loai_chu_ky_id ) REFERENCES "public".loai_chu_ky( loai_chu_ky_id )   ,
	CONSTRAINT fk_chu_ky_trang_thai_phong FOREIGN KEY ( trang_thai_phong_id ) REFERENCES "public".trang_thai_phong( trang_thai_phong_id )   
 );

CREATE  TABLE "public".dich_vu_thiet_bi ( 
	dich_vu_id           uuid  NOT NULL  ,
	thiet_bi_id          uuid  NOT NULL  ,
	so_luong             smallint    ,
	CONSTRAINT pk_dich_vu_thiet_bi PRIMARY KEY ( dich_vu_id, thiet_bi_id ),
	CONSTRAINT fk_dich_vu_thiet_bi_thiet_bi FOREIGN KEY ( thiet_bi_id ) REFERENCES "public".thiet_bi( thiet_bi_id )   ,
	CONSTRAINT fk_dich_vu_thiet_bi_dich_vu FOREIGN KEY ( dich_vu_id ) REFERENCES "public".dich_vu( dich_vu_id )   
 );

