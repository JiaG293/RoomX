package com.roomx.shared.enums;


public enum EmailTemplateType {
    CONFIRM_MEETING("confirm-meeting-booking"),
    MEETING_AFTER_TIME ("meeting-after-time"),
    REMINDER_MEETING_TODAY("reminder-meeting-today"),
    UPDATE_MEETING("update-meeting");
    private final String fileName;

    EmailTemplateType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }


}
