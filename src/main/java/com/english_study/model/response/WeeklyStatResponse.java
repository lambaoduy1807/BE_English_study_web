package com.english_study.model.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class WeeklyStatResponse {
    private LocalDate date;
    private int numMemorizeNew;
}
