CREATE  TABLE branch (
                         branch_id            uuid  NOT NULL  ,
                         name                 varchar(500)    ,
                         phone_number         varchar(500)    ,
                         email                varchar(500)    ,
                         address              varchar(500)    ,
                         branch_code          varchar(32)    ,
                         created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                         updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                         status               varchar    ,
                         CONSTRAINT pk_chi_nhanh PRIMARY KEY ( branch_id ),
                         CONSTRAINT unq_branch UNIQUE ( branch_code )
);

CREATE  TABLE equipment (
                            equipment_id         uuid  NOT NULL  ,
                            name                 varchar(500)  NOT NULL  ,
                            brand                varchar    ,
                            description          text    ,
                            created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                            updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                            equipment_code       varchar(32)    ,
                            status               varchar(32)    ,
                            image_urls           varchar[]    ,
                            CONSTRAINT pk_thiet_bi PRIMARY KEY ( equipment_id ),
                            CONSTRAINT unq_equipment UNIQUE ( equipment_code )
);

CREATE  TABLE equipment_price_history (
                                          equipment_price_history_id uuid  NOT NULL  ,
                                          equipment_id         uuid  NOT NULL  ,
                                          valid_from           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                                          unit_price           numeric    ,
                                          valid_end            timestamp    ,
                                          CONSTRAINT pk_equipment_price_history PRIMARY KEY ( equipment_price_history_id )
);

CREATE  TABLE exception_date (
                                 exception_date_id    uuid  NOT NULL  ,
                                 start_date           timestamp    ,
                                 end_date             timestamp    ,
                                 description          text    ,
                                 name                 varchar(500)    ,
                                 exception_date_type  varchar(32)  NOT NULL  ,
                                 CONSTRAINT pk_ngay_ngoai_le PRIMARY KEY ( exception_date_id )
);

CREATE  TABLE image_url (
                            image_url_id         uuid  NOT NULL  ,
                            "type"               varchar(64)    ,
                            entity_id            uuid    ,
                            url                  varchar    ,
                            "order"              integer    ,
                            CONSTRAINT pk_image_url PRIMARY KEY ( image_url_id )
);

CREATE  TABLE place (
                        place_id             uuid  NOT NULL  ,
                        branch_id            uuid    ,
                        name                 varchar    ,
                        layout               varchar    ,
                        place_type           varchar(32)  NOT NULL  ,
                        parent_id            uuid    ,
                        status               varchar(32)    ,
                        code                 varchar(32)    ,
                        CONSTRAINT pk_vi_tri PRIMARY KEY ( place_id )
);

CREATE  TABLE recurrence (
                             recurrence_id        uuid  NOT NULL  ,
                             booking_request_id   uuid    ,
                             recurrence_type      varchar(32)  NOT NULL  ,
                             "interval"           smallint    ,
                             start_date           date    ,
                             end_date             date    ,
                             time_start           time    ,
                             time_end             time    ,
                             days_of_week         varchar    ,
                             CONSTRAINT pk_chu_ky PRIMARY KEY ( recurrence_id ),
                             CONSTRAINT unq_recurrence_booking_request_id UNIQUE ( booking_request_id )
);

CREATE  TABLE "role" (
                         role_id              varchar(64)  NOT NULL  ,
                         description          text    ,
                         "level"              smallint    ,
                         CONSTRAINT pk_tbl PRIMARY KEY ( role_id )
);

CREATE  TABLE room_class (
                             capacity             integer    ,
                             room_class_id        uuid  NOT NULL  ,
                             created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                             updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                             room_class_code      varchar(32)    ,
                             status               varchar(32)    ,
                             CONSTRAINT pk_loai_phong PRIMARY KEY ( room_class_id ),
                             CONSTRAINT unq_room_class UNIQUE ( room_class_code )
);

CREATE  TABLE room_class_price_history (
                                           room_class_price_history uuid  NOT NULL  ,
                                           room_class_id        uuid  NOT NULL  ,
                                           valid_from           timestamp    ,
                                           base_price           numeric    ,
                                           total_price          numeric    ,
                                           valid_end            timestamp    ,
                                           CONSTRAINT pk_room_class_price_history PRIMARY KEY ( room_class_price_history )
);

