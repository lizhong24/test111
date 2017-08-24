package cn.smbms.dao.bill.impl;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import cn.smbms.bean.Bill;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.util.BaseDao;

public class BillDaoImplTest {

	private BillDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new BillDaoImpl();
	}

	@Test
	public void testgetTotalCount() {

		Connection connection = null;
		Bill bill = new Bill();
		try {

			connection = BaseDao.getConnection();
			Integer result = dao.getTotalCount(connection, bill);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}

		// result = false;
		// 断言 Assert.assertTrue("增加失败", result);
		// Assert.assertEquals("0", result);
	}

}
