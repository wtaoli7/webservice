package com.entstudy.info.app.webservice.mapper;

import java.util.Map;

import com.entstudy.info.app.webservice.model.StuInfo;

/**
 * @ClassName: Application
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liyujie
 * @date 2016年7月20日
 * 
 */
public interface StuInfoMapper {

	/**
	 * 
	 * @Description: 根据学生编码查询学生信息 @return 设定文件 @throws
	 */
	public StuInfo findByNo(String no) throws Exception;

	/**
	 * @Description: 根据老师ID 名字验证信息 写卡的时候进行验证
	 * @param fid
	 * @param teachername
	 * @return
	 * @throws Exception
	 */
	public int valiStuInfo(Map map) throws Exception;

}
