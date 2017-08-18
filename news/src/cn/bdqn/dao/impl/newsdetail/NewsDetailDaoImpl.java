package cn.bdqn.dao.impl.newsdetail;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import cn.bdqn.bean.News_Detail;
import cn.bdqn.dao.newsdetail.NewsDetailDao;
import cn.bdqn.util.BaseDao;
import cn.bdqn.util.PageUtil;
import cn.bdqn.util.ResultSetUtil;

public class NewsDetailDaoImpl extends BaseDao implements NewsDetailDao {

	@Override
	public News_Detail findById(Serializable id) {
		String sql = " SELECT * FROM news_detail WHERE id=?";
		Object[] params = { id };
		rs = excuteQuery(sql, params);
		News_Detail detail = ResultSetUtil.findT(rs, News_Detail.class);
		return detail;
	}

	@Override
	public List<News_Detail> getList() {
		String sql = "select * from news_detail";
		rs = excuteQuery(sql);
		List<News_Detail> details = null;
		try {
			details = ResultSetUtil.eachResultSet(rs, News_Detail.class);
		} finally {
			closeConnection();
		}
		return details;
	}

	@Override
	public int deleteById(Serializable id) {
		String sql = "delete from news_detail where id=?";
		Object[] params = { id };
		return excuteUpdate(sql, params);
	}

	@Override
	public int update(News_Detail detail) {
		String sql = "update news_detail set categoryId=?,title=?,content=?,author=?,summary=? where id=?";
		Object[] params = { detail.getCategoryId(), detail.getTitle(),
				detail.getContent(), detail.getAuthor(), detail.getSummary(),
				detail.getId() };

		return excuteUpdate(sql, params);
	}

	@Override
	public int add(News_Detail detail) {
		String sql = "insert into news_detail (categoryId,title,summary,content,picPath,author,createDate)"
				+ " values(?,?,?,?,?,?,?)";
		Object[] params = { detail.getCategoryId(), detail.getTitle(),
				detail.getSummary(), detail.getContent(), detail.getPicPath(),
				detail.getAuthor(), detail.getCreateDate() };

		return excuteUpdate(sql, params);
	}

	@Override
	public int findPageCounts() {
		// String sql = "select count(1) as a from news_detail";
		String sql = "select count(1) from news_detail";
		rs = excuteQuery(sql);
		int totalCounts = 0;
		try {
			if (rs.next()) {
				// totalCounts = rs.getInt("a");
				totalCounts = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return totalCounts;
	}

	@Override
	public List<News_Detail> findPageList(PageUtil util) {
		String sql = "select * from news_detail LIMIT ?,?";
		Object[] params = { (util.getPageIndex() - 1) * util.getPageSize(),
				util.getPageSize() };
		// 分页显示的新闻列表
		List<News_Detail> list = null;
		// News_Detail detail = null;
		rs = excuteQuery(sql, params);

		try {
			list = ResultSetUtil.eachResultSet(rs, News_Detail.class);

		} finally {
			closeConnection();
		}
		return list;

	}
}
