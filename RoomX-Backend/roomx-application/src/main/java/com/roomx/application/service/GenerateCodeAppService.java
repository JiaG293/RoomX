package com.roomx.application.service;

import com.roomx.shared.exception.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateCodeAppService {
    public String generateBookingCode(LocalDate meetingDate) {

      /* if(isAuto){
           String formattedDate = meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
           String nanoTimePart = Long.toString(System.nanoTime(), 36).toUpperCase();

           while (nanoTimePart.length() < 24) {
               nanoTimePart = "0" + nanoTimePart;
           }

           return formattedDate + "-" + nanoTimePart;
       } else if(!isAuto) {
           return null;
       } else{
           throw new IllegalArgumentException("Invalid value for isAuto.");
       }*/
        return null;
    }

    public String generateBranchName(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomName(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomNumber(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomClassCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomEquipmentCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomServiceCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateRoomCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateUserCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generateGroupCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }

    public String generatePlaceCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }











}
