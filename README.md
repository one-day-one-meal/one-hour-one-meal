# :plate_with_cutlery: 백오피스 프로젝트: 1시1끼(1 Hour 1 Eat)

<strong>셰프의 레시피를 통해 요리를 배워보세요!</strong>

:man_cook: 셰프가 개설한 요리 강좌를 수강해보세요!

:woman_cook: 요리 강좌에 있는 다양한 레시피 동영상들을 통해 요리를 배워보세요!

<br/>

## :spaghetti: <strong>프로젝트 소개</strong>

셰프는 셰프로 가입하여 승인 대기상태가 되고 강좌를 개설하고 동영상 레시피를 업로드 할 수 있습니다. 본인의 강좌, 레시피만을 편집, 삭제할 수 있습니다.

학생은 일반 회원가입을 통하여 가입하고 셰프를 구독하고 동영상 레시피를 보고 요리를 배울 수 있습니다. 구독한 셰프의 강좌만을 볼 수 있는 기능도 있습니다.

관리자는 가입이 불가능하고 관리자만이 관리자를 생성할 수 있습니다. 관리자는 셰프로 가입하려는 사람들을 승인할 수 있으며, 모든 강좌, 레시피, 댓글에 대한 편집 및 삭제 권한을 가집니다.

유저는 악성 댓글을 신고할 수 있으며 관리자는 신고를 무시하거나 신고를 받아들여 댓글을 삭제할 수 있습니다.

학생 / 셰프 / 어드민으로 이루어진 인증, 인가와 백오피스 기능에 초점을 맞춘 프로젝트 입니다

<br/>

## :memo: 팀원 소개

<br/>

