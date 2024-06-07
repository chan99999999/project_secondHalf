package com.ezen.springmvc.domain.dailyarticle.service;

import com.ezen.springmvc.domain.common.dto.SearchDto;
import com.ezen.springmvc.domain.dailyarticle.dto.*;
import com.ezen.springmvc.domain.dailyarticle.mapper.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTML;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyArticleServiceImpl implements DailyArticleService {

    private static final Logger log = LoggerFactory.getLogger(DailyArticleServiceImpl.class);
    private final DailyArticleMapper dailyArticleMapper;
    private final ReplyMapper replyMapper;
    private final HeartMapper heartMapper;
    private final FileMapper fileMapper;
    private final TagMapper tagMapper;
    private final TagArticleMapper tagArticleMapper;

    /**
     * 신규 일상 게시글 등록 구현
     * @param dailyArticleDto 일상 게시글
     * @param fileList        파일 목록
     * @return 일상 게시글
     */
    @Override
    @Transactional
    public DailyArticleDto writeDailyArticle(DailyArticleDto dailyArticleDto, List<FileDto> fileList) {
        // 게시글 등록
        dailyArticleMapper.createDailyArticle(dailyArticleDto);

        // 파일 등록
        if (fileList != null && !fileList.isEmpty()) { // 파일 목록이 비어있지 않을 경우
            if (fileList.size() == 1) { // 단일 파일 업로드일 경우
                fileMapper.createFileDto(fileList.get(0));
            } else { // 다중 파일 업로드일 경우
                fileMapper.createFileList(fileList);
            }
        }

        // 등록된 게시글 반환
        return dailyArticleMapper.findByDailyArticleId(dailyArticleDto.getDailyArticleId());
    }

    /**
     * 검색 조건에 따른 일상 게시글 목록 반환 구현
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return 일상 게시글 목록
     */
    @Override
    public List<DailyArticleDto> getDailyArticles(int categoryId, SearchDto searchDto) {
        return dailyArticleMapper.findByAllDailyArticle(categoryId, searchDto);
    }

    /**
     * 태그 이름 및 검색 조건에 따른 일상 게시글 목록 반환 구현
     * @param categoryId 카테고리 번호
     * @param tagName    태그 이름
     * @param searchDto  검색
     * @return 일상 게시글 목록
     */
    @Override
    public List<DailyArticleDto> getDailyArticlesByTagName(int categoryId, String tagName, SearchDto searchDto) {
        return dailyArticleMapper.findByAllTagName(categoryId, tagName, searchDto);
    }

    /**
     * 일상 게시글 상세보기 구현
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     * @return 일상 게시글
     */
    @Override
    @Transactional
    public DailyArticleDto getDailyArticle(int categoryId, int dailyArticleId) {
        // 조회수 업데이트
        dailyArticleMapper.updateDailyArticleHitCount(categoryId, dailyArticleId);
        return dailyArticleMapper.readDailyArticle(categoryId, dailyArticleId);
    }

    /**
     * 검색 조건에 따른 일상 게시글 개수 반환 구현
     * @param categoryId 카테고리 번호
     * @param searchDto  검색
     * @return 일상 게시글 개수
     */
    @Override
    public int getDailyArticleCount(int categoryId, SearchDto searchDto) {
        return dailyArticleMapper.findDailyArticleCount(categoryId, searchDto);
    }

    /**
     * 일상 게시글 수정 구현
     * @param dailyArticleId        일상 게시글 번호
     * @param editedDailyArticleDto 수정된 일상 게시글
     */
    @Override
    public void editDailyArticle(int dailyArticleId, DailyArticleDto editedDailyArticleDto) {
        dailyArticleMapper.updateDailyArticle(dailyArticleId, editedDailyArticleDto);
    }

    /**
     * 일상 게시글 삭제 구현
     * @param categoryId     카테고리 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    @Override
    public void removeDailyArticle(int categoryId, int dailyArticleId) {
        dailyArticleMapper.deleteDailyArticle(categoryId, dailyArticleId);
    }

    /**
     * 일상 게시글 번호로 파일 목록 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 파일 목록
     */
    @Override
    public List<FileDto> getFiles(int dailyArticleId) {
        return fileMapper.findByFileId(dailyArticleId);
    }

    /**
     * 댓글 등록 구현
     * @param replyDto 댓글
     */
    @Transactional
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
     * 댓글 개수 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 댓글 개수
     */
    @Override
    public int getReplyCount(int dailyArticleId) {
        return replyMapper.findReplyCount(dailyArticleId);
    }

    /**
     * 댓글 수정 구현
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId        댓글 번호
     * @param content        댓글 내용
     */
    @Override
    public void editReply(int dailyArticleId, int replyId, String content) {
        replyMapper.updateReply(dailyArticleId, replyId, content);
    }

    /**
     * 댓글 삭제 구현
     * @param dailyArticleId 일상 게시글 번호
     * @param replyId        댓글 번호
     */
    @Override
    public void removeReply(int dailyArticleId, int replyId) {
        replyMapper.deleteReply(dailyArticleId, replyId);
    }

    /**
     * 좋아요 등록 및 업데이트 구현
     * @param dailyArticleId 일상 게시글 번호
     * @param memberId       회원 아이디
     */
    @Override
    @Transactional
    public boolean insertAndUpdateHeart(int dailyArticleId, String memberId, boolean checked) {
        // 인서트 또는 업데이트에 실패했을 경우를 대비하여 try~catch문 작성
        try {
            boolean heartExists = heartMapper.existHeart(dailyArticleId, memberId) > 0;

            // 좋아요 존재 유무에 따라 좋아요 업데이트 또는 좋아요 생성
            if (heartExists) {
                // 클라이언트로부터 수신한 checked 값에 따라 증가, 감소 분기
                if (checked) {
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
     * 회원 아이디별 좋아요 개수 반환 구현
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
     * 일상 게시글별 좋아요 총 개수 반환 구현
     * @param dailyArticleId 일상 게시글 번호
     * @return 좋아요 총 개수
     */
    @Override
    public int getTotalHeartCount(int dailyArticleId) {
        return heartMapper.findTotalHeartCount(dailyArticleId);
    }

    /**
     * 태그 이름으로 기존 태그 또는 새로운 태그 반환 구현
     * @param tagName 태그 이름
     * @return 태그
     */
    @Override
    @Transactional
    public TagDto getOrCreateTag(String tagName) {
        TagDto existTag = tagMapper.findByTagName(tagName);

        // 태그 이름으로 태그 조회 후 있을 경우 기존 태그 반환
        if (existTag != null) {
            return existTag;
        } else {
            // 없을 경우 전달받은 태그 이름의 새로운 태그 생성
            TagDto newTag = TagDto.builder()
                    .tagName(tagName)
                    .build();
            tagMapper.createTag(newTag);

            // 새로 생성된 태그의 아이디를 가져와서 반환
            return tagMapper.findByTagId(newTag.getTagId());
        }
    }

    /**
     * 태그 게시글 등록 구현
     * @param tagId          태그 번호
     * @param dailyArticleId 일상 게시글 번호
     */
    @Override
    @Transactional
    public void getTagArticle(int tagId, int dailyArticleId) {
        tagArticleMapper.createTagArticle(tagId, dailyArticleId);
    }

    /**
     * 일상 게시글 번호로 태그 목록 반환
     * @param dailyArticleId 일상 게시글 번호
     * @return 태그 목록
     */
    @Override
    public List<TagDto> getTagBydailyArticleId(int dailyArticleId) {
        return tagMapper.findByDailyArticleId(dailyArticleId);
    }
}
