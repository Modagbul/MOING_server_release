package com.moing.backend.domain.missionHeart.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionHeartRes {
    private Long missionArchiveId;
    private String missionHeartStatus;
    private int hearts;
}
