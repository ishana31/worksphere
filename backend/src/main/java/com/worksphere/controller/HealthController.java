package com.worksphere.controller;

import com.worksphere.api.HealthApi;
import com.worksphere.api.dto.HealthStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthApi {

    @Override
    public ResponseEntity<HealthStatus> healthCheck() {
        HealthStatus status = new HealthStatus();
        status.setStatus("UP");
        return ResponseEntity.ok(status);
    }
}
