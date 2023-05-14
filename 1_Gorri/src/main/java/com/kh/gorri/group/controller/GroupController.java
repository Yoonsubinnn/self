package com.kh.gorri.group.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.gorri.group.model.service.GroupService;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupMember;

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
	public String insertGroup(@ModelAttribute Group g,@ModelAttribute GroupMember gm,
							  Model model, HttpServletRequest request,
							  @RequestParam("file") ArrayList<MultipartFile> file) {
		
		MultipartFile upload = file.get(0);
		gm.setMemberId(g.getHostId());
		gm.setGrade("모임장");
		String[] returnArr = saveFile(upload, request);
		Attachment a = new Attachment();
		if(returnArr[1] != null) {
			 a.setSavePath(returnArr[0]);
			 a.setOriginalName(upload.getOriginalFilename());
			 a.setRenameName(returnArr[1]);
			 a.setAttmLevel(0);
		 }
				
		int result1 = gService.insertGroup(g);
		int result2 = gService.insertAttm(a);
		int result3 = gService.insertGroupMember(gm);
		
		System.out.println(result1);
		System.out.println(result2);
		System.out.println(result3);
		return "redirect:groupMain.gr";
	}
	
	
	public String[] saveFile(MultipartFile file, HttpServletRequest request) {
		
		String root = request.getSession().getServletContext().getRealPath("resources"); 
		String savePath = root + "\\uploadFiles";
		File folder = new File(savePath);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// 파일 이름 변경 형식 지정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int ranNum = (int)(Math.random()*100000);
		String renameFileName = sdf.format(new Date(System.currentTimeMillis())) + ranNum 
								+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		// 변경된 이름의 파일을 저장 
		String renamePath = folder + "\\" + renameFileName;
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		String[] returnArr = new String[2];
		returnArr[0] = savePath;
		returnArr[1] = renameFileName;
		
		return returnArr;
	
	}
}
