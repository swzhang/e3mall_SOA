package com.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 内容管理Service
 * 
 * @author ZNG
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;

	public DataGridResult getContentList(Long categoryId, int page, int rows) {
		// 1、设置分页信息。使用Mybatis的分页插件
		PageHelper.startPage(page, rows);
		// 2、执行查询，根据分类id查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 3、取分页结果total
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		// 4、封装到DataGridResult对象中
		DataGridResult result = new DataGridResult();
		result.setTotal(total);
		result.setRows(list);
		// 5、返回DataGridResult
		return result;
	}

	public E3Result addContent(TbContent content) {
		// 1、设置属性
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		// 2、插入到数据库中
		contentMapper.insert(content);
		// 缓存同步
		jedisClient.hdel("content-info",content.getCategoryId().toString());
		// 3、返回成功
		return E3Result.ok();
	}

	public E3Result editContent(TbContent content) {
		// 1、更新内容修改时间
		content.setUpdated(new Date());
		// 2、更新内容信息到数据库
		contentMapper.updateByPrimaryKeySelective(content);
		// 3、返回成功
		return E3Result.ok();
	}

	public E3Result deleteContent(String ids) {
		String[] contentIds = ids.split(",");
		for (String id : contentIds) {
			// 删除数据库中内容信息
			contentMapper.deleteByPrimaryKey(new Long(id));
		}
		// 返回成功
		return E3Result.ok();
	}

	public List<TbContent> getContentListByCid(long cid) {
		// 先从redis缓存查询,添加缓存不能影响正常业务逻辑
		try {
			String result = jedisClient.hget("content-info", cid+"");
			if (StringUtils.isNotBlank(result)) {
				return JsonUtils.jsonToList(result, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据分类id查询内容列表
		// 设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询，返回结果
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 如果redis缓存中没有，在缓存中添加
		try {
			jedisClient.hset("content-info", cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
