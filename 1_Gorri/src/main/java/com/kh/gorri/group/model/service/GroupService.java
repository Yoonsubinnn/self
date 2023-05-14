package com.kh.gorri.group.model.service;

import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupMember;

public interface GroupService {

	int insertGroup(Group g);

	int insertAttm(Attachment a);

	int insertGroupMember(GroupMember gm);


}
