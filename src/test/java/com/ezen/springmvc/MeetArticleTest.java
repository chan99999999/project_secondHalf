package com.ezen.springmvc;

import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.ReplyDto;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetArticleMapper;
import com.ezen.springmvc.domain.meetArticle.mapper.ReplyMapper;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MeetArticleTest {
    @Autowired
    MeetArticleMapper meetArticleMapper;
//    @Autowired
//    TagMapper tagMapper;
    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("모임 게시글 등록 테스트")
    void createTestArticle(){
        MeetArticleDto createArticle = MeetArticleDto
                .builder()
                .title("테스트")
                .content("테스트")
                .time("2022-02-22")
                .enter(4)
                .hitcount(0)
                .categoryId(3)
                .memberId("sunday")
                .placeId(3)
                .build();
        meetArticleMapper.createMeetArticle(createArticle);
        log.info("등록 완료 : {}", createArticle);
    }


    @Test
    @DisplayName("모임 게시글 상세보기 테스트")
    void readMeetArticleTest(){
        MeetArticleDto meetArticleDto = meetArticleMapper.readMeetArticle(3, 1);
        log.info("게시글 상세보기 : {}", meetArticleDto);
    }

//    @Test
//    @DisplayName("모임 게시글 조회수 테스트")
//    void hitcountArticleTest(){
//        MeetArticleDto hitcountArticle = MeetArticleDto
//                .builder()
//                .hitcount(1)
//                .meetArticleId(1)
//                .build();
//        meetArticleMapper.hitcount(hitcountArticle);
//        log.info("조회수 증가 완료: {}", hitcountArticle);
//    }

    @Test
    @DisplayName("모임 게시글 전체 출력 테스트")
    void findByAllMeetArticleTest(){
        List<MeetArticleDto> list = meetArticleMapper.findByAllMeetArticle(3);
        for (MeetArticleDto MeetArticleDto : list) {
            log.info("조회된 게시글 목록 : {}", MeetArticleDto);
        }
    }

    @Test
    @DisplayName("모임 게시글 댓글 등록 테스트")
    void createReplyTest(){
        ReplyDto replyDto = ReplyDto
                .builder()
                .replyId(1)
                .content("댓글 테스트")
                .meetArticleId(1)
                .build();
        replyMapper.createReply(replyDto);
        log.info("등록된 댓글 : {}", replyDto);
    }

    @Test
    @DisplayName("모임 게시글 댓글 수정 테스트")
    void updateReplyTest(){
        ReplyDto updateReply = ReplyDto
                .builder()
                .content("댓글 수정")
                .replyId(5)
                .build();
        replyMapper.updateReply(updateReply);
        log.info("수정된 댓글 : {}", updateReply);
    }

//    @Test
//    @Transactional
//    @DisplayName("모임 게시글 태그 조회 테스트")
//    void findByTagNameTest(){
//        TagDto findByTagName = TagDto
//                .builder()
//                .tagName("기타")
//                .build();
//        tagMapper.findByTagName(findByTagName);
//        log.info("게시글과 태그 조회 : {}", findByTagName);
//    }
}
