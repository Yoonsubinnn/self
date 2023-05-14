package com.kh.gorri.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.gorri.group.model.service.GroupService;
import com.kh.gorri.group.model.vo.Group;

@Controller
public class GroupController {
	
	@Autowired
	private GroupService gService;
	
	@RequestMapping("groupMain.gr")
	public String groupMain() {
		return "groupMain2";
	}
	
	@RequestMapping(value="groupDetailY.gr")
	public String groupDetailY() {
		return "group_yJoin"; 
	}
	
	@RequestMapping(value="groupDetailN.gr")
	public String groupDetailN() {
		return "group_nJoin"; 
	}
	
	
	@RequestMapping(value="groupMaking.gr")
	public String groupMaking() {
		return "groupMaking"; 
	}
	
	@RequestMapping(value="groupJoin.gr")
	public String groupJoin() {
		return "groupJoin"; 
	}
	
	@RequestMapping(value="calendar.gr")
	public String calendar() {
		return "calendar"; 
	}
	
	@RequestMapping(value="groupAdmin.gr")
	public String groupAdmin() {
		return "groupAdmin"; 
	}
	
	@RequestMapping(value="groupDetailAdmin.gr")
	public String groupDetailAdmin() {
		return "group_Y_Admin"; 
	}
	
	@RequestMapping(value="groupUpdate.gr")
	public String groupUpdate() {
		return "groupUpdate"; 
	}
	
	@RequestMapping("insertGroup.gr")
	public String insertGroup(@ModelAttribute Group g, Model model) {
		System.out.println(g.getHostId());
		System.out.println(g.getMembershipContent());
		System.out.println(g.getMembershipName());
		return "redirect:groupMain.gr";
	}
}
