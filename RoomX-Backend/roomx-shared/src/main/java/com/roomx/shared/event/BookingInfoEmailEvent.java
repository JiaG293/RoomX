package com.roomx.shared.event;

import com.roomx.shared.base.MeetingMessage;
import com.roomx.shared.enums.EmailTemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingInfoEmailEvent {
    private String id;
    private String userId;
    private String bookingId;
    private String bookingRequestId;
    private String toEmail;
    private String fromEmail;
    private String participantName;
    private String roomName;
    private String branchName;
    private LocalDate meetingDate;
    private String meetingLocation;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private String meetingTitle;
    private String meetingDescription;
    private EmailTemplateType emailType;

    public String getDuration(){
        return Duration.between(meetingStart, meetingEnd).toMinutes() + " phút";
    }

}
