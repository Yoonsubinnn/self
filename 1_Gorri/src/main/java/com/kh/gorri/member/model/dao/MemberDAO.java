package com.kh.gorri.member.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.gorri.common.model.vo.PageInfo;
import com.kh.gorri.member.model.vo.Member;

@Repository
public class MemberDAO {

	public Member login(SqlSessionTemplate sqlSession, Member m) {
		System.out.println(m.getUserId());
		System.out.println(m.getUserPwd());
		return sqlSession.selectOne("memberMapper.login", m);
	}

	public int findPwd(SqlSessionTemplate sqlSession, Member m) {
		int result = sqlSession.selectOne("memberMapper.findPwd",m);
		System.out.println(result);
		return result;
	}

	public int changePwd(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.changePwd", m);
	}
	
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}

	public int checkId(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("memberMapper.checkId", id);
	}

	public int checkNick(SqlSessionTemplate sqlSession, String nickName) {
		return sqlSession.selectOne("memberMapper.checkNick", nickName);
	}

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("memberMapper.getListCount");
	}

	public ArrayList<Member> selectMemberList(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1 ) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		System.out.println(offset);
		System.out.println(pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("memberMapper.selectMemberList", null, rowBounds);
	}

//	public ArrayList<Member> findId(SqlSessionTemplate sqlSession, Member m) {
//		return sqlSession.selectList("memberMapper.findId", m);
//	}
	

}
