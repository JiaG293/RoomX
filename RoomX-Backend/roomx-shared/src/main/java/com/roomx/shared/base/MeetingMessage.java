package com.roomx.shared.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingMessage {
    private String title;
    private String message;
    private String senderId;
    private String recipientId;
    private Instant createdAt;
    private Instant updatedAt;
}
