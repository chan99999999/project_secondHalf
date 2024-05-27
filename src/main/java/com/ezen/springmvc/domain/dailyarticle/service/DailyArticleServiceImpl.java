package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.mapper.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyArticleServiceImpl implements DailyArticleService {

    private static final Logger log = LoggerFactory.getLogger(DailyArticleServiceImpl.class);
    private final DailyArticleMapper dailyArticleMapper;
    private final ReplyMapper replyMapper;
    private final HeartMapper heartMapper;
    private final FileMapper fileMapper;



    /**
     * 신규 일상 게시글 등록 구현
     *
     * @param dailyArticleDto 일상 게시글
     * @param fileDto         파일
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
     *
     * @param categoryId 카테고리 번호
     * @return 일상 게시글 목록
     */
    @Override
    public List<DailyArticleDto> getDailyArticles(int categoryId) {
        return dailyArticleMapper.findByAllDailyArticle(categoryId);
    }

    /**
     * 일상 게시글 상세 보기 구현
     *
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    @Override
    public DailyArticleDto getDailyArticle(int categoryId, int dailyArticleId) {
        return dailyArticleMapper.readDailyArticle(categoryId, dailyArticleId);
    }

    /**
     * 파일 번호로 파일 조회 구현
     *
     * @param fileId 파일 번호
     * @return 파일
     */
    @Override
    public FileDto getFile(int fileId) {
        return fileMapper.findByFileId(fileId);
    }

    /**
     * 파일 목록 조회 구현
     *
     * @return 파일 목록
     */
    @Override
    public List<FileDto> getFiles() {
        return fileMapper.findByAllFile();
    }

    /**
     * 댓글 등록 구현
     *
     * @param replyDto 댓글
     */
    @Transactional
    @Override
    public void writeReply(ReplyDto replyDto) {
        replyMapper.createReply(replyDto);
    }

    /**
     * 댓글 목록 반환 구현
     *
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 목록
     */
    @Override
    public List<ReplyDto> getReplyList(int dailyArticleId) {
        return replyMapper.findByReplyAll(dailyArticleId);
    }

    /**
     * 댓글 수 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 수
     */
    @Override
    public int getReplyCount(int dailyArticleId) {
        return replyMapper.findReplyCount(dailyArticleId);
    }

    /**
     * 좋아요 등록 구현
     *
     * @param heartDto 좋아요
     */
    @Override
    @Transactional
    public void clickHeart(HeartDto heartDto) {
        heartMapper.createHeart(30, "sunday");
    }

    /**
     * 좋아요 업데이트 구현
     *
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    @Override
    @Transactional
    public boolean insertAndUpdateHeart(int dailyArticleId, String memberId, boolean checked) {
        try {
            boolean heartExists = heartMapper.existHeart(dailyArticleId, memberId) > 0;
            // 존재하는지에 따라 좋아요 인서트할지 분기
            if (heartExists) {
                if (checked) { // 넘어온 checked 값에 따라 증가, 감소 분기
                    heartMapper.plusHeart(dailyArticleId, memberId);
                } else {
                    heartMapper.minusHeart(dailyArticleId, memberId);
                }
            } else {
                heartMapper.createHeart(dailyArticleId, memberId);
                if (checked) {
                    heartMapper.plusHeart(dailyArticleId, memberId);
                }

            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * 좋아요 개수 반환 구현
     *
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     * @return 좋아요 개수
     */
    @Override
    @Transactional
    public int getHeartCount(int dailyArticleId, String memberId) {
        return heartMapper.findHeartCount(dailyArticleId, memberId);
    }

    /**
     * 좋아요 행의 개수 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId 회원 아이디
     * @return 좋아요 행의 개수
     */
    @Override
    public int getExistHeart(int dailyArticleId, String memberId) {
        return heartMapper.existHeart(dailyArticleId, memberId);
    }

    /**
     * 좋아요 총 개수 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 좋아요 총 개수
     */
    @Override
    public int getTotalHeartCount(int dailyArticleId) {
        return heartMapper.findTotalHeartCount(dailyArticleId);
    }


}
