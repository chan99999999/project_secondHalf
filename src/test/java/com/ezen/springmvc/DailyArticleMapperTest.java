package com.ezen.springmvc;

import com.ezen.springmvc.domain.category.dto.CategoryDto;
import com.ezen.springmvc.domain.category.service.CategoryServiceImpl;
import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.mapper.*;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class DailyArticleMapperTest {

    @Autowired
    DailyArticleMapper dailyArticleMapper;
    @Autowired
    FileMapper fileMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TagArticleMapper tagArticleMapper;
    @Autowired
    private DailyArticleServiceImpl dailyArticleServiceImpl;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    @Autowired
    private HeartMapper heartMapper;

    @Test
    @DisplayName("카테고리 목록 반환 테스트")
    @Disabled
    void getCategoryListTest() {
        List<CategoryDto> categoryList = categoryServiceImpl.getCategoryList();
        log.info("카테고리 목록 : {}", categoryList);
    }

    @Test
    @DisplayName("신규 일상 게시글 등록 테스트")
    @Disabled
    void createDailyArticleTest() {
        DailyArticleDto createDailyArticleDto = DailyArticleDto
                .builder()
                .dailyArticleId(2)
                .title("일상 게시글 제목입니다...")
                .content("일상 게시글 내용입니다...")
                .categoryId(2)
                .memberId("sunday")
                .build();
        dailyArticleMapper.createDailyArticle(createDailyArticleDto);
        log.info("신규 일상 게시글 등록 완료 : {}", createDailyArticleDto);
    }

    @Test
    @DisplayName("일상 게시글 번호로 게시글 조회 테스트")
    @Disabled
    void findByDailyArticleIdTest() {
        // given
        int dailyArticleId = 21;
        // when
        DailyArticleDto dailyArticleDto = dailyArticleMapper.findByDailyArticleId(dailyArticleId);
        // then
        assertThat(dailyArticleDto)
                .isNotNull();
        log.info("조회된 게시글 : {}", dailyArticleDto);
    }


    @Test
    @DisplayName("일상 게시글 상세 보기 테스트")
    @Disabled
    void readDailyArticleTest() {
        DailyArticleDto dailyArticleDto = dailyArticleMapper.readDailyArticle(2, 34);
        log.info("조회된 게시글 : {}", dailyArticleDto);
    }

    @Test
    @DisplayName("일상 게시글 번호로 파일 조회 테스트")
    @Disabled
    void findByFileIdTest() {
        List<FileDto> fileDto = fileMapper.findByFileId(17);
        log.info("조회된 파일 : {}", fileDto);
    }

    @Test
    @DisplayName("파일 목록 조회 테스트")
    @Disabled
    void findByAllFileTest() {
        List<FileDto> list = fileMapper.findByAllFile();
        for (FileDto fileDto : list) {
            log.info("조회된 파일 : {}", fileDto);
        }
    }

    @Test
    @DisplayName("댓글 등록 테스트")
    @Disabled
    void createReplyTest() {
        ReplyDto replyDto = ReplyDto.builder()
                .replyId(2)
                .dailyArticleId(22)
                .content("댓글 냉요요용")
                .writer("monday")
                .build();
        replyMapper.createReply(replyDto);
        log.info("댓글 등록 완료");
    }

    @Test
    @DisplayName("댓글 목록 반환 테스트")
    @Disabled
    void findByReplyAllTest() {
        List<ReplyDto> list = replyMapper.findByReplyAll(20);
        for (ReplyDto replyDto : list) {
            log.info("조회된 댓글 목록 : {}", replyDto);
        }
    }

    @Test
    @DisplayName("좋아요 등록 테스트")
    @Disabled
    void clickHeartTest() {
        HeartDto heartDto = HeartDto.builder()
                .heartCount(2)
                .dailyArticleId(20)
                .memberId("승아")
                .build();

        dailyArticleServiceImpl.clickHeart(heartDto);
        log.info("좋아요 등록 완료");
    }

    @Test
    @DisplayName("좋아요 개수 반환 테스트")
    @Disabled
    void getHeartCountTest() {
        int heartCount = dailyArticleServiceImpl.getHeartCount(29, "sunday");
        log.info("조회된 좋아요 개수 : {}", heartCount);
    }

    @Test
    @DisplayName("좋아요 존재 유무 반환 테스트")
    @Disabled
    void existHeartTest() {
        boolean existed = heartMapper.existHeart(25, "sunday") > 0;
        log.info("존재 하나요? : {}", existed);
    }

    @Test
    @DisplayName("태그 등록 테스트")
    @Disabled
    void createTagTest() {
        TagDto tagDto = TagDto.builder()
                .tagName("음식")
                .build();
        tagMapper.createTag(tagDto);
        log.info("등록된 태그 : {}", tagDto);
    }

    @Test
    @DisplayName("태그 아티클 등록 테스트")
    @Disabled
    void createTagArticleTest() {
        TagArticleDto tagArticleDto = TagArticleDto.builder()
                .tagId(3)
                .dailyArticleId(39)
                .build();
        tagArticleMapper.createTagArticle(tagArticleDto);
        log.info("등록된 태그 게시글 : {}", tagArticleDto);
    }

    @Test
    @DisplayName("태그 별 게시글 목록 반환 테스트")
    @Disabled
    void findByAllTagNameTest() {
        SearchDto searchDto = SearchDto.builder()
                .limit(5)
                .page(1)
                .tagName("일상")
                .build();
        List<DailyArticleDto> list = dailyArticleMapper.findByAllTagName(2, searchDto.getTagName(), searchDto);
        for (DailyArticleDto dailyArticleDto : list) {
            log.info("태그 별 게시글 목록 : {}", dailyArticleDto);

        }
    }

    @Test
    @DisplayName("일상 게시글 목록 조회 테스트")
    @Disabled
    void findByAllDailyArticleTest() {
        SearchDto searchDto = SearchDto.builder()
                .limit(5)
                .page(1)
                .build();
        List<DailyArticleDto> list = dailyArticleMapper.findByAllDailyArticle(2, searchDto);
        for (DailyArticleDto dailyArticleDto : list) {
            log.info("조회된 게시글 목록 : {}", dailyArticleDto);
        }
    }

    @Test
    @DisplayName("일상 게시글 개수 반환 테스트")
    @Disabled
    void getDailyArticleCountTest() {
        SearchDto searchDto = SearchDto.builder()
                .tagName("일상")
                .build();
        int dailyArticleCount = dailyArticleServiceImpl.getDailyArticleCount(2, searchDto);
        log.info("조회된 게시글 개수 : {}", dailyArticleCount);
    }

    @Test
    @DisplayName("다중 파일 등록 테스트")
    @Disabled
    void createFileListTest() {
        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .memberId("sunday")
                .categoryId(2)
                .build();
        dailyArticleMapper.createDailyArticle(dailyArticleDto);

        List<FileDto> fileDtoList = new ArrayList<>();
        FileDto fileDto1 = FileDto.builder()
                .fileName("fileName1.jpg")
                .encryptedName("filefileName1.jpg")
                .build();

        FileDto fileDto2 = FileDto.builder()
                .fileName("fileName2.jpg")
                .encryptedName("filefileName2.jpg")
                .build();

        FileDto fileDto3 = FileDto.builder()
                .fileName("fileName3.jpg")
                .encryptedName("filefileName3.jpg")
                .build();

        fileDtoList.add(fileDto1);
        fileDtoList.add(fileDto2);
        fileDtoList.add(fileDto3);

        fileMapper.createFileList(fileDtoList);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    @Disabled
    void deleteDailyArticleTest() {
        dailyArticleMapper.deleteDailyArticle(2, 176);
        log.info("게시글 삭제 완료!");
    }

    @Test
    @DisplayName("게시글 수정 테스트")
//    @Disabled
    void updateDailyArticleTest() {
        DailyArticleDto dailyArticleDto = DailyArticleDto.builder()
                .title("냠냠")
                .build();
        dailyArticleMapper.updateDailyArticle(173, dailyArticleDto);
        log.info("게시글 제목 수정 완료!");
    }
}
