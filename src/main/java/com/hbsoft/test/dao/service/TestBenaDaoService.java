package com.hbsoft.test.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.test.dao.TestBenaDao;
import com.hbsoft.test.bean.TestBena;

@Service
public class TestBenaDaoService {
	@Autowired
	private TestBenaDao testBenaDao;

	/**
	 * 插入一条数据
	 * 
	 * @param testBena
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(TestBena testBena) throws Exception {
		Integer i = testBenaDao.insert(testBena);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param testBena
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(TestBena testBena) throws Exception {
		Integer prikey = null;
		Integer i = testBenaDao.insertPrikey(testBena);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = testBena.getId_prikey();
		if (o instanceof Integer) {
			prikey = (Integer) o;
		}
		return prikey;
	}

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return 返回影响行数
	 * @throws Exception
	 */
	public Integer addBatch(List<TestBena> list) throws Exception {
		Integer i = testBenaDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param testBena
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(TestBena testBena) throws Exception {
		Integer i = testBenaDao.delete(testBena);
		if ( i==0 ) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 按主键删除单条记录
	 * 
	 * @param id
	 * @return 返回是否删除成功
	 * @throws Exception
	 */
	public Boolean removeOne(Integer id) throws Exception {
		Integer i = testBenaDao.deleteOne(id);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return true;
	}

	/**
	 * 按主键删除多条记录
	 * 
	 * @param ids
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer removeBatch(List<Integer> ids) throws Exception {
		Integer i = testBenaDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param testBena
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(TestBena testBena) throws Exception {
		Integer i = testBenaDao.update(testBena);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param testBena
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(TestBena testBena) throws Exception {
		Integer i = testBenaDao.updateEmpty(testBena);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public TestBena getById(Integer id) throws Exception {
		return testBenaDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param testBena
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public TestBena getByField(TestBena testBena) throws Exception {
		return testBenaDao.findByField(testBena);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param testBena
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<TestBena> getAll(TestBena testBena) throws Exception {
		return testBenaDao.findAll(testBena);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param testBena
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(TestBena testBena) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = testBenaDao.findPagingCount(testBena);
		List<TestBena> list = testBenaDao.findPagingData(testBena);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public List<TestBena> getTreeAll(TestBena testBena) throws Exception {
		return testBenaDao.findTreeData(testBena);
	}
}
