package com.moing.backend.domain.missionArchive.exception;

import com.moing.backend.domain.missionState.exception.MissionStateException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAccessMissionArchiveException extends MissionStateException {
    public NoAccessMissionArchiveException() {
        super(ErrorCode.NO_MORE_ARCHIVE_ERROR, HttpStatus.NOT_FOUND);

    }
}
