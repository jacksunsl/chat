package com.hipishare.chat.server.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.hipishare.chat.server.dao.po.Uc_userPO;

/**
 * <b>uc_user[uc_user]数据访问接口</b>
 * 
 * @author sunlei
 * @date 2016-08-16 12:09:17
 */
public interface Uc_userMapper {

	/**
	 * 插入一个数据持久化对象(插入字段为传入PO实体的非空属性)
	 * <p> 防止DB字段缺省值需要程序中再次赋值
	 *
	 * @param uc_userPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insert(Uc_userPO uc_userPO);
	
	/**
	 * 插入一个数据持久化对象(含所有字段)
	 * 
	 * @param uc_userPO
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insertAll(Uc_userPO uc_userPO);

	/**
	 * 根据主键修改数据持久化对象
	 * 
	 * @param uc_userPO
	 *            要修改的数据持久化对象
	 * @return int 返回影响行数
	 */
	int updateByKey(Uc_userPO uc_userPO);

	/**
	 * 根据主键查询并返回数据持久化对象
	 * 
	 * @return Uc_userPO
	 */
	Uc_userPO selectByKey(@Param(value = "id") Integer id);


	/**
	 * 根据主键删除数据持久化对象
	 *
	 * @return 影响行数
	 */
	int deleteByKey(@Param(value = "id") Integer id);
	
	Uc_userPO getUserByAccount(@Param(value = "account")String account);
}
