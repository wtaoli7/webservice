package com.entstudy.info.app.webservice.mapper;

import java.util.Map;

/**
 * @ClassName: Application
 * @Description: 记录开卡的信息
 * @author liyujie
 * @date 2016年7月20日
 * 
 */
public interface RecordInfoMapper {
	/**
	 * @Description: 根据编码 和角色识别来插入考勤 老师/学生编码 encoding 老师/学生角色role(TE/ST)
	 * @param encoding
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int insertAttenInfo(Map map) throws Exception;

	/**
	 * @Description: 验证登录信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int valiLoginInfo(Map map) throws Exception;

	/**
	 * @Description: 插入开卡人信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
//	public int insertCardInfo(Map map) throws Exception;

	/**
	 * @Description: 更改持卡人信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
//	public int updateCarfInfo(Map map) throws Exception;

	/**
	 * @Description: 根据Card_id 查询持卡人信息
	 * @param card_id
	 * @return
	 * @throws Exception
	 */
//	public int selectCardInfo(Map map) throws Exception;
	/**
	 * 验证ic卡 持有人信息 并插入
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int valicard(Map map)throws Exception;
}
