package com.raystec.proj4.test;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.model.TimeTableModel;
/**
 * Timetable Model Test classes
 * 
 * @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 * 
 */
public class TimeTableTest {
	public static TimeTableModel tModel = new TimeTableModel();

	public static void main(String args[]) throws DuplicateRecordException {
		//testUpdate();
	}

	/*public static void testUpdate() throws DuplicateRecordException {
		try {
			TimeTableBean bean = tModel.findByPK(4L);
			bean.setfId("Aman");
			bean.setCollegeId("N.I.T");
			;
			tModel.update(bean);
			System.out.println("Test update success");
			TimeTableBean updateBean = tModel.findByPK(4L);
			if (!"N.I.T".equals(updateBean.getCollegeId())) {
				System.out.println("Test update is fail");

			} else {
				System.out.println("Test update Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}


