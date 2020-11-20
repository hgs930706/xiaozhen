package com.yishang.xiaozhen.event;

import com.yishang.xiaozhen.entity.MeetingAreaBooking;
import org.springframework.context.ApplicationEvent;


public class MeetingAreaBookingEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private MeetingAreaBooking meetingAreaBooking;

    public MeetingAreaBookingEvent(Object source, MeetingAreaBooking meetingAreaBooking) {
        super(source);
        this.meetingAreaBooking = meetingAreaBooking;
    }


    public MeetingAreaBooking getMeetingAreaBooking() {
        return meetingAreaBooking;
    }

    public void setMeetingAreaBooking(MeetingAreaBooking meetingAreaBooking) {
        this.meetingAreaBooking = meetingAreaBooking;
    }
}
