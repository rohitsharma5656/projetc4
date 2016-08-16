package com.raystec.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.proj4.bean.RoleBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.RoleModel;
/**
 * Role Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class RoleTest {
	public static RoleModel model = new RoleModel();

	public static void main(String args[]) throws ParseException,
			DatabaseException {
		// testAdd();
		// testNextPk();
		// testFindByName();
		// update();
		 testDelete();
		// testSearch();
		//list();
	}

	public static void testAdd() throws ParseException {

		try {

			RoleBean bean = new RoleBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setName("Faculty");
			bean.setDescription("amannnnnn");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedBy("admin");
			bean.setCreatedBy("abirr");
			long pk = model.add(bean);
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testNextPk() throws DatabaseException {
		System.out.println(model.nextPK());
	}

	public static void testFindByName() {
		try {
			RoleBean bean = new RoleBean();
			bean = model.findByName("admin");
			if (bean == null) 
			{
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 7;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void update() throws DatabaseException {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(2L);
			bean.setName("12abc021");
			bean.setDescription("Ejjjjj");
			model.update(bean);
			System.err.println("kk");
			RoleBean updatedbean = model.findByPK(2L);
			System.out.println("dddf");
			if (!"12abc021".equals(updatedbean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws DatabaseException {
		try {
			RoleBean bean = new RoleBean();
			long pk = 5L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Data is deleted");
			RoleBean deleteBean = model.findByPK(pk);
			if (deleteBean != null) {
				System.out.println("Test delete is fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get search
	 */
	public static void testSearch() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("admin");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void list() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.list(1, 4);
			if (list.size() < 0) 
			{
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
