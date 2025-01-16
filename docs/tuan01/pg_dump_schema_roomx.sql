--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: chi_nhanh; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.chi_nhanh (
    id uuid NOT NULL,
    ten character varying(256) NOT NULL,
    dia_chi text NOT NULL,
    so_dien_thoai character varying(10) NOT NULL,
    email character varying(256) NOT NULL
);


ALTER TABLE public.chi_nhanh OWNER TO jiag;

--
-- Name: chi_tiet_dich_vu; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.chi_tiet_dich_vu (
    dich_vu_id uuid NOT NULL,
    thiet_bi_id uuid NOT NULL,
    mo_ta text NOT NULL
);


ALTER TABLE public.chi_tiet_dich_vu OWNER TO jiag;

--
-- Name: dich_vu; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.dich_vu (
    id uuid NOT NULL,
    ten character varying(256) NOT NULL,
    gia numeric NOT NULL,
    so_luong integer NOT NULL,
    mo_ta text NOT NULL,
    ghi_chu text NOT NULL,
    phieu_dat_phong_id uuid NOT NULL
);


ALTER TABLE public.dich_vu OWNER TO jiag;

--
-- Name: loai_thiet_bi; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.loai_thiet_bi (
    id uuid NOT NULL,
    chi_phi numeric NOT NULL,
    ten character varying(50) NOT NULL
);


ALTER TABLE public.loai_thiet_bi OWNER TO jiag;

--
-- Name: ngan_sach; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.ngan_sach (
    id uuid NOT NULL,
    chi_phi numeric NOT NULL,
    ngay_phan_bo date NOT NULL,
    trang_thai character varying(50) NOT NULL,
    phong_ban_id uuid NOT NULL
);


ALTER TABLE public.ngan_sach OWNER TO jiag;

--
-- Name: nhan_vien; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.nhan_vien (
    id bigint NOT NULL,
    phong_ban_id uuid NOT NULL,
    ho character varying(64) NOT NULL,
    ten character varying(128) NOT NULL,
    so_dien_thoai character varying(10) NOT NULL,
    email character varying(256) NOT NULL,
    tinh_thanh_pho character varying(64) NOT NULL,
    quan_huyen character varying(64) NOT NULL,
    duong character varying(64) NOT NULL,
    gioi_tinh boolean NOT NULL,
    password character varying(512) NOT NULL
);


ALTER TABLE public.nhan_vien OWNER TO jiag;

--
-- Name: nhan_vien_vai_tro; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.nhan_vien_vai_tro (
    vai_tro_id character varying(64) NOT NULL,
    nhan_vien_id bigint NOT NULL
);


ALTER TABLE public.nhan_vien_vai_tro OWNER TO jiag;

--
-- Name: phieu_dat_phong; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.phieu_dat_phong (
    id uuid NOT NULL,
    phong_hop_id uuid NOT NULL,
    nhan_vien_id bigint NOT NULL,
    ngay_dat timestamp with time zone NOT NULL,
    ngay_ket_thuc timestamp with time zone NOT NULL,
    thoi_luong_dat integer NOT NULL,
    trang_thai character varying(32) NOT NULL,
    loai character varying(50) NOT NULL,
    tong_chi_phi numeric NOT NULL
);


ALTER TABLE public.phieu_dat_phong OWNER TO jiag;

--
-- Name: phong_ban; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.phong_ban (
    id uuid NOT NULL,
    ten character varying(128) NOT NULL,
    truong_phong bigint NOT NULL,
    vi_tri text NOT NULL,
    so_luong smallint NOT NULL,
    so_dien_thoai character varying(10) NOT NULL,
    chi_nhanh_id uuid NOT NULL
);


ALTER TABLE public.phong_ban OWNER TO jiag;

--
-- Name: phong_hop; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.phong_hop (
    id uuid NOT NULL,
    loai character varying(32) NOT NULL,
    suc_chua integer NOT NULL,
    toa_nha character varying(64) NOT NULL,
    tang smallint NOT NULL,
    thiet_bi uuid NOT NULL,
    trang_thai character varying(50) NOT NULL,
    mo_ta text NOT NULL,
    chi_phi numeric NOT NULL,
    chi_nhanh_id uuid NOT NULL
);


ALTER TABLE public.phong_hop OWNER TO jiag;

--
-- Name: quyen; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.quyen (
    id character varying(64) NOT NULL,
    mo_ta text NOT NULL
);


ALTER TABLE public.quyen OWNER TO jiag;

--
-- Name: thiet_bi; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.thiet_bi (
    id uuid NOT NULL,
    ma_san_pham character varying(128) NOT NULL,
    ten character varying(128) NOT NULL,
    ngay_lap_dat timestamp with time zone NOT NULL,
    trang_thai_su_dung character varying(32) NOT NULL,
    tinh_trang character varying(32) NOT NULL,
    vi_tri_id uuid NOT NULL,
    loai_thiet_bi_id uuid NOT NULL
);


