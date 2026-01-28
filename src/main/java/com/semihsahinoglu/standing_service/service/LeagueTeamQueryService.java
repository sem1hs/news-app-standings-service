package com.semihsahinoglu.standing_service.service;

import com.semihsahinoglu.standing_service.client.LeagueClient;
import com.semihsahinoglu.standing_service.client.TeamClient;
import com.semihsahinoglu.standing_service.dto.LeagueAndTeamName;
import com.semihsahinoglu.standing_service.dto.LeagueResponse;
import com.semihsahinoglu.standing_service.dto.TeamResponse;
import com.semihsahinoglu.standing_service.exception.LeagueNotFoundException;
import com.semihsahinoglu.standing_service.exception.TeamNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class LeagueTeamQueryService {

    private final LeagueClient leagueClient;
    private final TeamClient teamClient;
    private final Executor apiExecutor;

    public LeagueTeamQueryService(LeagueClient leagueClient, TeamClient teamClient, Executor apiExecutor) {
        this.leagueClient = leagueClient;
        this.teamClient = teamClient;
        this.apiExecutor = apiExecutor;
    }

    public LeagueAndTeamName fetchLeagueAndTeamName(Long leagueId, Long teamId) {
        CompletableFuture<LeagueResponse> leagueFuture = CompletableFuture.supplyAsync(() -> leagueClient.findLeagueById(leagueId), apiExecutor);
        CompletableFuture<TeamResponse> teamFuture = CompletableFuture.supplyAsync(() -> teamClient.findTeamById(teamId), apiExecutor);

        CompletableFuture.allOf(leagueFuture, teamFuture).join();

        TeamResponse team = teamFuture.join();
        LeagueResponse league = leagueFuture.join();

        if (league == null) throw new LeagueNotFoundException("Lig bulunamadı !");
        if (team == null) throw new TeamNotFoundException("Takım bulunamadı !");

        return new LeagueAndTeamName(league.name(), team.name());
    }

    public void validateLeagueAndTeamExists(Long leagueId, Long teamId) {

        CompletableFuture<Boolean> leagueExistsFuture = CompletableFuture.supplyAsync(() -> leagueClient.existsById(leagueId), apiExecutor);

        CompletableFuture<Boolean> teamExistsFuture = CompletableFuture.supplyAsync(() -> teamClient.existsById(teamId), apiExecutor);

        CompletableFuture.allOf(leagueExistsFuture, teamExistsFuture).join();

        Boolean leagueExists = leagueExistsFuture.join();
        Boolean teamExists = teamExistsFuture.join();

        if (!leagueExists) throw new LeagueNotFoundException("Lig bulunamadı !");
        if (!teamExists) throw new TeamNotFoundException("Takım bulunamadı !");

    }

}
