package com.ezen.springmvc;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetTagDto;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetArticleMapper;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetTagMapper;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MeetArticleServiceTest {
    @Autowired
    MeetArticleService meetArticleService;
    @Autowired
    MeetArticleMapper meetArticleMapper;
    @Autowired
    MeetTagMapper meetTagMapper;

    @Test
    @DisplayName("모임 게시글 상세보기 테스트")
    @Disabled
    void readMeetArticleTest(){
        MeetArticleDto meetArticleDto = meetArticleMapper.readMeetArticle(1,29);
        log.info("게시글 상세보기 : {}", meetArticleDto);
    }

    @Test
    @DisplayName("모임 게시글 등록 테스트")
    @Disabled
    void createMeetArticleTest(){
        MeetArticleDto createArticle = MeetArticleDto
                .builder()
                .title("테스트")
                .content("테스트")
                .time("2022-22-22")
                .enter(5)
                .hitcount(0)
                .categoryId(3)
                .memberId("monday")
//                .placeId(1)
                .build();
        meetArticleService.addMeet(createArticle);
        log.info("등록 완료 : {}", createArticle);
    }

    @Test
    @DisplayName("모임 게시글 등록")
    @Disabled
    void updateMeetArticleTest(){
        MeetArticleDto meetArticleDto = MeetArticleDto.builder()
                .title("제목목")
                .content("내용용")
                .enter(3)
                .categoryId(3)
                .memberId("chan999")
                .placeId(11)
                .build();
        meetArticleMapper.createMeetArticle(meetArticleDto);
    }

    @Test
    @DisplayName("모임 태그 등록")
    @Disabled
    void addMeetTagTest(){
        MeetTagDto meetTagDto = MeetTagDto.builder()
                .tagName("건강")
                .build();
        meetTagMapper.createMeetTag(meetTagDto);
    }


//    @Test
//    @DisplayName("모임 게시글 수정 테스트")
//    @Disabled
//    void updateArticleTest(){
//        MeetArticleDto updateArticle = MeetArticleDto
//                .builder()
//                .title("수정 테스트")
//                .content("수정 테스트")
//                .meetArticleId(7)
//                .build();
//        meetArticleService.updateMeetArticle(updateArticle);
//        log.info("수정 완료 : {}", updateArticle);
//    }
//
//    @Test
//    @DisplayName("모임 게시글 삭제 테스트")
//    @Disabled
//    void deleteArticleTest(){
//        MeetArticleDto deleteArticle = MeetArticleDto
//                .builder()
//                .meetArticleId(7)
//                .build();
//        meetArticleService.deleteMeetArticle(deleteArticle);
//        log.info("삭제 완료 : {}", deleteArticle);
//    }

    @Test
    @DisplayName("모임 게시글 전체 출력 테스트")
//    @Disabled
    void findByAllArticleTest(){
        List<MeetArticleDto> list = meetArticleService.findByAllMeetArticle(3);
        for (MeetArticleDto MeetArticleDto : list) {
            log.info("조회된 게시글 목록 : {}", MeetArticleDto);
        }
    }

//    @Test
//    @DisplayName("모임 게시글 댓글 등록 테스트")
//    @Disabled
//    void createReplyTest(){
//        MeetReplyDto meetReplyDto = meetReplyDto
//                .builder()
//                .content("댓글 테스트")
//                .meetArticleId(2)
//                .build();
//        meetArticleService.createReply(meetReplyDto);
//        log.info("등록된 댓글 : {}", meetReplyDto);
//    }

//    @Test
//    @Transactional
//    @DisplayName("모임 게시글 태그 조회 테스트")
//    void findByTagNameTest(){
//        TagDto findByTagName = TagDto
//                .builder()
//                .tagName("기타")
//                .build();
//        meetArticleService.findByTagName(findByTagName);
//        log.info("게시글과 태그 조회 : {}", findByTagName);
//    }
}
