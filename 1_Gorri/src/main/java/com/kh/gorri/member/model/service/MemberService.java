package com.kh.gorri.member.model.service;

import java.util.ArrayList;

import com.kh.gorri.common.model.vo.PageInfo;
import com.kh.gorri.member.model.vo.Member;

public interface MemberService {

	Member login(Member m);

	int findPwd(Member m);

	int changePwd(Member m);
	
	int insertMember(Member m);

	int checkId(String id);

	int checkNick(String nickName);

	int getListCount();

	ArrayList<Member> selectMemberList(PageInfo pi);

//	ArrayList<Member> findId(Member m);
}
