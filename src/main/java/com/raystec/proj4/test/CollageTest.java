package com.raystec.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.CollegeModel;
/**
 * College Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */

public class CollageTest {
	/**
	 * model object test
	 */
	public static CollegeModel model = new CollegeModel();

	public static void testNextPK() throws DatabaseException {
		System.out.println(model.nextPK());
	}

	public static void testAdd() throws DuplicateRecordException {
		try {
			CollegeBean bean = new CollegeBean();
			// bean.setId(9L);
			bean.setName("Dist");
			bean.setAddress("ashok nagar");
			bean.setState("mp");
			bean.setCity("indoe");
			bean.setPhoneNo("00451117");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test add succces");
			CollegeBean addedBean = model.findByPk(pk);
			if (addedBean == null) 
			{
				System.out.println("Test add fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete
	 * 
	 * @throws DuplicateRecordException
	 */
	public static void testDelete() throws DuplicateRecordException {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 7;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Model test Delete Succes");
			CollegeBean deletedBean = model.findByPk(pk);
			if (deletedBean != null) {
				System.out.println("Tset delete Fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws DuplicateRecordException {
		try {
			CollegeBean bean = model.findByPk(2L);
			bean.setName("oitt");
			bean.setAddress("RRRRRR");
			model.update(bean);
			System.out.println("Test update success");
			CollegeBean updateBean = model.findByPk(2L);
			if (!"oitt".equals(updateBean.getName())) {
				System.out.println("Test update is fail");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("LNCT");
			// bean.setAddress("borawan");
			list = model.search(bean, 1, 13);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() throws ApplicationException {
		try {

			CollegeBean bean = new CollegeBean();
			long pk = 7L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("test find by  pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void findByName() {
		try {
			CollegeBean bean = model.findByName("svits");
			if (bean == null) {
				System.out.println("Test find by name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getCity());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test get list of collage
	 */
	public static void tsetList() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("listis fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method to call test
	 * 
	 * @throws DatabaseException
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static void main(String[] args) throws DatabaseException,
			ApplicationException, DuplicateRecordException {
		 //testNextPK();
		// testFindByPk();
		// findByName();
		// testUpdate();
		 testDelete();
		//testAdd();
		// testSearch();
		// tsetList();
	}
}
