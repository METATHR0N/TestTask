package db.entity;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Employee implements Serializable{

	private static final long serialVersionUID = -7091303837943959576L;

	private int e_id;
	
	private int d_id;

	private String mail;
	
	private String name;
	
	private String phone;
	
	private Date birthday;

	public int getId() {
		return e_id;
	}

	public void setId(int id) {
		this.e_id = id;
	}
	
	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setBirthday(String date) {
		try {
			java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			this.birthday = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "[id=" + e_id + ", d_id=" + d_id + ", mail=" + mail + ", name=" + name + ", phone=" + phone
				+ ", birthday=" + birthday + "]";
	}

	
	
	
}
