package com.entstudy.info.app.webservice.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entstudy.info.app.webservice.mapper.RecordInfoMapper;
import com.entstudy.info.app.webservice.mapper.StuInfoMapper;
import com.entstudy.info.app.webservice.mapper.TeaInfoMapper;
import com.entstudy.info.app.webservice.model.StuInfo;
import com.entstudy.info.app.webservice.model.TeaInfo;

/**
 * @ClassName: AttendenceController
 * @Description: 考勤服务控制器
 * @author liwt
 * @date 2016年7月12日 下午4:43:47
 * 
 */
@RestController
@RequestMapping(value = "/attendence")
public class AttendenceController {
	private static final Logger logger = LoggerFactory
			.getLogger(AttendenceController.class);
	@Autowired
	private StuInfoMapper userMapper;
	@Autowired
	private TeaInfoMapper teaMapper;
	@Autowired
	private RecordInfoMapper redMapper;
	
	
	@RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
	public void heartbeat() {
		
	}

	/**
	 * 
	 * @Description: 根据学生编码查询学生信息 @param name @return 设定文件 @throws
	 */

	private Map<String, Object> queryStudentInfo(String encoding) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据库里查询数据
		StuInfo stuInfo = null;
		try {
			stuInfo = userMapper.findByNo(encoding);
		} catch (Exception e) {
			logger.error("数据库查询学生信息异常.....", e);
			result.put("flag", false);
			return result;
		}
		if (stuInfo == null) {
			result.put("flag", false);
			logger.debug("数据库没有这个学生信息.......");
			return result;
		} else {
			logger.debug("数据库查到信息....正在存值...");
			result.put("flag", true);
			result.put("name", stuInfo.getName());
		}
		return result;
	}

	/**
	 * @throws Exception
	 * @Description: 写卡 学生验证！ @param name @return 设定文件 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/stuvail", method = RequestMethod.POST)
	public Map<String, Object> vailStudentInfo(@RequestBody String studate)
			throws Exception {
		// 数据库里查询数据
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(studate);
		int num = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("encoding", json.get("stuNo").toString());
		map.put("name", json.get("stuName").toString());
		try {
			num = userMapper.valiStuInfo(map);
		} catch (Exception e) {
			logger.error("数据库查询学生信息异常.....", e);
		}

		if (num > 0) {
			result.put("flag", true);
		} else {
			result.put("flag", false);
		}
		return result;
	}

	/**
	 * @throws SQLException
	 * 
	 * @Description: 写卡老师验证！ @param name @return 设定文件 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/teavail", method = RequestMethod.POST)
	public Map<String, Object> vailTeacherInfo(@RequestBody String teadata)
			throws SQLException {
		// 数据库里查询数据
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(teadata);
		int num = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("encoding", json.get("teaNo").toString());
		map.put("name", json.get("teaName").toString());
		try {
			num = teaMapper.valiteainfo(map);
		} catch (Exception e) {
			logger.error("数据库查询老师信息异常.....", e);
		}

		if (num > 0) {
			result.put("flag", true);
		} else {
			result.put("flag", false);
		}
		return result;
	}

	/**
	 * 
	 * @Description: 根据老师编码查询老师信息 @param name @return 设定文件 @throws
	 */

	private Map<String, Object> queryTeacherInfo(String encoding) {
		Map<String, Object> result = new HashMap<String, Object>();
		TeaInfo teaInfo = null;
		try {
			teaInfo = teaMapper.findByencoding(encoding);
		} catch (Exception e1) {
			logger.error("数据库查询老师信息异常.....", e1);
			result.put("flag", false);
			return result;
		}
		if (teaInfo == null) {
			result.put("flag", false);
			logger.debug("数据库没有这个老师信息.......");
			return result;
		} else {
			logger.debug("数据库查到信息....正在存值...");
			result.put("flag", true);
			result.put("name", teaInfo.getName());
		}
		return result;
	}

	/**
	 * login 验证
	 * 
	 * @param teadata
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> vailloginInfo(@RequestBody String logindata)
			throws SQLException {
		// 数据库里查询数据
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(logindata);
		int num = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		// 运用MD5 SHA 加俩次密之后进去数据库进行比对！
		map.put("username", json.get("name").toString());
		map.put("password",
				PwdEncryptUtil.encrypt(json.get("password").toString()));
		try {
			num = redMapper.valiLoginInfo(map);
		} catch (Exception e) {
			logger.error("数据库查询登录信息异常.....", e);
		}
		if (num > 0) {
			result.put("flag", true);
		} else {
			result.put("flag", false);
		}
		return result;
	}

	/**
	 * 
	 * @Description: 插入考勤信息到数据库 @param name @return 设定文件 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> addAttendenceRecord(@RequestBody String data) {
		JSONObject json = JSONObject.fromObject(data);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Timestamp ts = Timestamp.valueOf(json.get("time").toString());
		map.put("time", ts);
		map.put("encoding", json.get("encoding").toString());
		map.put("role", json.get("role").toString());
		int num = 0;
		if (map.get("role").equals("TE")) {
			results = queryTeacherInfo(json.get("encoding").toString());
		} else if (map.get("role").equals("ST")) {
			results = queryStudentInfo(json.get("encoding").toString());
		}

		if (results.get("flag").equals(true)) {
			try {
				num = redMapper.insertAttenInfo(map);
			} catch (Exception e1) {
				logger.error("考勤数据插入异常.....", e1);
				result.put("flag", false);
				return result;
			}
			// num 0 失败 1 成功
			if (num > 0) {
				result.put("flag", true);
				result.put("name", results.get("name"));
			} else {
				result.put("flag", false);
				result.put("name", results.get("name"));
			}
			return result;
		} else {
			logger.debug("数据库没有信息...直接返回false");
			result.put("flag", false);
		}
		return result;

	}

	/**
	 * 验证开卡记录表有没有此卡记录 并直接插入数据
	 * 
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/vailrecode", method = RequestMethod.POST)
	public Map<String, Object> eclectRecord(@RequestBody String data) {
		JSONObject json = JSONObject.fromObject(data);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardid", json.get("cardid").toString());
		map.put("encoding", json.get("encoding").toString());
		map.put("role", json.get("role").toString());
		map.put("action_time", new Timestamp(System.currentTimeMillis()));
		map.put("username", json.get("username").toString());
		int num = 0;
		try {
			num = redMapper.valicard(map);
		} catch (Exception e1) {
			logger.error("开卡记录查询异常.....", e1);
			result.put("flag", false);
			return result;
		}
		// num 0 失败 1 成功
		if (num > 0) {
			result.put("flag", true);
		} else {
			result.put("flag", false);
		}
		return result;
	}

}
