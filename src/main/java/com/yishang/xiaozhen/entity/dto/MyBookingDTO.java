package com.yishang.xiaozhen.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MyBookingDTO {

    private String id;

    private String name;

    private String bookingType;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingTime;

    private String bookingAddress;

    private String bookingPerson;

    private String bookingContent;

    private String images;


}
