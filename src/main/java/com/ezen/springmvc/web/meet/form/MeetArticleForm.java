package com.ezen.springmvc.web.meet.form;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MeetArticleForm {
    private int meetArticleId;
    private String title;
    private String content;
    private String regdate;
    private String time;
    private int enter;
    private int hitcount;
    private int categoryId;
    private String memberId;
    private int placeId;
}