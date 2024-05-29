package com.ezen.springmvc.domain.member.service;

import com.ezen.springmvc.domain.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ezen.springmvc.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public void register(MemberDto memberDto) {
        memberMapper.create(memberDto);
    }

    @Override
    public MemberDto isMember(String id, String passwd) {
        return memberMapper.findByIdAndPasswd(id, passwd);
    }

    @Override
    public List<MemberDto> getMembers() {
        return memberMapper.findByAll();
    }

    @Override
    public MemberDto getMember(String id) {
        return memberMapper.findById(id);
    }

    @Override
    @Transactional
    public void editMember(MemberDto memberDto) {
        memberMapper.updateInfo(memberDto);
    }

    @Override
    @Transactional
    public void editPasswd(MemberDto memberDto) {
        memberMapper.updatePasswd(memberDto);
    }

    @Override
    @Transactional
    public void editPicture(MemberDto memberDto) {
        memberMapper.updatePicture(memberDto);
    }

    @Override
    @Transactional
    public String searchId(String name, String nickname) {
        String memberId = memberMapper.findMemberId(name, nickname);
        return memberId;
    }

    @Override
    @Transactional
    public MemberDto searchPasswd(String id, String name, String email){
        return memberMapper.findMemberPasswd(id, name, email);
    }
}
