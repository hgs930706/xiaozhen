package com.yishang.xiaozhen.event;

import com.yishang.xiaozhen.entity.ReceiveBooking;
import org.springframework.context.ApplicationEvent;


public class ReceiveBookingEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private ReceiveBooking receiveBooking;

    public ReceiveBookingEvent(Object source, ReceiveBooking receiveBooking) {
        super(source);
        this.receiveBooking = receiveBooking;
    }


    public ReceiveBooking getReceiveBooking() {
        return receiveBooking;
    }

    public void setReceiveBooking(ReceiveBooking receiveBooking) {
        this.receiveBooking = receiveBooking;
    }
}
