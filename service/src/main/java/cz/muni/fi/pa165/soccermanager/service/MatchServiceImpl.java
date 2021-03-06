package cz.muni.fi.pa165.soccermanager.service;

import cz.muni.fi.pa165.soccermanager.dao.MatchDao;
import cz.muni.fi.pa165.soccermanager.entity.Match;
import cz.muni.fi.pa165.soccermanager.entity.Team;
import cz.muni.fi.pa165.soccermanager.enums.StadiumEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the MatchService. The class is the part of implementation of
 * the business logic of the application.
 * @author 456519 Filip Lux
 * @version 11/23/2017.
 */

@Service
public class MatchServiceImpl implements MatchService {

    @Inject
    private MatchDao matchDao;

    @Override
    public Match fetchById(long matchId) {
        return matchDao.fetchById(matchId);
    }

    @Override
    public List<Match> fetchAll() {
        return matchDao.fetchAll();
    }

    @Override
    public List<Match> fetchByDate(LocalDate date) {
        return matchDao.fetchByDate(date);
    }

    @Override
    public List<Match> fetchByTeam(Team team) {
        return matchDao.fetchByTeam(team);
    }

    @Override
    public List<Match> fetchFinished() {return matchDao.fetchFinishedMatches();}

    @Override
    public List<Match> fetchByStadium(StadiumEnum stadium) {
        return matchDao.fetchByStadium(stadium);
    }

    @Override
    public Match createMatch(Match match) {
        matchDao.insert(match);
        return match;
    }

    @Override
    public boolean isFinished(Match match) {
        return match.isFinished();
    }

    @Override
    public void updateMatch(Match match) {
        matchDao.update(match);
    }

    @Override
    public void removeMatch(Long matchId) {
        matchDao.delete(matchId);

    }
}
