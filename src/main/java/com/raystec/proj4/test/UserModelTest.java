package com.raystec.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.exception.RecordNotFoundException;
import com.raystec.proj4.model.UserModel;

/**
 * User Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class UserModelTest {

	/**
	 * Model object to test
	 */

	public static UserModel model = new UserModel();

	public static void main(String args[]) throws ParseException {

		testAdd();
		// testUpdate();
		// testdelete();
		// testFindByPk();
		// testFindByLogin();
		// testSearch();
		// testList();
		// testAuthenticate();
		// testRegisterUser();
		// testchangePassword();
		// testforgetPassword();
		// testresetPassword();
	}

	/**
	 * Tests add a User
	 * 
	 * @throws ParseException
	 * @throws DuplicateRecordException
	 */

	public static void testAdd() {

		try {

			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(2L);
			bean.setFirstName("priyank");
			bean.setLastName("sharma");
			bean.setLogin("priyank2311@gmail.com");
			bean.setPassword("123456");
			bean.setDob(sdf.parse("1-1-1990"));
			bean.setRoleId(1);
			bean.setUnSuccessfulLogin(1);
			bean.setGender("male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("no");
			bean.setConfirmPassword("123456");
			bean.setRegisteredIP("192.168.1.11");
			bean.setLastLoginIP("192.168.1.15");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);

			UserBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {

			UserBean bean = model.findByPK(46);
			bean.setFirstName("nc limited");
			bean.setLastName("pvt ltd");
			bean.setLogin("ncspvtltd10@gmail.com");
			bean.setPassword("658986");
			bean.setMobileNo("1545645479");
			bean.setRoleId(2);

			model.update(bean);

			UserBean updatedbean = model.findByPK(46);
			if (!"ankit".equals(updatedbean.getLogin())) {
				System.out.println("Test Update fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testdelete() {

		try {
			UserBean bean = new UserBean();

			long pk = 49;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test delete success" + bean.getId());

			UserBean deletedbean = model.findByPK(pk);
			if (deletedbean == null) {
				System.out.println("Test Delete fail");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			UserBean bean = new UserBean();
			long pk = 47;
			bean = model.findByPK(pk);

			if (bean == null) {
				System.out.println("Test Find By PK fail");

			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByLogin() {

		try {
			UserBean bean = new UserBean();
			bean = model.findByLogin("ankur2311@gmail.com");

			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();

			// bean.setFirstName("ankit");
			// bean.setId(47);
			bean.setMobileNo("1545645479");

			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testList() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.list(2, 2);

			if (list.size() < 0) {
				System.out.println("Test list fail");
			}

			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				/*
				 * System.out.println(bean.getLogin());
				 * System.out.println(bean.getPassword());
				 * System.out.println(bean.getDob());
				 * System.out.println(bean.getRoleId());
				 * System.out.println(bean.getUnSuccessfulLogin());
				 * System.out.println(bean.getGender());
				 * System.out.println(bean.getLastLogin());
				 * System.out.println(bean.getLock());
				 * System.out.println(bean.getMobileNo());
				 * System.out.println(bean.getCreatedBy());
				 * System.out.println(bean.getModifiedBy());
				 * System.out.println(bean.getCreatedDatetime());
				 * System.out.println(bean.getModifiedDatetime());
				 */}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testAuthenticate() {

		try {
			UserBean bean = new UserBean();
			bean.setLogin("ankita@gmail.com");
			bean.setPassword("123");

			bean = model.authenticate(bean.getLogin(), bean.getPassword());

			if (bean != null) {
				System.out.println("Successfully login");

			} else {
				System.out.println("Invalid login Id & password");

			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testRegisterUser() throws ParseException {
		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			// bean.setId(8);
			bean.setFirstName("vipin");
			bean.setLastName("kumar");
			bean.setLogin("ankitparmar0808@gmail.com");
			bean.setPassword("1111");
			bean.setConfirmPassword("rr");
			bean.setDob(sdf.parse("30/12/2015"));
			bean.setGender("Male");
			bean.setRoleId(2);

			long pk = model.registerUser(bean);

			System.out.println("Successfully register");

			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());

			/*
			 * UserBean registerbean = model.findByPK(pk); if (registerbean !=
			 * null) { System.out.println("Test registation fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testchangePassword() {
		try {
			UserBean bean = model.findByLogin("ankitparmar0808@gmail.com");

			String oldPassword = bean.getPassword();
			// bean.setId(47);
			bean.setPassword("pass123");
			bean.setConfirmPassword("1234");

			String newPassword = bean.getPassword();

			try {
				model.changePassword(47, oldPassword, newPassword);
				System.out.println("password has been change successfully");

			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testforgetPassword() {

		try {
			boolean b = model.forgetPassword("ankitparmar0808@gmail.com");

			System.out.println("Suucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testresetPassword() {

		UserBean bean = new UserBean();

		try {
			bean = model.findByLogin("ankita@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
