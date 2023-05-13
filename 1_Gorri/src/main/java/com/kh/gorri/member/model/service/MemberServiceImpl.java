package com.kh.gorri.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.gorri.member.model.dao.MemberDAO;
import com.kh.gorri.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDAO mDAO;

	@Override
	public Member login(Member m) {
		return mDAO.login(sqlSession, m);
	}

	@Override
	public int findPwd(Member m) {
		return mDAO.findPwd(sqlSession, m);
	}

	@Override
	public int changePwd(Member m) {
		return mDAO.changePwd(sqlSession,m);
	}
}
