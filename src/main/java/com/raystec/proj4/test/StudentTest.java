package com.raystec.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.proj4.bean.StudentBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.StudentModel;
/**
 * Student Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class StudentTest {
	/**
	 * modell object to test
	 */
	public static StudentModel model = new StudentModel();

	public static void main(String args[]) throws Exception {
	 testAdd();
		// testFindByEmail();
		// testFindByPK();
		// testDelete();
		// testUpdate();
		// tsetList();
		//testSearch();
	}

	public static void testSearch() {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			bean.setFirstName("Manish");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/***
	 * add student
	 * 
	 * @throws DuplicateRecordException
	 * @throws ParseException
	 */
	public static void testAdd() throws DuplicateRecordException,
			ParseException {
		try {
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			bean.setFirstName("Ankit");// 1
			bean.setLastName("Sharama");// 2
			bean.setDob(sdf.parse("3/12/2015"));// 3
			bean.setMobileNo("93384332");// 4
			bean.setEmail("Erdohit56@gmail.com");// 5
			bean.setCollegeId(14L);// 6

			bean.setCreatedBy("ADmin");// 7
			bean.setModifiedBy("Admin");// 8
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));// 9
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));// 10
			long pk = model.add(bean);
			StudentBean addedBean = model.findByEmailId("ErRohit56gmail.com");
			if (addedBean == null) {
				// System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * delete
	 */
	public static void testDelete() {
		try {
			StudentBean bean = new StudentBean();
			long pk = 11L;
			bean.setId(pk);
			model.delete(bean);
			StudentBean deletebean = model.findByPK(pk);
			if (deletebean != null) {
				System.out.println("Test delte Fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * find vy pk
	 */
	public static void testFindByPK() {
		try {
			StudentBean bean = new StudentBean();
			long pk = 16L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getEmail());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCollegeName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test update Student
	 * 
	 * @throws Exception
	 */
	public static void testUpdate() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			bean = model.findByPK(1L);
			bean.setCollegeId(2L);
			bean.setFirstName("Daksh");
			bean.setLastName("Sharma");
			bean.setEmail("aman.sahu@gmail.con");
			model.update(bean);

			StudentBean updatedBean = model.findByPK(1L);
			if (!"Abirr".equals(updatedBean.getFirstName())) {
				System.out.println("Test update fail");

			}
		} catch (ApplicationException e) {
			e.printStackTrace();

		} catch (DuplicateRecordException e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * test delete Student
	 */

	public static void testFindByEmail() {
		try {
			StudentBean bean = new StudentBean();
			bean = model.findByEmailId("ErRohit56@gmail.com");
			if (bean != null) {
				System.out.println("Test find by email is fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void tsetList() {
		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.list(1, 8);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
