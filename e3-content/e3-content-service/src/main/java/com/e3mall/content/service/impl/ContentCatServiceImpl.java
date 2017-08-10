package com.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.pojo.TreeNode;
import com.e3mall.content.service.ContentCatService;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import com.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理service
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	public List<TreeNode> getContentCatList(long parentId) {
		// 1、创建查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 2、执行查询，根据parentid查询内容分类列表
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 3、把内容分类列表转换成TreeNode列表
		List<TreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加到结果列表
			resultList.add(treeNode);
		}
		// 4、返回结果
		return resultList;
	}

	public E3Result addContentCategory(long parentId, String name) {
		// 1、创建一个TbContentCategory对象
		TbContentCategory contentCategory = new TbContentCategory();
		// 2、补全属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		// 1(正常),2(删除)
		contentCategory.setStatus(1);
		// 默认排序值是1
		contentCategory.setSortOrder(1);
		// 1为true，0为false 新添加节点一定是false
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 3、插入到表中。
		contentCategoryMapper.insert(contentCategory);
		// 4、需要判断父节点的isparent
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 5、如果父节点的isparent为false应该改为true
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			// 更新到数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		// 6、返回E3Result其中包含TbContentCategory对象
		return E3Result.ok(contentCategory);
	}

	public E3Result updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok();
	}

	public E3Result deleteContentCategory(long id) {
		// 1、获取当前内容分类节点
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 2、创建自定义E3Result对象用于响应页面
		E3Result e3Result = new E3Result();
		if (!contentCategory.getIsParent()) {
			// 获取父节点id
			Long parentId = contentCategory.getParentId();
			// 创建查询条件
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			// 执行查询，根据parentid查询内容分类列表
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			// 判断该父节点下子节点个数，如果为1，删除子节点后将父节点的isParent设置为false，否则不做修改
			if (list.size() == 1) {
				// 获取父内容分类节点
				TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
				// 设置父节点变成叶子节点
				parentContentCategory.setIsParent(false);
				// 更新父节点
				contentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
			}
			// 删除内容分类子节点
			contentCategoryMapper.deleteByPrimaryKey(id);
			e3Result.setStatus(200);
		} else {
			e3Result.setStatus(201);
		}
		return e3Result;
	}

}
