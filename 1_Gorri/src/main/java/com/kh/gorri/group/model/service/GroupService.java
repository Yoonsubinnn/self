package com.kh.gorri.group.model.service;

import java.util.ArrayList;
import java.util.Properties;

import com.kh.gorri.common.model.vo.PageInfo;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupBoard;
import com.kh.gorri.group.model.vo.GroupMember;
import com.kh.gorri.member.model.vo.Member;

public interface GroupService {

	int insertGroup(Group g);

	int insertAttm(Attachment a);

	int insertGroupMember(GroupMember gm);

	int getListCount();

	ArrayList<Group> selectGroupList(PageInfo pi);

	ArrayList<Attachment> selectAttmGroupList();

	ArrayList<Group> searchGroup(Properties search);

	// 보서)글번호에 맞는 데이터 셀렉
	ArrayList<Group> selectGroupView(int groupNo);

	// 보서)모임 번호에 맞는 board 수 셀렉
	int getListCount(int i);

	// 보서)페이징 + board 셀렉
	ArrayList<GroupBoard> selectBoardList(PageInfo pi, int i);

	GroupBoard selectDetailBoard(GroupBoard gb);

	ArrayList<Group> selectGroupListWithHost(PageInfo pi);

	int getMemberCount(int membershipNo);

	int getCategoryListCount(String category);

	ArrayList<Group> selectCategoryGroupList(String category, PageInfo pi);

	ArrayList<Member> getGroupMemberList(int membershipNo, PageInfo pi);

	Attachment getGroupPic(int membershipNo);

	Group getGroupInfo(int membershipNo);

	int deleteMember(GroupMember gc);

	ArrayList<Group> getMainGroupList();

	// 0517 가입 여부
	int checkStatus(String login, int membershipNo);

	// 0517 관리자 여부
	int checkAdmin(String login, int membershipNo);

	// 0517 상세글 보기
	GroupBoard selectDetailBoard(int boardNo);

	// 0517글 등록
	int insertBoard(GroupBoard groupBoard);

	// 최신 글번호 조회 (등록 후 상세글로 가기 위함)
	int selectNewBoardNo(String id);

	// 모임 게시글 사진 첨부
	int insertGroupAttm(Attachment attachment);

	// 모임 게시글 사진 조회
	ArrayList<Attachment> selectAttm(int boardNo);
	
	// 수빈: 모임 가입
	int groupJoin(GroupMember gm);

	int checkGroupAdmin(GroupMember gm);



	


}
