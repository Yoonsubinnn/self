package com.kh.gorri.group.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.gorri.common.model.vo.PageInfo;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupBoard;
import com.kh.gorri.group.model.vo.GroupMember;
import com.kh.gorri.member.model.vo.Member;

@Repository
public class GroupDAO {

	public int insertGroup(SqlSessionTemplate sqlSession, Group g) {
		return sqlSession.insert("groupMapper.insertGroup", g);
	}

	public int insertAttm(SqlSessionTemplate sqlSession, Attachment a) {
		return sqlSession.insert("groupMapper.insertAttm", a);
	}

	public int insertGroupMember(SqlSessionTemplate sqlSession, GroupMember gm) {
		return sqlSession.insert("groupMapper.insertGroupMember", gm);
	}

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("groupMapper.getListCount");
	}

	public ArrayList<Group> selectGroupList(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1 ) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("groupMapper.selectGroupList", null, rowBounds);
	}
	
	public ArrayList<Attachment> selectAttmGroupList(SqlSessionTemplate sqlSession){
		return (ArrayList)sqlSession.selectList("groupMapper.selectAttmGroupList");
	}

	public ArrayList<Group> searchGroupList(SqlSessionTemplate sqlSession, Properties search) {
		String searchCate = search.getProperty("searchCate");
		String searchWord = search.getProperty("searchWord");
		
		if(searchCate == "모임장") {
			return (ArrayList)sqlSession.selectList("groupMapper.searchGroupByHost", searchWord);			
		} else if(searchCate == "모임명"){
			return (ArrayList)sqlSession.selectList("groupMapper.searchGroupByName", searchWord);	
		} else {
			return (ArrayList)sqlSession.selectList("groupMapper.searchGroup", searchWord);
		}
	}
	

	public ArrayList<Group> selectGroupView(SqlSessionTemplate sqlSession, int groupNo) {
		return (ArrayList) sqlSession.selectList("groupMapper.selectGroupView", groupNo);
	}

	public int selectListCount(SqlSessionTemplate sqlSession, int i) {
		return sqlSession.selectOne("groupMapper.selectListCount", i);
	}

	public ArrayList<GroupBoard> selectBoardList(SqlSessionTemplate sqlSession, PageInfo pi, int i) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList) sqlSession.selectList("groupMapper.selectBoardList", i, rowBounds);
	}

	// 0514 상세 게시글 조회
	public GroupBoard selectDetailBoard(SqlSessionTemplate sqlSession, GroupBoard gb) {
		return sqlSession.selectOne("groupMapper.selectDetailBoard", gb);
	}

	public ArrayList<Group> selectGroupListWithHost(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("groupMapper.selectGroupListWithHost", null, rowBounds);
	}
	
	// 모임 회원 수
	public int getMemberCount(SqlSessionTemplate sqlSession, int membershipNo) {
		return sqlSession.selectOne("groupMapper.getMemberCount", membershipNo);
	}

	public int getCategoryListCount(SqlSessionTemplate sqlSession, String category) {
		return sqlSession.selectOne("groupMapper.getCategoryListCount", category);
	}

	public ArrayList<Group> selectCategoryGroupList(SqlSessionTemplate sqlSession, String category, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("groupMapper.selectCategoryGroupList", category, rowBounds);
	}

	public ArrayList<Member> getGroupMemberList(SqlSessionTemplate sqlSession, int membershipNo, PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("groupMapper.getGroupMemberList", membershipNo, rowBounds);
	}

	public Attachment getGroupPic(SqlSessionTemplate sqlSession, int membershipNo) {
		return sqlSession.selectOne("groupMapper.getGroupPic", membershipNo);
	}

	public Group getGroupInfo(SqlSessionTemplate sqlSession, int membershipNo) {
		return sqlSession.selectOne("groupMapper.getGroupInfo", membershipNo);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, GroupMember gc) {
		return sqlSession.update("groupMapper.deleteMember", gc);
	}

	public ArrayList<Group> getMainGroupList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("groupMapper.getMainGroupList");
	}

	public int checkStatus(SqlSessionTemplate sqlSession, String login, int membershipNo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("login", login);
		paramMap.put("membershipNo", membershipNo);
		return sqlSession.selectOne("groupMapper.checkStatus", paramMap);
	}

	public int checkAdmin(SqlSessionTemplate sqlSession, String login, int membershipNo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("login", login);
		paramMap.put("membershipNo", membershipNo);
		return sqlSession.selectOne("groupMapper.checkAdmin", paramMap);
	}

	public GroupBoard selectDetailBoard(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("groupMapper.selectDetailBoard", boardNo);
	}

	public int insertBoard(SqlSessionTemplate sqlSession, GroupBoard groupBoard) {
		return sqlSession.insert("groupMapper.insertBoard", groupBoard);
	}

	public int selectNewBoardNo(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("groupMapper.selectNewBoardNo", id);
	}

	public int insertGroupAttm(SqlSessionTemplate sqlSession, Attachment attachment) {
		return sqlSession.insert("groupMapper.insertGroupAttm", attachment);
	}

	public ArrayList<Attachment> selectAttm(SqlSessionTemplate sqlSession, int boardNo) {
		return (ArrayList) sqlSession.selectList("groupMapper.selectAttm", boardNo);
	}

}
