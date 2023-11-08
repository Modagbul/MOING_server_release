package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionRecommendRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.mission.exception.NoAccessCreateMission;
import com.moing.backend.domain.mission.exception.NoMoreCreateMission;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateUseCase {

    private final MissionSaveService missionSaveService;
    private final MissionQueryService missionQueryService;
    private final TeamGetService teamGetService;
    private final MemberGetService memberGetService;

    public MissionCreateRes createMission(String userSocialId, Long teamId, MissionReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Team team = teamGetService.getTeamByTeamId(teamId);

        // 소모임 장 확인
        if (member.getMemberId().equals(team.getLeaderId())) {
            Mission mission = MissionMapper.mapToMission(missionReq, member, MissionStatus.ONGOING);
            // teamRepository 변경 예정

            if (missionQueryService.isAbleCreateRepeatMission(team.getTeamId())) {
                throw new NoMoreCreateMission();
            }
            mission.setTeam(team);
            missionSaveService.save(mission);
            return MissionMapper.mapToMissionCreateRes(mission);
        }
        else{
            throw new NoAccessCreateMission();
        }




    }

    public MissionRecommendRes getCategoryByTeam(Long teamId) {
        Team team = teamGetService.getTeamByTeamId(teamId);

        return MissionRecommendRes.builder()
                .category(team.getCategory().name())
                .build();
    }

}
