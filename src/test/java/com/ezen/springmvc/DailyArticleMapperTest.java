package com.ezen.springmvc;

import com.ezen.springmvc.domain.dailyarticle.dto.DailyArticleDto;
import com.ezen.springmvc.domain.dailyarticle.dto.FileDto;
import com.ezen.springmvc.domain.dailyarticle.mapper.DailyArticleMapper;
import com.ezen.springmvc.domain.dailyarticle.mapper.FileMapper;
import com.ezen.springmvc.domain.dailyarticle.service.DailyArticleServiceImpl;
import com.ezen.springmvc.domain.member.dto.MemberDto;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private DailyArticleServiceImpl dailyArticleServiceImpl;

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
    @DisplayName("일상 게시글 목록 조회 테스트")
//    @Disabled
    void findByAllDailyArticleTest() {
        List<DailyArticleDto> list = dailyArticleMapper.findByAllDailyArticle(2);
        for (DailyArticleDto dailyArticleDto : list) {
            log.info("조회된 게시글 목록 : {}", dailyArticleDto);
        }
    }

    @Test
    @DisplayName("일상 게시글 상세 보기 테스트")
//    @Disabled
    void readDailyArticleTest() {
        DailyArticleDto dailyArticleDto = dailyArticleMapper.readDailyArticle(2, 11);
        log.info("조회된 게시글 : {}", dailyArticleDto);
    }

    @Test
    @DisplayName("파일 번호로 파일 조회 테스트")
    @Disabled
    void findByFileIdTest() {
        FileDto fileDto = fileMapper.findByFileId(2);
        log.info("조회된 파일 : {}", fileDto);
    }
}
