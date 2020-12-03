package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.NoticeBean;

@Mapper
public interface NoticeBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(NoticeBean noticeBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param noticeBean
	 * @throws Exception
	 */
	public Integer insertPrikey(NoticeBean noticeBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<NoticeBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(NoticeBean noticeBean) throws Exception;

	/**
	 * 按主键删除单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteOne(Integer id) throws Exception;

	/**
	 * 按主键删除多条记录
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Integer deleteBatch(@Param("ids") List<Integer> ids) throws Exception;

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(NoticeBean noticeBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param noticeBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(NoticeBean noticeBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NoticeBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public NoticeBean findByField(NoticeBean noticeBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public List<NoticeBean> findAll(NoticeBean noticeBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(NoticeBean noticeBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public List<NoticeBean> findPagingData(NoticeBean noticeBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param noticeBean
	 * @return
	 * @throws Exception
	 */
	public List<NoticeBean> findTreeData(NoticeBean noticeBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
}
