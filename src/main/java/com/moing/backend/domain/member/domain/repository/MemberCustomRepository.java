package com.moing.backend.domain.member.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;

import java.util.Optional;

public interface MemberCustomRepository {
    boolean checkNickname(String nickname);
}
