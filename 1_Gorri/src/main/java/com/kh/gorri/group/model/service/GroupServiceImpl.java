package com.kh.gorri.group.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.gorri.group.model.dao.GroupDAO;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupMember;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private GroupDAO gDAO;

	@Override
	public int insertGroup(Group g) {
		return gDAO.insertGroup(sqlSession, g);
	}

	@Override
	public int insertAttm(Attachment a) {
		return gDAO.insertAttm(sqlSession, a);
	}

	@Override
	public int insertGroupMember(GroupMember gm) {
		return gDAO.insertGroupMember(sqlSession, gm);
	}

	
}
