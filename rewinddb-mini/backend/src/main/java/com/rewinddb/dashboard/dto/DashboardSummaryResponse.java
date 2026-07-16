package com.rewinddb.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    private long connectionCount;
    private long triggerCount;
    private long capturedChangeCount;
    private long rollbackCount;
}
