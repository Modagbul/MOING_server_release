package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FinishMissionBoardRes {
    private Long missionId;
    private String dueTo; // 날짜
    private String title;
    private String status;
    private String missionType;

}
