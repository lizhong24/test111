package cn.bdqn.service.impl.newsdetail;

import java.io.Serializable;
import java.util.List;

import cn.bdqn.bean.News_Detail;
import cn.bdqn.dao.DaoFactory;
import cn.bdqn.dao.newsdetail.NewsDetailDao;
import cn.bdqn.service.newsdetail.NewsDetailService;
import cn.bdqn.util.BaseDao;
import cn.bdqn.util.PageUtil;

public class NewsDetailServiceImpl extends BaseDao implements NewsDetailService {

	private NewsDetailDao dao;

	public NewsDetailServiceImpl() {
		// 实例化需要的dao层对象
		dao = (NewsDetailDao) DaoFactory.getDaoImpl("NewsDetailDao");
	}

	@Override
	public News_Detail findById(Serializable id) {
		return dao.findById(id);
	}

	@Override
	public List<News_Detail> getList() {
		// TODO Auto-generated method stub
		return dao.getList();
	}

	@Override
	public int deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.deleteById(id);
	}

	@Override
	public int update(News_Detail t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public int add(News_Detail t) {
		// TODO Auto-generated method stub
		return dao.add(t);
	}

	@Override
	public int findPageCounts() {
		// TODO Auto-generated method stub
		return dao.findPageCounts();
	}

	@Override
	public List<News_Detail> findPageList(PageUtil util) {
		// TODO Auto-generated method stub
		return dao.findPageList(util);
	}
}
