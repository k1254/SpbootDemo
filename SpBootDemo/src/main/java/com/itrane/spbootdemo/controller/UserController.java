package com.itrane.spbootdemo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itrane.common.util.WebAppUtil;
import com.itrane.common.util.model.DataTableObject;
import com.itrane.common.util.model.DtAjaxData;
import com.itrane.spbootdemo.cmd.UserCmd;
import com.itrane.spbootdemo.model.User;
import com.itrane.spbootdemo.repo.UserRepository;
import com.itrane.spbootdemo.service.DbAccessException;
import com.itrane.spbootdemo.service.UserService;

/**
 * ユーザー情報の処理を行うコントローラ.
 */
@Controller
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;

	// %%%%% 一覧表 %%%%%%%%%%
	/**
	 * ユーザー一覧ビューを表示する. (管理者権限)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ModelAndView userList(@Param("mode") String mode) {
		log.debug("mode=" + mode);
		// デモ用にテストデータを登録
		createData();
		return getModelAndView(new ModelAndView("views/userList"),
				new UserCmd(), new User());
	}

	/**
	 * 取得ページ情報に基づいて DataTable を更新する.
	 * 
	 * @param request
	 * @return DataTableObject の JSON文字列
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/userPage", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getUserPage(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String qs = request.getQueryString();
		List<User> users = null;
		DtAjaxData dta = WebAppUtil.getDtAjaxData(qs);
		long total = userRepo.count();
		users = userService.findPage(dta.start, dta.length, dta.search.value,
				dta.getSortDir(), dta.getSortCol());
		DataTableObject<User> dt = new DataTableObject<User>(users, dta.draw,
				total, (int) total);
		return WebAppUtil.toJson(dt);
	}

	// %%%%% 新規/編集フォーム %%%%%%%%%%
	/**
	 * ユーザーを新規追加.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public ModelAndView createUser() {
		return getModelAndView(new ModelAndView("views/userForm"),
				new UserCmd(0, "新規ユーザーを登録してください"), new User());
	}

	/**
	 * id で指定したユーザーを編集.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable Long id) {
		return getModelAndView(new ModelAndView("views/userForm"),
				new UserCmd(0, "選択したユーザーを修正してください"), userRepo.findOne(id));
	}

	/**
	 * ユーザー入力フォームのデータ・submit処理.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(@Valid @ModelAttribute("user") User user,
			BindingResult result, @ModelAttribute("cmd") UserCmd cmd) {
		log.debug(user.toString());
		boolean flgNew = user.getId() == null ? true : false;
		ModelAndView mav = new ModelAndView("views/userList");
		if (!result.hasErrors()) {
			cmd.setStatus(1);
			try {
				user = userService.save(user);
				cmd.setStatus(1);
			} catch (DbAccessException dex) {
				cmd.setStatus(9);
				if (dex.getErrorCode() == DbAccessException.OPTIMISTICK_LOCK_ERROR) {
					log.error("楽観的ロックエラー");
					cmd.setStatus(99);
				} else if (dex.getErrorCode() == DbAccessException.DUPLICATE_ERROR) {
					log.error("ユーザー名重複エラー");
					result.addError(new FieldError("user", "name",
							"このユーザー名はすでに登録されています"));
				}
			}
			log.debug("save: "+user.toString());
		} else {
			//検証エラーの場合は新規/修正とも編集画面に戻る
			cmd.setStatus(9);
			log.debug("error: "+user.toString());
			mav.setViewName("views/userForm");
		}
		if (flgNew) {
			//新規モード
			mav.setViewName("views/userForm");
			if (cmd.getStatus()==1) {
				user = new User();
			}
		}
		return getModelAndView(mav, cmd, user);
	}

	/*
	 * 渡された ModelAndView に UserCmd と User をセットして返す。
	 */
	private ModelAndView getModelAndView(ModelAndView mav, UserCmd cmd,
			User user) {
		mav.addObject("cmd", cmd);
		mav.addObject("user", user);
		return mav;
	}

	private void createData() {
		List<User> users = userRepo.findAll();
		if (users.size() == 0) {
			userRepo.save(new User("user1", "岡崎賢治", "おかざきけんじ", "1970-01-01",
					"男"));
			userRepo.save(new User("user2", "吉村和夫", "よしむらかずお", "1970-02-01",
					"男"));
			userRepo.save(new User("user3", "鈴木由美子", "すずきゆみこ", "1970-03-01",
					"女"));
		}
	}
}
