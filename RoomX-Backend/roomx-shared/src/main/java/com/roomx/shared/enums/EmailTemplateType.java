package com.roomx.shared.enums;


public enum EmailTemplateType {
    CONFIRM_MEETING("confirm-meeting-booking"),
    MEETING_AFTER_TIME("meeting-after-time"),
    REMINDER_MEETING_TODAY("reminder-meeting-today"),
    UPDATE_MEETING("update-meeting"),
    APPROVAL_CONFLICT_BOOKING("approval-conflict-booking"),
    ACCEPT_CONFLICT_BOOKING("accept-conflict-booking");
    private final String fileName;

    EmailTemplateType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return fileName;
    }


    public String getEnum() {
        return name();
    }
}
