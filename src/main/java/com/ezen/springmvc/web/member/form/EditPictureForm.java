package com.ezen.springmvc.web.member.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class EditPictureForm {
    private MultipartFile profileImage;
}
