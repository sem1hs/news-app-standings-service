package com.semihsahinoglu.standing_service.controller;

import com.semihsahinoglu.standing_service.dto.StandingUpdateFromFixtureRequest;
import com.semihsahinoglu.standing_service.service.StandingInternalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/standing")
public class StandingInternalController {

    private final StandingInternalService standingInternalService;

    public StandingInternalController(StandingInternalService standingInternalService) {
        this.standingInternalService = standingInternalService;
    }

    @PostMapping("/update-from-fixture")
    public ResponseEntity<Void> updateFromFixture(@RequestBody StandingUpdateFromFixtureRequest standingUpdateFromFixtureRequest) {
        standingInternalService.applyMatchResultFromFixture(standingUpdateFromFixtureRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