ALTER TABLE public.thiet_bi OWNER TO jiag;

--
-- Name: vai_tro; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.vai_tro (
    id character varying(64) NOT NULL,
    mo_ta text NOT NULL
);


ALTER TABLE public.vai_tro OWNER TO jiag;

--
-- Name: vai_tro_quyen; Type: TABLE; Schema: public; Owner: jiag
--

CREATE TABLE public.vai_tro_quyen (
    vai_tro_id character varying(64) NOT NULL,
    quyen_id character varying(64) NOT NULL
);


ALTER TABLE public.vai_tro_quyen OWNER TO jiag;

--
-- Name: chi_nhanh pk_chi_nhanh; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.chi_nhanh
    ADD CONSTRAINT pk_chi_nhanh PRIMARY KEY (id);


--
-- Name: chi_tiet_dich_vu pk_chitietdichvu; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.chi_tiet_dich_vu
    ADD CONSTRAINT pk_chitietdichvu PRIMARY KEY (dich_vu_id, thiet_bi_id);


--
-- Name: dich_vu pk_dichvu; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.dich_vu
    ADD CONSTRAINT pk_dichvu PRIMARY KEY (id);


--
-- Name: loai_thiet_bi pk_loaithietbi; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.loai_thiet_bi
    ADD CONSTRAINT pk_loaithietbi PRIMARY KEY (id);


--
-- Name: ngan_sach pk_ngan_sach; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.ngan_sach
    ADD CONSTRAINT pk_ngan_sach PRIMARY KEY (id);


--
-- Name: nhan_vien pk_nhan_vien; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.nhan_vien
    ADD CONSTRAINT pk_nhan_vien PRIMARY KEY (id);


--
-- Name: phieu_dat_phong pk_phieudatphong; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phieu_dat_phong
    ADD CONSTRAINT pk_phieudatphong PRIMARY KEY (id);


--
-- Name: phong_ban pk_phong_ban; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phong_ban
    ADD CONSTRAINT pk_phong_ban PRIMARY KEY (id);


--
-- Name: phong_hop pk_phonghop; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phong_hop
    ADD CONSTRAINT pk_phonghop PRIMARY KEY (id);


--
-- Name: quyen pk_quyen; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.quyen
    ADD CONSTRAINT pk_quyen PRIMARY KEY (id);


--
-- Name: thiet_bi pk_thietbi; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.thiet_bi
    ADD CONSTRAINT pk_thietbi PRIMARY KEY (id);


--
-- Name: vai_tro pk_vai_tro; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.vai_tro
    ADD CONSTRAINT pk_vai_tro PRIMARY KEY (id);


--
-- Name: nhan_vien_vai_tro pk_vaitroid_nhanvienid; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.nhan_vien_vai_tro
    ADD CONSTRAINT pk_vaitroid_nhanvienid PRIMARY KEY (vai_tro_id, nhan_vien_id);


--
-- Name: vai_tro_quyen pk_vaitroid_quyenid; Type: CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.vai_tro_quyen
    ADD CONSTRAINT pk_vaitroid_quyenid PRIMARY KEY (vai_tro_id, quyen_id);


--
-- Name: fk_chitietdichvu_dichvuid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_chitietdichvu_dichvuid_index ON public.chi_tiet_dich_vu USING btree (dich_vu_id);


--
-- Name: fk_chitietdichvu_thietbiid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_chitietdichvu_thietbiid_index ON public.chi_tiet_dich_vu USING btree (thiet_bi_id);


--
-- Name: fk_dichvu_phieudatphongid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_dichvu_phieudatphongid_index ON public.dich_vu USING btree (phieu_dat_phong_id);


--
-- Name: fk_ngansach_phongbanid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_ngansach_phongbanid_index ON public.ngan_sach USING btree (phong_ban_id);


--
-- Name: fk_nhanvien_phongbanid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_nhanvien_phongbanid_index ON public.nhan_vien USING btree (phong_ban_id);


--
-- Name: fk_nhanvienvaitro_nhanvie_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_nhanvienvaitro_nhanvie_index ON public.nhan_vien_vai_tro USING btree (nhan_vien_id);


--
-- Name: fk_nhanvienvaitro_vaitroid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_nhanvienvaitro_vaitroid_index ON public.nhan_vien_vai_tro USING btree (vai_tro_id);


--
-- Name: fk_phieudatphong_nhanvienid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_phieudatphong_nhanvienid_index ON public.phieu_dat_phong USING btree (nhan_vien_id);


--
-- Name: fk_phieudatphong_phonghopid; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_phieudatphong_phonghopid ON public.phieu_dat_phong USING btree (phong_hop_id);


--
-- Name: fk_phongban_chinhanhid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_phongban_chinhanhid_index ON public.phong_ban USING btree (chi_nhanh_id);


--
-- Name: fk_phonghop_chinhanhid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_phonghop_chinhanhid_index ON public.phong_hop USING btree (chi_nhanh_id);


