package com.ezen.springmvc;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetArticleDto;
import com.ezen.springmvc.domain.meetArticle.dto.MeetReplyDto;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetArticleMapper;
import com.ezen.springmvc.domain.category.service.CategoryServiceImpl;
import com.ezen.springmvc.domain.meetArticle.mapper.MeetReplyMapper;
import com.ezen.springmvc.domain.meetArticle.service.MeetArticleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class MeetArticleTest {
    @Autowired
    MeetArticleMapper meetArticleMapper;
//    @Autowired
//    TagMapper tagMapper;
    @Autowired
MeetReplyMapper meetReplyMapper;
    @Autowired
    MeetArticleService meetArticleService;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;


//    @Test
//    @DisplayName("카테고리 목록 반환 테스트")
////    @Disabled
//    void categoryListTest() {
//        List<CategoryDto> categoryList = categoryServiceImpl.findByCategoryList();
//        log.info("카테고리 목록 : {}", categoryList);
//    }

    @Test
    @DisplayName("모임 게시글 등록 테스트")
    @Disabled
    void createTestArticle(){
        MeetArticleDto createArticle = MeetArticleDto
                .builder()
                .title("테스트2")
                .content("테스트2")
                .enter(4)
                .categoryId(3)
                .memberId("monday")
                .placeId(3)
                .build();
        meetArticleMapper.createMeetArticle(createArticle);
        log.info("등록 완료 : {}", createArticle);
    }


    @Test
    @DisplayName("모임 게시글 상세보기 테스트")
//    @Disabled
    void readMeetArticleTest(){
        MeetArticleDto meetArticleDto = meetArticleMapper.readMeetArticle(3, 29);
        assertThat(meetArticleDto).isNotNull();
        log.info("게시글 상세보기 : {}", meetArticleDto);
    }

    @Test
    @DisplayName("모임 게시글 조회수 테스트")
    void hitcountArticleTest(){
        MeetArticleDto meetArticleId = MeetArticleDto.builder()
                .meetArticleId(29)
                .build();
        // 게시글 조회 전 조회수 확인
//        MeetArticleDto originalArticle = meetArticleMapper.findByMeetArticleId(meetArticleId);
//        int originalHitcount = originalArticle.getHitcount();
//        log.info("증가 전 조회수: {}", originalHitcount);
        // 조회수 증가
        meetArticleService.hitcount(meetArticleId);
        log.info(meetArticleId.toString());
        // 게시글 조회 후 조회수 확인
//        MeetArticleDto updatedArticle = meetArticleMapper.findByMeetArticleId(meetArticleId);
//        int updatedHitcount = updatedArticle.getHitcount();
//        log.info("증가 후 조회수: {}", updatedHitcount);
//        assertEquals(originalHitcount + 1, updatedHitcount);
    }

//    @Test
//    @DisplayName("모임 게시글 전체 출력 테스트")
//    @Disabled
//    void findByAllMeetArticleTest(){
//        List<MeetArticleDto> list = meetArticleMapper.findByAllMeetArticle(3);
//        for (MeetArticleDto MeetArticleDto : list) {
//            log.info("조회된 게시글 목록 : {}", MeetArticleDto);
//        }
//    }

//    @Test
//    @DisplayName("모임 게시글 댓글 등록 테스트")
////    @Disabled
//    void createReplyTest(){
//        MeetReplyDto meetReplyDto = meetReplyDto
//                .builder()
//                .replyId(1)
//                .meetArticleId(29)
//                .content("15151515151515151515")
//                .writer("monday")
//                .build();
//        meetReplyMapper.createReply(meetReplyDto);
//        log.info("등록된 댓글 : {}", meetReplyDto);
//    }

    @Test
    @DisplayName("모임 게시글 댓글 목록 반환 테스트")
//    @Disabled
    void findByReplyAllTest() {
        List<MeetReplyDto> list = meetReplyMapper.findByReplyAll(29);
        for (MeetReplyDto meetReplyDto : list) {
            log.info("조회된 댓글 목록 : {}", meetReplyDto);
        }
    }

//    @Test
//    @DisplayName("모임 게시글 댓글 수정 테스트")
//    @Disabled
//    void updateReplyTest(){
//        ReplyDto updateReply = ReplyDto
//                .builder()
//                .content("댓글 수정")
//                .replyId(1)
//                .build();
//        replyMapper.updateReply(updateReply);
//        log.info("수정된 댓글 : {}", updateReply);
//    }

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
