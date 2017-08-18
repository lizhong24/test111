package cn.bdqn.service.impl.category;

import java.io.Serializable;
import java.util.List;

import cn.bdqn.bean.News_Category;
import cn.bdqn.dao.DaoFactory;
import cn.bdqn.dao.category.CategoryDao;
import cn.bdqn.service.category.CategoryService;
import cn.bdqn.util.PageUtil;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao dao;

	public CategoryServiceImpl() {
		// 实例化需要的dao层对象
		dao = (CategoryDao) DaoFactory.getDaoImpl("CategoryDao");
	}

	@Override
	public News_Category findById(Serializable id) {
		return dao.findById(id);
	}

	@Override
	public List<News_Category> getList() {
		// TODO Auto-generated method stub
		return dao.getList();
	}

	@Override
	public int deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.deleteById(id);
	}

	@Override
	public int update(News_Category t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public int add(News_Category t) {
		// TODO Auto-generated method stub
		return dao.add(t);
	}

	@Override
	public int findPageCounts() {
		// TODO Auto-generated method stub
		return dao.findPageCounts();
	}

	@Override
	public List<News_Category> findPageList(PageUtil util) {
		// TODO Auto-generated method stub
		return dao.findPageList(util);
	}
}
