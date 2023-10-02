package com.moing.backend.domain.member.domain.entity;

import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.entity.BaseTimeEntity;
import com.moing.backend.global.util.AesConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    @Convert(converter = AesConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Convert(converter = AesConverter.class)
    @Column(nullable = false)
    private String profileImage; //없으면 undef

    @Column(nullable = false, length = 6)
    private String gender; //없으면 undef

    @Column(nullable = false, length = 10)
    private String ageRange; //없으면 undef


    // 추가정보
    @Convert(converter = AesConverter.class)
    @Column(nullable = false)
    private String nickName; //없으면 undef

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Convert(converter = AesConverter.class)
    @Column(nullable = false)
    private String introduction; //없으면 undef

    @Column(nullable = false)
    private String fcmToken; //없으면 undef

    @ColumnDefault("true")
    private boolean isNewUploadPush;

    @ColumnDefault("true")
    private boolean isRemindPush;

    @ColumnDefault("true")
    private boolean isFirePush;

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>(); //최대 3개이므로 양방향

    //==생성 메서드==//
    public static Member valueOf(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return Member.builder()
                .socialId((String) attributes.get("socialId"))
                .provider((SocialProvider) attributes.get("provider"))
                .nickName((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .gender((String) attributes.get("gender"))
                .ageRange((String) attributes.get("age"))
                .role((Role) attributes.get("role"))
                .registrationStatus(RegistrationStatus.UNCOMPLETED)
                .build();
    }

    //==초기화==//
    @PrePersist
    public void prePersist() {
        if (profileImage == null) profileImage = "undef";
        if (gender == null) gender = "undef";
        if (ageRange == null) ageRange = "undef";
        if (nickName == null) nickName = "undef";
        if (introduction == null) introduction = "undef";
        if (fcmToken == null) fcmToken = "undef";
        if (registrationStatus == null) registrationStatus = RegistrationStatus.UNCOMPLETED;
    }

    public void signUp(String nickName, String fcmToken) {
        this.nickName = nickName;
        this.fcmToken = fcmToken;
        this.registrationStatus = RegistrationStatus.COMPLETED;
    }

    @Builder
    public Member(String email, String profileImage, String gender, String ageRange, Role role) {
        this.email = email;
        this.profileImage = profileImage;
        this.gender = gender;
        this.ageRange = ageRange;
        this.role = role;
    }


    public void updateMember(String nickName, String fcmToken) {
        this.nickName = nickName;
        this.fcmToken = fcmToken;
    }

    public void updateProfile(String profileImage, String nickName, String introduction) {
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void updateNewUploadPush(boolean newUploadPush) {
        this.isNewUploadPush = newUploadPush;
    }

    public void updateRemindPush(boolean remindPush) {
        this.isRemindPush = remindPush;
    }

    public void updateFirePush(boolean firePush) {
        this.isFirePush = firePush;
    }

    public void updateAllPush(boolean allPush) {
        this.isNewUploadPush = allPush;
        this.isRemindPush = allPush;
        this.isFirePush = allPush;
    }

    public Member(String ageRange, String email, String fcmToken, String gender, String introduction, String nickName, String profileImage, SocialProvider provider, RegistrationStatus registrationStatus, Role role, String socialId) {
        this.ageRange = ageRange;
        this.email = email;
        this.fcmToken = fcmToken;
        this.gender = gender;
        this.introduction = introduction;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.provider = provider;
        this.registrationStatus = registrationStatus;
        this.role = role;
        this.socialId = socialId;
    }

    public void deleteTeamMember(){
        List<TeamMember> teamMemberList=this.getTeamMembers();
        for(TeamMember teamMember:teamMemberList){
            teamMember.deleteMember();
        }
    }

}