CREATE  TABLE service (
                          service_id           uuid  NOT NULL  ,
                          name                 varchar(500)    ,
                          description          varchar(500)    ,
                          note                 varchar(500)    ,
                          created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                          updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                          service_code         varchar(32)    ,
                          status               varchar    ,
                          image_urls           varchar[]    ,
                          CONSTRAINT pk_dich_vu PRIMARY KEY ( service_id ),
                          CONSTRAINT unq_service UNIQUE ( service_code )
);

CREATE  TABLE service_price_history (
                                        service_price_history_id uuid  NOT NULL  ,
                                        service_id           uuid  NOT NULL  ,
                                        unit_price           numeric    ,
                                        valid_start          timestamp    ,
                                        valid_end            timestamp    ,
                                        CONSTRAINT pk_service_price_history PRIMARY KEY ( service_price_history_id )
);

CREATE  TABLE service_room_class (
                                     room_class_id        uuid  NOT NULL  ,
                                     service_id           uuid  NOT NULL  ,
                                     quantity             smallint    ,
                                     CONSTRAINT pk_loai_phong_dich_vu PRIMARY KEY ( room_class_id, service_id )
);

CREATE  TABLE "user" (
                         first_name           varchar(128)    ,
                         last_name            varchar(64)    ,
                         phone_number         varchar(10)    ,
                         email                varchar(500)  NOT NULL  ,
                         gender               boolean    ,
                         avatar_image         varchar    ,
                         user_type            varchar(32)  NOT NULL  ,
                         user_code            varchar(32)  NOT NULL  ,
                         user_id              uuid  NOT NULL  ,
                         created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                         updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                         enable               boolean    ,
                         status               varchar(32)    ,
                         CONSTRAINT pk_nguoi_dung PRIMARY KEY ( user_id ),
                         CONSTRAINT unq_nguoi_dung UNIQUE ( user_code, email, phone_number )
);

CREATE  TABLE user_role (
                            user_id              uuid  NOT NULL  ,
                            role_id              varchar(64)  NOT NULL  ,
                            CONSTRAINT pk_user_role PRIMARY KEY ( user_id, role_id )
);

CREATE  TABLE equipment_room_class (
                                       room_class_id        uuid  NOT NULL  ,
                                       equipment_id         uuid  NOT NULL  ,
                                       quantity             smallint    ,
                                       CONSTRAINT pk_loai_phong_thiet_bi PRIMARY KEY ( room_class_id, equipment_id )
);

CREATE  TABLE "group" (
                          group_id             uuid  NOT NULL  ,
                          name                 varchar(500)    ,
                          group_type           varchar(32)  NOT NULL  ,
                          branch_id            uuid    ,
                          user_id              uuid    ,
                          group_code           varchar(32)    ,
                          status               varchar(32)    ,
                          CONSTRAINT pk_nhom PRIMARY KEY ( group_id ),
                          CONSTRAINT unq_group UNIQUE ( group_code )
);

CREATE  TABLE group_member (
                               user_id              uuid  NOT NULL  ,
                               group_id             uuid  NOT NULL  ,
                               CONSTRAINT pk_thanh_vien_nhom PRIMARY KEY ( user_id, group_id )
);

CREATE  TABLE approval_form (
                                approval_form_id     uuid  NOT NULL  ,
                                approver_id          uuid  NOT NULL  ,
                                booking_request_id   uuid  NOT NULL  ,
                                status               varchar(32)    ,
                                note                 text    ,
                                created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                                updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                                CONSTRAINT pk_don_duyet PRIMARY KEY ( approval_form_id )
);

CREATE  TABLE booking (
                          booking_id           uuid  NOT NULL  ,
                          booking_code         varchar(32)  NOT NULL  ,
                          booking_request_id   uuid  NOT NULL  ,
                          room_id              uuid  NOT NULL  ,
                          meeting_start        time    ,
                          meeting_end          time    ,
                          "count"              smallint    ,
                          total_price          numeric    ,
                          status               varchar(32)    ,
                          created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                          updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                          previous_room_id     uuid    ,
                          meeting_date         date    ,
                          CONSTRAINT pk_booking_order PRIMARY KEY ( booking_id ),
                          CONSTRAINT unq_booking_room_id UNIQUE ( room_id ) ,
                          CONSTRAINT unq_booking UNIQUE ( booking_code )
);

CREATE INDEX idx_booking ON booking USING  btree ( meeting_start, meeting_end );

CREATE  TABLE booking_equipment (
                                    equipment_id         uuid  NOT NULL  ,
                                    booking_id           uuid  NOT NULL  ,
                                    quantity             smallint    ,
                                    CONSTRAINT pk_booking_equipment PRIMARY KEY ( equipment_id, booking_id )
);

