package com.kh.gorri;

import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.gorri.group.model.service.GroupService;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private GroupService gService;
	
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		ArrayList<Attachment> aList = gService.selectAttmGroupList();
		ArrayList<Group> gList = gService.getMainGroupList();
		ArrayList<Group> main = new ArrayList();
		for(int num=0; num<=6; num++) {
			main.add(num, gList.get(num));
		}
		model.addAttribute("gList", main);
		model.addAttribute("aList", aList);
		return "gorriMain";
	}
	
}
