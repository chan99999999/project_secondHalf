package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyArticleServiceImpl implements DailyArticleService {

    private final CategoryMapper categoryMapper;
    private final DailyArticleMapper dailyArticleMapper;
    private final ReplyMapper replyMapper;
    private final HeartMapper heartMapper;
    private final FileMapper fileMapper;

    /**
     * 카테고리 목록 반환 구현
     * @return 카테고리 목록
     */
    @Override
    public List<CategoryDto> getCategoryList() {
        return categoryMapper.findByCategoryAll();
    }

    /**
     * 신규 일상 게시글 등록 구현
     * @param dailyArticleDto 일상 게시글
     * @param fileDto 파일
     * @return 일상 게시글
     */
    @Override
    @Transactional
    public DailyArticleDto writeDailyArticle(DailyArticleDto dailyArticleDto, FileDto fileDto) {
        dailyArticleMapper.createDailyArticle(dailyArticleDto);
        fileDto.setDailyArticleId(dailyArticleDto.getDailyArticleId());
        fileMapper.createFileDto(fileDto);
        return dailyArticleMapper.findByDailyArticleId(dailyArticleDto.getDailyArticleId());
    }

    /**
     * 일상 게시글 목록 반환 구현
     * @param categoryId 카테고리 번호
     * @return 일상 게시글 목록
     */
    @Override
    public List<DailyArticleDto> getDailyArticles(int categoryId) {
        return dailyArticleMapper.findByAllDailyArticle(categoryId);
    }

    /**
     * 일상 게시글 상세 보기 구현
     * @param categoryId 카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    @Override
    public DailyArticleDto getDailyArticle(int categoryId, int dailyArticleId) {
        return dailyArticleMapper.readDailyArticle(categoryId, dailyArticleId);
    }

    /**
     * 파일 번호로 파일 조회 구현
     * @param fileId 파일 번호
     * @return 파일
     */
    @Override
    public FileDto getFile(int fileId) {
        return fileMapper.findByFileId(fileId);
    }

    /**
     * 파일 목록 조회 구현
     * @return 파일 목록
     */
    @Override
    public List<FileDto> getFiles() {
        return fileMapper.findByAllFile();
    }

    /**
     * 댓글 등록 구현
     * @param replyDto 댓글
     */
    @Override
    public void writeReply(ReplyDto replyDto) {
        replyMapper.createReply(replyDto);
    }

    /**
     * 댓글 목록 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 목록
     */
    @Override
    public List<ReplyDto> getReplyList(int dailyArticleId) {
        return replyMapper.findByReplyAll(dailyArticleId);
    }

    /**
     * 좋아요 등록 구현
     * @param heartDto 좋아요
     */
    @Override
    public void clickHeart(HeartDto heartDto) {
        heartMapper.createHeart(heartDto);
    }


}