CREATE  TABLE booking_participant (
                                      booking_id           uuid  NOT NULL  ,
                                      user_id              uuid  NOT NULL  ,
                                      CONSTRAINT pk_booking_participant PRIMARY KEY ( booking_id, user_id )
);

CREATE  TABLE booking_request (
                                  booking_request_id   uuid  NOT NULL  ,
                                  room_id              uuid  NOT NULL  ,
                                  status               varchar(32)    ,
                                  requester_id         uuid  NOT NULL  ,
                                  priority             smallint    ,
                                  created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                                  updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
                                  booking_request_code varchar(32)    ,
                                  end_date_approval    timestamp    ,
                                  CONSTRAINT pk_yeu_cau_dat_phong PRIMARY KEY ( booking_request_id ),
                                  CONSTRAINT unq_booking_request UNIQUE ( booking_request_code )
);

CREATE  TABLE booking_request_participant (
                                              user_id              uuid  NOT NULL  ,
                                              booking_request_id   uuid  NOT NULL  ,
                                              CONSTRAINT pk_booking_request_participant PRIMARY KEY ( user_id, booking_request_id )
);

CREATE  TABLE booking_service (
                                  booking_id           uuid  NOT NULL  ,
                                  service_id           uuid  NOT NULL  ,
                                  quantity             smallint    ,
                                  CONSTRAINT pk_booking_service PRIMARY KEY ( booking_id, service_id )
);

CREATE  TABLE equipment_request (
                                    quantity             smallint    ,
                                    equipment_id         uuid  NOT NULL  ,
                                    booking_request_id   uuid  NOT NULL  ,
                                    CONSTRAINT pk_yeu_cau_thiet_bi PRIMARY KEY ( booking_request_id, equipment_id )
);

CREATE  TABLE room (
                       room_id              uuid  NOT NULL  ,
                       place_id             uuid  NOT NULL  ,
                       status               varchar(32)    ,
                       description          text    ,
                       room_class_id        uuid  NOT NULL  ,
                       room_code            varchar(32)    ,
                       image_urls           varchar[]    ,
                       CONSTRAINT pk_phong_hop PRIMARY KEY ( room_id ),
                       CONSTRAINT unq_phong_hop_ma_phong UNIQUE ( room_code )
);

CREATE  TABLE service_request (
                                  quantity             smallint    ,
                                  service_id           uuid  NOT NULL  ,
                                  booking_request_id   uuid  NOT NULL  ,
                                  CONSTRAINT pk_yeu_cau_dich_vu PRIMARY KEY ( booking_request_id, service_id )
);

ALTER TABLE approval_form ADD CONSTRAINT fk_don_duyet_don_yeu_cau FOREIGN KEY ( booking_request_id ) REFERENCES booking_request( booking_request_id );

ALTER TABLE approval_form ADD CONSTRAINT fk_don_duyet_nguoi_dung FOREIGN KEY ( approver_id ) REFERENCES "user"( user_id );

ALTER TABLE booking ADD CONSTRAINT fk_booking_booking_request FOREIGN KEY ( booking_request_id ) REFERENCES booking_request( booking_request_id );

ALTER TABLE booking_equipment ADD CONSTRAINT fk_booking_equipment_equipment FOREIGN KEY ( equipment_id ) REFERENCES equipment( equipment_id );

ALTER TABLE booking_equipment ADD CONSTRAINT fk_booking_equipment_booking FOREIGN KEY ( booking_id ) REFERENCES booking( booking_id );

ALTER TABLE booking_participant ADD CONSTRAINT fk_booking_participant_user FOREIGN KEY ( user_id ) REFERENCES "user"( user_id );

ALTER TABLE booking_participant ADD CONSTRAINT fk_booking_participant_booking FOREIGN KEY ( booking_id ) REFERENCES booking( booking_id );

ALTER TABLE booking_request ADD CONSTRAINT fk_don_yeu_cau_nguoi_dung FOREIGN KEY ( requester_id ) REFERENCES "user"( user_id );

ALTER TABLE booking_request ADD CONSTRAINT fk_yeu_cau_dat_phong_phong_hop FOREIGN KEY ( room_id ) REFERENCES room( room_id );

ALTER TABLE booking_request ADD CONSTRAINT fk_booking_request_recurrence FOREIGN KEY ( booking_request_id ) REFERENCES recurrence( booking_request_id );

