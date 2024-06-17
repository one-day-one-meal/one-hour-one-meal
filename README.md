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

| 이름  |  태그  |   MBTI   | 블로그                              | GitHub                        | 한마디!                                                              |
|:---:|:----:|:--------:|----------------------------------|-------------------------------|-------------------------------------------------------------------|
| 노재원 | `팀장` |  `ISFP`  | https://velog.io/@mobz/posts     | https://github.com/Mobzzzzz   | 템포가 매일 빨라지는 것 같아요                                                 |
| 김보현 | `팀원` |  `INTP`  |                                  | https://github.com/ckhcree    | 좋은 에너지로 함께할수 있도록 노력하겠습니다!                                         |
| 류원형 | `팀원` | `INFJ-A` | https://blog.naver.com/ryu1hyung | https://github.com/1hyung     | 좋은 분들 만난거 같아서 다행입니다. 좋은 관계로 마무리 했으면 좋겠습니다. 코딩은 부족하지만 열심히 따라가겠습니다! |
| 이무준 | `팀원` |  `INFJ`  | https://moomoo11.tistory.com/    | https://github.com/Moo-moo-11 | 행복합니다 즐겁습니다                                                       |
| 이수진 | `팀원` |  `INTP`  | https://velog.io/@devssu         | https://github.com/devitssu   | 모두 화이팅                                                            |

## :handshake: <strong>협업 도구</strong>

<img src="https://img.shields.io/badge/Git-F05032?style=plastic&logo=git&logoColor=white"/>

<img src="https://img.shields.io/badge/GitHub-181717?style=plastic&logo=github&logoColor=white"/>

<img src="https://img.shields.io/badge/Slack-4A154B?style=plastic&logo=slack&logoColor=white"/>

## :cocktail: <strong>Event storming</strong>

이미지 추가

<br/>

## :card_index_dividers: <strong>API 명세서</strong>

이미지 or 링크 추가

<br/>

## :card_file_box: <strong>ERD</strong>

ERD 이미지 추가

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

+ 프로젝트 기능 1
    + 프로젝트 기능 설명
+ 프로젝트 기능 2
    + 프로젝트 기능 설명
+ 프로젝트 기능 3
    + 프로젝트 기능 설명
+ 프로젝트 기능 4
    + 프로젝트 기능 설명
+ 프로젝트 기능 5
    + 프로젝트 기능 설명
+ 프로젝트 기능 6
    + 프로젝트 기능 설명

<br/>

## :clipboard: 협업시 어려웠던 점

저는 행복했어요

<br/>

## :gun: 트러불 슈팅

<br/>

## :pushpin: 환경설정

맘에 드는 뱃지 모양을 골라주세요 (1. Kotlin, Intellij -> plastic, 2.Eclipse Temurin -> for-the-badge, 3. Spring Boot, jjwt ->
flat-squre )

|           |                                                             Tool & Version                                                             |
|:---------:|:--------------------------------------------------------------------------------------------------------------------------------------:|
| Language  |              <img src="https://img.shields.io/badge/Kotlin-ver 1.9.24-7F52FF?style=plastic&logo=Kotlin&logoColor=white"/>              |
|    IDE    |            <img src="https://img.shields.io/badge/Intellij%20IDEA-000000?style=plastic&logo=intellijidea&logoColor=white"/>            |
|    SDK    | <img src="https://img.shields.io/badge/Eclipse%20Temurin-ver 21.0.2-FF1464?style=for-the-badge&logo=eclipseadoptium&logoColor=white"/> | 
| Framework |       <img src="https://img.shields.io/badge/Spring%20Boot-ver 3.3.0-6DB33F?style=flat-squre&logo=springboot&logoColor=white"/>        |
|    JWT    |         <img src="https://img.shields.io/badge/jjwt-ver 0.12.5-000000?style=flat-square&logo=jsonwebtokens&logoColor=white"/>          |