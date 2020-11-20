package com.yishang.xiaozhen.event;

import com.yishang.xiaozhen.entity.ActivityBooking;
import org.springframework.context.ApplicationEvent;


public class ActivityBookingEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private ActivityBooking activityBooking;

    public ActivityBookingEvent(Object source,ActivityBooking activityBooking) {
        super(source);
        this.activityBooking = activityBooking;
    }

    public ActivityBooking getActivityBooking() {
        return activityBooking;
    }

    public void setActivityBooking(ActivityBooking activityBooking) {
        this.activityBooking = activityBooking;
    }
}