ALTER TABLE booking_request_participant ADD CONSTRAINT fk_booking_request_participant_booking_request FOREIGN KEY ( booking_request_id ) REFERENCES booking_request( booking_request_id );

ALTER TABLE booking_request_participant ADD CONSTRAINT fk_booking_request_participant_user FOREIGN KEY ( user_id ) REFERENCES "user"( user_id );

ALTER TABLE booking_service ADD CONSTRAINT fk_booking_service_service FOREIGN KEY ( service_id ) REFERENCES service( service_id );

ALTER TABLE booking_service ADD CONSTRAINT fk_booking_service_booking FOREIGN KEY ( booking_id ) REFERENCES booking( booking_id );

ALTER TABLE equipment_price_history ADD CONSTRAINT fk_equipment_price_history_equipment FOREIGN KEY ( equipment_id ) REFERENCES equipment( equipment_id );

ALTER TABLE equipment_request ADD CONSTRAINT fk_yeu_cau_thiet_bi_don_yeu_cau FOREIGN KEY ( booking_request_id ) REFERENCES booking_request( booking_request_id );

ALTER TABLE equipment_request ADD CONSTRAINT fk_yeu_cau_thiet_bi_thiet_bi FOREIGN KEY ( equipment_id ) REFERENCES equipment( equipment_id );

ALTER TABLE equipment_room_class ADD CONSTRAINT fk_loai_phong_thiet_bi_thiet_bi FOREIGN KEY ( equipment_id ) REFERENCES equipment( equipment_id );

ALTER TABLE equipment_room_class ADD CONSTRAINT fk_loai_phong_thiet_bi_loai_phong FOREIGN KEY ( room_class_id ) REFERENCES room_class( room_class_id );

ALTER TABLE "group" ADD CONSTRAINT fk_nhom_nguoi_dung FOREIGN KEY ( user_id ) REFERENCES "user"( user_id );

ALTER TABLE "group" ADD CONSTRAINT fk_nhom_chi_nhanh FOREIGN KEY ( branch_id ) REFERENCES branch( branch_id );

ALTER TABLE group_member ADD CONSTRAINT fk_thanh_vien_nhom_nhom FOREIGN KEY ( group_id ) REFERENCES "group"( group_id );

ALTER TABLE group_member ADD CONSTRAINT fk_thanh_vien_nhom_nguoi_dung FOREIGN KEY ( user_id ) REFERENCES "user"( user_id );

ALTER TABLE place ADD CONSTRAINT fk_vi_tri_chi_nhanh FOREIGN KEY ( branch_id ) REFERENCES branch( branch_id );

ALTER TABLE room ADD CONSTRAINT fk_phong_hop_loai_phong FOREIGN KEY ( room_class_id ) REFERENCES room_class( room_class_id );

ALTER TABLE room ADD CONSTRAINT fk_phong_hop_vi_tri FOREIGN KEY ( place_id ) REFERENCES place( place_id );

ALTER TABLE room ADD CONSTRAINT fk_room_booking FOREIGN KEY ( room_id ) REFERENCES booking( room_id );

ALTER TABLE room_class_price_history ADD CONSTRAINT fk_room_class_price_history_room_class FOREIGN KEY ( room_class_id ) REFERENCES room_class( room_class_id );

ALTER TABLE service_price_history ADD CONSTRAINT fk_service_price_history_service FOREIGN KEY ( service_id ) REFERENCES service( service_id );

ALTER TABLE service_request ADD CONSTRAINT fk_yeu_cau_dich_vu_don_yeu_cau FOREIGN KEY ( booking_request_id ) REFERENCES booking_request( booking_request_id );

ALTER TABLE service_request ADD CONSTRAINT fk_yeu_cau_dich_vu_dich_vu FOREIGN KEY ( service_id ) REFERENCES service( service_id );

ALTER TABLE service_room_class ADD CONSTRAINT fk_loai_phong_dich_vu_dich_vu FOREIGN KEY ( service_id ) REFERENCES service( service_id );

ALTER TABLE service_room_class ADD CONSTRAINT fk_loai_phong_dich_vu_loai_phong FOREIGN KEY ( room_class_id ) REFERENCES room_class( room_class_id );

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_user FOREIGN KEY ( user_id ) REFERENCES "user"( user_id );

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role FOREIGN KEY ( role_id ) REFERENCES "role"( role_id );

