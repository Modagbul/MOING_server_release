package com.moing.backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST("400", "입력값이 유효하지 않습니다."),
    METHOD_NOT_ALLOWED("405", "클라이언트가 사용한 HTTP 메서드가 리소스에서 허용되지 않습니다."),
    INTERNAL_SERVER_ERROR("500", "서버에서 요청을 처리하는 동안 오류가 발생했습니다."),
    NOT_FOUND_REFRESH_TOKEN_ERROR( "J0008",  "유효하지 않는 RefreshToken 입니다."),

    //유저 관련 에러 코드
    NOT_FOUND_BY_SOCIAL_ID_ERROR( "U0001",  "해당 socialId인 유저가 존재하지 않습니다."),
    ACCOUNT_ALREADY_EXIST("AU0001", "해당 email로 다른 소셜 플랫폼으로 가입하였습니다."),
    TOKEN_INVALID_ERROR("AU0002", "입력 토큰이 유효하지 않습니다."),
    APPID_INVALID_ERROR("AU0003", "appId가 유효하지 않습니다"),
    NICKNAME_DUPLICATION_ERROR("AU0003", "닉네임이 중복됩니다."),

    //팀멤버 에러 코드
    TOO_MANY_TEAM_MEMBER_ERROR("TM0001", "팀 최대 개수 3을 초과했습니다"),

    //미션 관련 에러코드
    NO_ACCESS_CREATE_MISSION("M0001", "소모임장만 미션을 생성할 수 있습니다."),
    NOT_FOUND_MISSION("M0002", "미션을 찾을 수 없습니다."),
    NOT_FOUND_MISSION_ARCHIVE("MA0001", "아직 미션을 제출하지 않았습니다."),

    //팀 관련 에러 코드
    NOT_FOUND_BY_TEAM_ID_ERROR("T0001", "해당 teamId인 팀이 존재하지 않습니다."),
    NOT_AUTH_BY_TEAM_ERROR("T0002","권한이 없습니다."),

    //게시글 관련 에러 코드
    NOT_FOUND_BY_BOARD_ID_ERROR("B0001","해당 boardId인 팀이 존재하지 않습니다.");

    private String errorCode;
    private String message;

}