![Picture](https://one-hour-one-eat-photo.s3.ap-northeast-2.amazonaws.com/team_pic.PNG)

<table align=center>
    <thead>
        <tr >
            <th style="text-align:center;" >노재원</th>
            <th style="text-align:center;" >류원형</th>
            <th style="text-align:center;" >이무준</th>
            <th style="text-align:center;" >이수진</th>
            <th style="text-align:center;" >김보현</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><img width="160" src="/readme_assets/mobzzzz_profile.jpg" /> </td>
            <td><img width="160" src="/readme_assets/1hyung_profile.jfif" /></td>
            <td><img width="160" src="/readme_assets/moomoo_profile.gif" /></td>
            <td><img width="160" src="/readme_assets/devitssu_profile.jpg" /></td>
            <td><img width="160" src="/readme_assets/ckhcree_profile.png" /></td>
        </tr>
        <tr>
            <td><a href="https://github.com/Mobzzzzz">@Mobzzzzz</a></td>
            <td><a href="https://github.com/1hyung">@1hyung</a></td>
            <td><a href="https://github.com/Moo-moo-11">@Moo-moo-11</a></td>
            <td><a href="https://github.com/devitssu">@devitssu</a></td>
            <td><a href="https://github.com/ckhcree">@ckhcree</a></td>
        </tr>
        <tr>
            <td width="160">유저 도메인<br>전반적인<br>인증/인가</td>
            <td width="160">레시피 도메인<br>쿼리 최적화</td>
            <td width="160">강좌 도메인<br>쿼리 최적화</td>
            <td width="160">백오피스 <br>API 및 정책<br>Ngrok + Retools 구축</td>
            <td width="160">댓글 도메인<br>신고하기<br> Soft delete</td>
        </tr>
    </tbody>
</table>                                                     

## :handshake: <strong>협업 도구</strong>

<img alt="Git" src="https://img.shields.io/badge/Git-F05032?style=flat-squre&logo=git&logoColor=white"/>

<img alt="GitHub" src="https://img.shields.io/badge/GitHub-181717?style=flat-squre&logo=github&logoColor=white"/>

<img alt="Slack" src="https://img.shields.io/badge/Slack-4A154B?style=flat-squre&logo=slack&logoColor=white"/>

## :cocktail: <strong>Event storming</strong>

![Event storming](/readme_assets/event_storming.png)

<br/>

## :cocktail: <strong>Wire frame</strong>

![Wire frame](/readme_assets/wire_frame.png)

<br/>

## :card_index_dividers: <strong>API 명세서</strong>

![API Docs](/readme_assets/API_docs.png)

<br/>

## :card_file_box: <strong>ERD</strong>

![ERD](/readme_assets/ERD.png)

<br/>

## :books: 프로젝트 요구 사항에 따른 필수, 선택 구현 기능

#### **- 필수 구현 기능**

- **게시물 CRUD 기능**
    - 전체 게시글 정보를 조회 하는 기능
    - 게시물 조회를 제외한 나머지 기능들은 전부 인가 개념 적용
    - 내가 작성한 글을 남이 수정하거나 삭제할 수는 없어야 함. 본인만 수정/삭제 할 수 있음
- **사용자 인증 기능**
    - 회원가입 기능
    - 로그인 및 로그아웃 기능
- **댓글 CRUD 기능**
    - 게시물과 마찬가지로 인가 개념 적용
- **프로필 관리 기능**
    - 프로필 수정 기능
        - 최근 3번 안에 사용한 비밀번호는 사용할 수 없도록 제한

<br/>

#### **- 선택 구현 기능**

- **소셜 로그인 기능 구현**
    - 개발 가이드를 참고하여 네이버, 카카오 로그인을 구현
- **백오피스 만들어보기**
    - 관리자페이지 만들기
    - Retool을 이용해 백오피스 구현
        - Ngrok을 사용해서 Public 접근이 가능하게 만들기
- **좋아요 기능 구현**
    - 게시물 좋아요/ 좋아요 취소 기능
- **팔로우 기능 구현**
    - 팔로우한 사용자의 게시물을 볼 수 있는 기능 구현
- **프로필에 사진 업로드 기능 구현**
    - AWS S3를 이용해 프로필 사진 저장하는 기능 구현

<br/>

## :clipboard: 요구사항에 따라 제작한 프로젝트 주요 기능

+ JWT + Security Filter를 통한 기본 인증
    + 특정 도메인을 제외하고 기본적으로 Authorization header를 통해 Access token을 검증합니다.
    + Claim엔 id, role, 토큰의 type을 담고 있어 Refresh token으로는 인증이 불가능합니다.
+ 이전 비밀번호 체크
    + 최근 비밀번호 3회에 대한 테이블을 따로 구성해 offset, limit을 통한 쿼리로 3회를 체크, 저장합니다
+ 리프레쉬 토큰 관리
    + 로그아웃 및 재발급의 수동 관리를 위해 Stateless의 장점을 약간 포기하고 Refresh token 테이블을 별도로 관리합니다.
    + 1:1로 유저와 맵핑되어 있습니다.
+ 구독 관리
    + 유저의 구독(팔로잉) 정보를 저장하고 이후 구독 목록 조회, 구독한 쉐프의 강의 조회가 가능합니다.
    + 유저와 쉐프를 외래키로 사용하는 복합키 테이블입니다.
+ 카카오 / Naver 소셜 로그인
    + 소셜 로그인 진행시 유저 테이블에 Provider로 구분해 회원가입 / 로그인 처리를 진행합니다.
+ Query 최적화를 위한 QueryDSL 사용
    + <a href="/readme_assets/recipe_fetch_join_single.png">Recipe 최적화1</a> <a href="/readme_assets/recipe_fetch_join_list.png">Recipe 최적화2</a>
    + 유저와 쉐프를 외래키로 사용하는 복합키 테이블입니다.
+ 댓글 + 댓글 신고 기능
  + 신고의 정책을 위해 soft delete 를 활용하여 admin이 신고 내역을 관리합니다.

<br/>

## :clipboard: 이번 프로젝트에서 협업시 좋았던 점


<br/>

## :gun: 트러블 슈팅

<br/>

## :pushpin: 환경설정

|           |                                                             Tool & Version                                                             |
|:---------:|:--------------------------------------------------------------------------------------------------------------------------------------:|
| Language  |              <img src="https://img.shields.io/badge/Kotlin-ver 1.9.24-7F52FF?style=flat-squre&logo=Kotlin&logoColor=white"/>              |
|    IDE    |            <img src="https://img.shields.io/badge/Intellij%20IDEA-000000?style=flat-squre&logo=intellijidea&logoColor=white"/>            |
|    SDK    | <img src="https://img.shields.io/badge/Eclipse%20Temurin-ver 21.0.2-FF1464?style=flat-squre&logo=eclipseadoptium&logoColor=white"/> | 
| Framework |       <img src="https://img.shields.io/badge/Spring%20Boot-ver 3.3.0-6DB33F?style=flat-squre&logo=springboot&logoColor=white"/>        |
|    JWT    |         <img src="https://img.shields.io/badge/jjwt-ver 0.12.5-000000?style=flat-square&logo=jsonwebtokens&logoColor=white"/>          |
