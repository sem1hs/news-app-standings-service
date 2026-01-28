package com.semihsahinoglu.standing_service.service;

import com.semihsahinoglu.standing_service.client.LeagueClient;
import com.semihsahinoglu.standing_service.client.TeamClient;
import com.semihsahinoglu.standing_service.dto.*;
import com.semihsahinoglu.standing_service.entity.Standing;
import com.semihsahinoglu.standing_service.exception.LeagueNotFoundException;
import com.semihsahinoglu.standing_service.exception.StandingAlreadyExistException;
import com.semihsahinoglu.standing_service.exception.StandingNotFoundException;
import com.semihsahinoglu.standing_service.exception.TeamNotFoundException;
import com.semihsahinoglu.standing_service.mapper.StandingMapper;
import com.semihsahinoglu.standing_service.repository.StandingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class StandingService {

    private final LeagueTeamQueryService leagueTeamQueryService;
    private final TeamClient teamClient;
    private final LeagueClient leagueClient;
    private final StandingRepository standingRepository;
    private final StandingMapper standingMapper;
    private final Executor apiExecutor;

    public StandingService(LeagueTeamQueryService leagueTeamQueryService, TeamClient teamClient, LeagueClient leagueClient, StandingRepository standingRepository, StandingMapper standingMapper, Executor apiExecutor) {
        this.leagueTeamQueryService = leagueTeamQueryService;
        this.teamClient = teamClient;
        this.leagueClient = leagueClient;
        this.standingRepository = standingRepository;
        this.standingMapper = standingMapper;
        this.apiExecutor = apiExecutor;
    }

    public List<StandingResponse> getLeagueTable(Long leagueId) {
        List<Standing> standings = standingRepository.findByLeagueIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(leagueId);
        LeagueResponse league = leagueClient.findLeagueById(leagueId);

        if (league == null) throw new LeagueNotFoundException("Lig bulunamadı");

        List<CompletableFuture<TeamResponse>> teamFutures = standings.stream().map(standing -> CompletableFuture.supplyAsync(() -> teamClient.findTeamById(standing.getTeamId()), apiExecutor)).toList();

        CompletableFuture.allOf(teamFutures.toArray(new CompletableFuture[0])).join();

        Map<Long, String> teamNameMap = teamFutures.stream().map(CompletableFuture::join).filter(team -> team != null).collect(Collectors.toMap(TeamResponse::id, TeamResponse::name));
        String leagueName = league.name();


        return standings.stream().map(standing -> {
                    String teamName = teamNameMap.get(standing.getTeamId());

                    if (teamName == null) throw new TeamNotFoundException("Takım bulunamadı: " + standing.getTeamId());

                    LeagueAndTeamName meta = new LeagueAndTeamName(leagueName, teamName);

                    return standingMapper.toDto(standing, meta);
                })
                .toList();

    }

    public StandingResponse getStanding(Long leagueId, Long teamId) {
        LeagueAndTeamName leagueAndTeamName = leagueTeamQueryService.fetchLeagueAndTeamName(leagueId, teamId);
        Standing standing = standingRepository.findByLeagueIdAndTeamId(leagueId, teamId).orElseThrow(() -> new StandingNotFoundException("Puan Tablosu bulunamadı"));

        return standingMapper.toDto(standing, leagueAndTeamName);
    }

    public StandingResponse createStanding(StandingCreateRequest standingCreateRequest) {
        leagueTeamQueryService.validateLeagueAndTeamExists(standingCreateRequest.leagueId(), standingCreateRequest.teamId());

        boolean exists = standingRepository.findByLeagueIdAndTeamId(standingCreateRequest.leagueId(), standingCreateRequest.teamId()).isPresent();

        if (exists) throw new StandingAlreadyExistException("Bu takım için zaten puan durumu mevcut");

        LeagueAndTeamName leagueAndTeamName = leagueTeamQueryService.fetchLeagueAndTeamName(standingCreateRequest.leagueId(), standingCreateRequest.teamId());

        Standing standing = standingMapper.toEntity(standingCreateRequest);
        Standing saved = standingRepository.save(standing);

        return standingMapper.toDto(saved, leagueAndTeamName);
    }

    public StandingResponse updateStanding(Long leagueId, Long teamId, StandingUpdateRequest standingUpdateRequest) {
        LeagueAndTeamName leagueAndTeamName = leagueTeamQueryService.fetchLeagueAndTeamName(leagueId, teamId);

        Standing standing = standingRepository.findByLeagueIdAndTeamId(leagueId, teamId).orElseThrow(() -> new StandingNotFoundException("Puan durumu bulunamadı"));
        standing.updateEntity(standingUpdateRequest);
        Standing updated = standingRepository.save(standing);

        return standingMapper.toDto(updated, leagueAndTeamName);
    }

    public void deleteStanding(Long leagueId, Long teamId) {
        Standing standing = standingRepository.findByLeagueIdAndTeamId(leagueId, teamId).orElseThrow(() -> new StandingNotFoundException("Puan durumu bulunamadı"));
        standingRepository.delete(standing);
    }

}