--
-- Name: fk_thietbi_loaithietbiid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_thietbi_loaithietbiid_index ON public.thiet_bi USING btree (loai_thiet_bi_id);


--
-- Name: fk_thietbi_vitriid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_thietbi_vitriid_index ON public.thiet_bi USING btree (vi_tri_id);


--
-- Name: fk_vaitroquyen_quyenid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_vaitroquyen_quyenid_index ON public.vai_tro_quyen USING btree (quyen_id);


--
-- Name: fk_vaitroquyen_vaitroid_index; Type: INDEX; Schema: public; Owner: jiag
--

CREATE INDEX fk_vaitroquyen_vaitroid_index ON public.vai_tro_quyen USING btree (vai_tro_id);


--
-- Name: chi_tiet_dich_vu fk_chitietdichvu_dichvuid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.chi_tiet_dich_vu
    ADD CONSTRAINT fk_chitietdichvu_dichvuid FOREIGN KEY (dich_vu_id) REFERENCES public.dich_vu(id);


--
-- Name: chi_tiet_dich_vu fk_chitietdichvu_thietbiid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.chi_tiet_dich_vu
    ADD CONSTRAINT fk_chitietdichvu_thietbiid FOREIGN KEY (thiet_bi_id) REFERENCES public.thiet_bi(id);


--
-- Name: dich_vu fk_dichvu_phieudatphongid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.dich_vu
    ADD CONSTRAINT fk_dichvu_phieudatphongid FOREIGN KEY (phieu_dat_phong_id) REFERENCES public.phieu_dat_phong(id);


--
-- Name: ngan_sach fk_ngansach_phongbanid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.ngan_sach
    ADD CONSTRAINT fk_ngansach_phongbanid FOREIGN KEY (phong_ban_id) REFERENCES public.phong_ban(id);


--
-- Name: nhan_vien fk_nhanvien_phongbanid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.nhan_vien
    ADD CONSTRAINT fk_nhanvien_phongbanid FOREIGN KEY (phong_ban_id) REFERENCES public.phong_ban(id);


--
-- Name: nhan_vien_vai_tro fk_nhanvienvaitro_nhanvienid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.nhan_vien_vai_tro
    ADD CONSTRAINT fk_nhanvienvaitro_nhanvienid FOREIGN KEY (nhan_vien_id) REFERENCES public.nhan_vien(id);


--
-- Name: nhan_vien_vai_tro fk_nhanvienvaitro_vaitroid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.nhan_vien_vai_tro
    ADD CONSTRAINT fk_nhanvienvaitro_vaitroid FOREIGN KEY (vai_tro_id) REFERENCES public.vai_tro(id);


--
-- Name: phieu_dat_phong fk_phieudatphong_nhanvienid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phieu_dat_phong
    ADD CONSTRAINT fk_phieudatphong_nhanvienid FOREIGN KEY (nhan_vien_id) REFERENCES public.nhan_vien(id);


--
-- Name: phieu_dat_phong fk_phieudatphong_phonghopid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phieu_dat_phong
    ADD CONSTRAINT fk_phieudatphong_phonghopid FOREIGN KEY (phong_hop_id) REFERENCES public.phong_hop(id);


--
-- Name: phong_ban fk_phongban_chinhanhid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phong_ban
    ADD CONSTRAINT fk_phongban_chinhanhid FOREIGN KEY (chi_nhanh_id) REFERENCES public.chi_nhanh(id);


--
-- Name: phong_hop fk_phonghop_chinhanhid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.phong_hop
    ADD CONSTRAINT fk_phonghop_chinhanhid FOREIGN KEY (chi_nhanh_id) REFERENCES public.chi_nhanh(id);


--
-- Name: thiet_bi fk_thietbi_loaithietbiid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.thiet_bi
    ADD CONSTRAINT fk_thietbi_loaithietbiid FOREIGN KEY (loai_thiet_bi_id) REFERENCES public.loai_thiet_bi(id);


--
-- Name: thiet_bi fk_thietbi_vitriid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.thiet_bi
    ADD CONSTRAINT fk_thietbi_vitriid FOREIGN KEY (vi_tri_id) REFERENCES public.phong_hop(id);


--
-- Name: vai_tro_quyen fk_vaitroquyen_quyenid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.vai_tro_quyen
    ADD CONSTRAINT fk_vaitroquyen_quyenid FOREIGN KEY (quyen_id) REFERENCES public.quyen(id);


--
-- Name: vai_tro_quyen fk_vaitroquyen_vaitroid; Type: FK CONSTRAINT; Schema: public; Owner: jiag
--

ALTER TABLE ONLY public.vai_tro_quyen
    ADD CONSTRAINT fk_vaitroquyen_vaitroid FOREIGN KEY (vai_tro_id) REFERENCES public.vai_tro(id);


--
-- PostgreSQL database dump complete
--

