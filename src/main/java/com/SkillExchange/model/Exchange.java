package com.SkillExchange.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "exchanges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    @Id
    private String id;

    private String requesterId;
    private String providerId;

    private String skillOffered;
    private String skillRequested;

    private String status = "Pending"; // Pending, Accepted, Completed, Cancelled
    private LocalDate startDate;
    private LocalDate endDate;
    private String progress;
    private int karmaTransfer = 0;
    private String feedbackId;
}
