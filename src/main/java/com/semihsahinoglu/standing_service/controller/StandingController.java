package com.semihsahinoglu.standing_service.controller;

import com.semihsahinoglu.standing_service.dto.ApiResponse;
import com.semihsahinoglu.standing_service.dto.StandingCreateRequest;
import com.semihsahinoglu.standing_service.dto.StandingResponse;
import com.semihsahinoglu.standing_service.dto.StandingUpdateRequest;
import com.semihsahinoglu.standing_service.service.StandingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/standing")
public class StandingController {

    private final StandingService standingService;

    public StandingController(StandingService standingService) {
        this.standingService = standingService;
    }

    @GetMapping
    public ResponseEntity<List<StandingResponse>> getLeagueTable(@RequestParam Long leagueId) {
        List<StandingResponse> response = standingService.getLeagueTable(leagueId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{leagueId}/{teamId}")
    public ResponseEntity<ApiResponse> getByLeagueAndTeamId(@PathVariable Long leagueId, @PathVariable Long teamId) {
        StandingResponse response = standingService.getStanding(leagueId, teamId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid StandingCreateRequest request) {
        StandingResponse response = standingService.createStanding(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{leagueId}/{teamId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long leagueId, @PathVariable Long teamId, @RequestBody  StandingUpdateRequest request) {
        StandingResponse response = standingService.updateStanding(leagueId, teamId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{leagueId}/{teamId}")
    public ResponseEntity<Void> delete(@PathVariable Long leagueId, @PathVariable Long teamId) {
        standingService.deleteStanding(leagueId, teamId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
