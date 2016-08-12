package com.entstudy.info.app.webservice.mapper;

import java.util.Map;

import com.entstudy.info.app.webservice.model.TeaInfo;

public interface TeaInfoMapper {

	
	/**
	 * 
	* @Description: 根据老师ID 查询老师信息
	* @param id
	* @return
	* @throws Exception    设定文件  
	* @throws
	 */
	public TeaInfo findByencoding(String encoding) throws Exception;
	/**
	 * @Description: 根据老师ID 名字验证信息 写卡的时候进行验证
	 * @param fid
	 * @param teachername
	 * @return
	 * @throws Exception
	 */
	public int valiteainfo(Map map) throws Exception;


}
