package com.kh.gorri.group.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.gorri.common.Pagination;
import com.kh.gorri.common.model.vo.PageInfo;
import com.kh.gorri.group.model.exception.GroupException;
import com.kh.gorri.group.model.service.GroupService;
import com.kh.gorri.group.model.vo.Attachment;
import com.kh.gorri.group.model.vo.Group;
import com.kh.gorri.group.model.vo.GroupBoard;
import com.kh.gorri.group.model.vo.GroupMember;
import com.kh.gorri.member.model.vo.Member;

@Controller
public class GroupController {
	
	@Autowired
	private GroupService gService;
	
	@RequestMapping("groupMain.gr")
	public String groupMain(@RequestParam(value="page", required=false) Integer page, Model model) {

		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = gService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 9);
		
		ArrayList<Group> gList = gService.selectGroupList(pi);
		ArrayList<Attachment> gAttm = gService.selectAttmGroupList();
		
		System.out.println(gList);
		if(gList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("gList", gList);
			model.addAttribute("gAttm", gAttm);
			
		} else {
			throw new GroupException("모임 조회를 실패했습니다.");
		}
		
		
		return "groupMain2";
	}
	
	
	@RequestMapping(value="groupDetailN.gr")
	public String groupDetailN(@RequestParam("membershipNo") int membershipNo, Model model) {
		model.addAttribute("membershipNo", membershipNo);
		return "group_nJoin"; 
	}
	
	
	@RequestMapping(value="groupMaking.gr")
	public String groupMaking(HttpServletRequest request) {
		Member loginUser = (Member)request.getSession().getAttribute("loginUser");
		System.out.println(loginUser);
		if(loginUser != null) {			
			return "groupMaking"; 
		} else {
			throw new GroupException("잘못된 접근입니다");
		}
	}
		
	@RequestMapping(value="calendar.gr")
	public String calendar() {
		return "calendar"; 
	}
	
	
	
	@RequestMapping(value="groupDetailAdmin.gr")
	public String groupDetailAdmin() {
		return "group_Y_Admin"; 
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
	
	
	// 카테고리 검색
	@RequestMapping("selectCate.gr")
	public String selectCate(@RequestParam(value="page", required=false) Integer page,
							 @RequestParam("category") String category, Model model) {
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = gService.getCategoryListCount(category);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 9);
		
		ArrayList<Group> gList = gService.selectCategoryGroupList(category, pi);
		ArrayList<Attachment> gAttm = gService.selectAttmGroupList();
		if(gList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("gList", gList);
			model.addAttribute("gAttm", gAttm);
			model.addAttribute("category", category);
		} else {
			throw new GroupException("모임 조회를 실패했습니다.");
		}		
		return "groupCateSelect";
	}
	
	
	
	// 그룹 검색
	@RequestMapping("searchGroup.gr")
	public String searchGroup(@RequestParam("searchCate") String searchCate, 
							  @RequestParam("searchWord") String searchWord,
							  @RequestParam(value="page", required=false) Integer page,
							  Model model) {
		
		Properties search = new Properties();
		search.put("searchCate", searchCate);
		search.put("searchWord", searchWord);
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = gService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 9);
		
		ArrayList<Group> searchGroupList = gService.searchGroup(search);
		ArrayList<Attachment> gAttm = gService.selectAttmGroupList();
		if(searchGroupList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("gList", searchGroupList);
			model.addAttribute("gAttm", gAttm);
			
		} else {
			throw new GroupException("모임 조회를 실패했습니다.");
		}
		
		
		
		return "groupMain2";
	}
	
	
	
	     
	   //---------------------보서 : 2023-05-14 : 가입자일 때 모임 화면 
	   @RequestMapping(value="groupDetailY.gr")
	   public String groupDetailY() {	      
	      return "redirect:selectGroupView.gr"; 
	   }

	   
	   @RequestMapping(value = "selectGroupView.gr")
	   public String selectGroupView(
	         @RequestParam("membershipNo") int membershipNo,
	         @RequestParam(value = "page", required = false) Integer currentPage, Model model
	         , HttpSession session) {
	      
	      System.out.println("모임번호 : " + membershipNo);
	      
	      //미가입자-가입자 화면 나누기 
	      Member m = (Member)session.getAttribute("loginUser");
	      String login = null;
	      if( m != null) { 
	         login = m.getUserId();
	      }
	      int checkStatus = gService.checkStatus(login, membershipNo); 
	      
	      //비관리자-관리자
	      int checkAdmin = gService.checkAdmin(login, membershipNo);
	      
	      //membershipNo에 해당되는 화면 
	      ArrayList<Group> groups = gService.selectGroupView(membershipNo); //지금 이게 0번으로 들어와서 그래...
	      model.addAttribute("list", groups);
	      
	      // boardList 가져오기
	      if (currentPage == null) { // 값이 안 들어오면
	         currentPage = 1;
	      }
	      // 행 개수 가져오기
	      int listCount = gService.getListCount(membershipNo);
	      PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
	      ArrayList<GroupBoard> boardList = gService.selectBoardList(pi, membershipNo);
	      System.out.println("boardList : " + boardList);
	      
	      //페이지, 게시글, 미가입자/가입자, 비관리자/관리자 여부 
	      if (boardList != null) {
	         model.addAttribute("pi", pi);
	         model.addAttribute("boardList", boardList); //게시글 
	         model.addAttribute("status", checkStatus);
	         model.addAttribute("adminStatus", checkAdmin);
	         model.addAttribute("membershipNo", membershipNo);
	         model.addAttribute("page", currentPage);
	         return "group_yJoin";
	      } else {
	         return "groupMain2"; // 게시글 조회 실패 시 보여줄 화면 만들기
	      }
	   }
	   
	// ---------------------보서 : 2023-05-14 : 모임 게시글 상세보기
	   @RequestMapping(value = "groupDetailBoard.gr")
	   public String groupDetailBoard(@RequestParam("boardNo") int boardNo,  @RequestParam("page") Integer page, Model model) {
	      System.out.println("groupDetailBoard");
	      //모임 글번호를 보내서 모임 번호에 일치하는 상세 글 조회 
	      GroupBoard detailBoard = gService.selectDetailBoard(boardNo);
	      //글번호에 맞는 조회 
	      ArrayList<Attachment> gAttm = gService.selectAttm(boardNo);
	      System.out.println("gAttm : " + gAttm);
	      
	      System.out.println("boardNo : " + boardNo);
	      if (detailBoard != null) {
	         model.addAttribute("list", detailBoard);
	         model.addAttribute("gList", gAttm);
	         return "detailBoardNotice";
	      } else {
	         return "groupMain2"; // 게시글 상세 조회 실패 시 보여줄 화면 만들기
	      }
	   }

	   
	   // 관리자 모임 페이지
	   @RequestMapping("adminMoim.gr")
	   public String adminMember(@RequestParam(value="page", required=false) Integer page, Model model) {
			int currentPage = 1;
			if(page != null) {
				currentPage = page;
			}
			
			int listCount = gService.getListCount();
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
			
			ArrayList<Group> gList = gService.selectGroupListWithHost(pi);
			if(gList != null) {
				model.addAttribute("pi", pi);
				model.addAttribute("gList", gList);
				return "adminMoim";
				
			} else {
				throw new GroupException("모임 조회를 실패했습니다.");
			}
			
			
			
		}	   
		
		@RequestMapping("deleteMember.gr")
		public String deleteMember(@ModelAttribute GroupMember gc, Model model) {
			int result = gService.deleteMember(gc);
			int membershipNo = gc.getMembershipNo();
			if(result>0) {
				model.addAttribute("membershipNo", membershipNo);
				return "redirect:groupAdmin.gr";
			} else {
				throw new GroupException("멤버 추방에 실패했습니다.");
			}
		}
		
		
		// 모임 수정
		@RequestMapping(value="groupUpdate.gr")
		public String groupUpdate(@RequestParam("membershipNo") int membershipNo, Model model) {
			
			Attachment a = gService.getGroupPic(membershipNo);
			Group membership = gService.getGroupInfo(membershipNo);
			System.out.println(membership);
			
			model.addAttribute("groupPic", a );
			model.addAttribute("groupInfo", membership );
			model.addAttribute("membershipNo", membershipNo);
			return "groupUpdate"; 
		}
		
		
		// ---------------------보서 : 2023-05-15 : 모임 게시글 작성하기
		// 상세 페이지에서 작성하기 버튼
		@RequestMapping("groupBoardWrite.gr")
		public String groupBoardWrite(@RequestParam(value = "page", required = false) Integer page,
				@RequestParam("membershipNo") int groupNo, Model model) {

			model.addAttribute("groupNo", groupNo);
			model.addAttribute("page", page);

			return "boardWrite"; // 글 작성 화면에서 데이터 쓰고 submit누르면
		}

		
		
		// 작성 공간에서 submit 클릭
		@RequestMapping("insertBoard.gr")
		public String groupBoardWrite(@ModelAttribute GroupBoard groupBoard, HttpServletRequest request,
				@RequestParam(value = "page", required = false) Integer page,
				@RequestParam("file") ArrayList<MultipartFile> file, Model model) {

			int currentPage = 1;
			if (page != null) {
				currentPage = page;
			}

			String id = ((Member) request.getSession().getAttribute("loginUser")).getUserId();
			groupBoard.setUsersId(id);
			System.out.println("로그인계정 : " + id);

			// 첨부파일
			Attachment attachment = new Attachment();
			ArrayList<Attachment> list = new ArrayList<>();
			for (int i = 0; i < file.size(); i++) {
				MultipartFile upload = file.get(i); // 내용이 빈 파일도 들어옴
				// [MultipartFile[field="file", filename=짱구.png, contentType=image/png,
				// size=1174898]]
				if (upload != null && !upload.isEmpty()) { // 추가 파일이 있다면
					String[] returnArr = saveFile(upload, request); // saveFile메소드에 넘겨 이름 변경/저장소에 저장

					if (returnArr[1] != null) { // 내용이 있는 파일만 list에 담기
						attachment.setSavePath(returnArr[0]); // 경로 변경
						attachment.setOriginalName(upload.getOriginalFilename());
						attachment.setRenameName(returnArr[1]); // 변경 이름 넣기
						attachment.setAttmLevel(1);
						attachment.setBoardType("모임");
						attachment.setUserId(id);
						list.add(attachment);
					}
				}
			}
			System.out.println("attachment : " + attachment);
			System.out.println("groupBoard : " + groupBoard);

			// -------------------------------------------글 insert
			// GroupBoard - Attachment 각각 하나씩 저장
			int result1 = gService.insertBoard(groupBoard); // 글 등록
			System.out.println("result1 : " + result1);
			// 최신 글번호 조회
			int newBoardNo = gService.selectNewBoardNo(id);
			System.out.println("특정 아이디의 최신 글 번호 조회 : " + newBoardNo);

			int result2 = gService.insertGroupAttm(attachment); // 사진 등록
			System.out.println("사진 추가 : " + result2);

			System.out.println("그룹번호 : " + groupBoard.getGroupNo());
			// 글 등록 직후 등록한 게시글 바로 볼 수 있도록 boardNo 넘기기
			return "redirect:groupDetailBoard.gr?page=" + currentPage + "&boardNo=" + newBoardNo;

		}
		
		// 수빈 : 모임 가입 페이지 23.05.17
		@RequestMapping(value = "groupJoin.gr")
		public String groupJoin(@RequestParam("membershipNo") int membershipNo, @RequestParam("page") int page, HttpSession session, Model model) {
			Member m = (Member)session.getAttribute("loginUser");
			GroupMember gm = new GroupMember();
			gm.setMemberId(m.getUserId());
			gm.setMembershipNo(membershipNo);
			int result = gService.groupJoin(gm);
			if(result > 0) {				
				return "redirect:selectGroupView.gr?membershipNo=" + membershipNo + "&page=" + page;
			} else {
				throw new GroupException("모임 가입에 실패했습니다");
			}
		}
		
		// 수빈 : 모임장 페이지 수정 23.05.17
		@RequestMapping(value="groupAdmin.gr")
		public String groupAdmin(@RequestParam("membershipNo") int membershipNo,
								@RequestParam(value="page", required=false) Integer page, Model model, HttpSession session) {
			
		    Member loginUser = (Member)session.getAttribute("loginUser");
		   	GroupMember gm = new GroupMember();
		   	gm.setMemberId(loginUser.getUserId());
			gm.setMembershipNo(membershipNo);
			int result = gService.checkGroupAdmin(gm);		
			
			if(result > 0) {
				int currentPage = 1;
				if(page != null) {
					currentPage = page;
				}
				
				int memberCount = gService.getMemberCount(membershipNo);
				PageInfo pi = Pagination.getPageInfo(currentPage, memberCount, 10);
				
				ArrayList<Member> mList = gService.getGroupMemberList(membershipNo, pi);
				if(mList != null) {
					model.addAttribute("pi", pi);
					model.addAttribute("mList", mList);
					model.addAttribute("membershipNo", membershipNo);
					return "groupAdmin";
					
				} else {
					throw new GroupException("모임 회원 조회를 실패했습니다.");
				}
				
			} else {
				throw new GroupException("잘못된 접근입니다");
			}
		}		
		
}
