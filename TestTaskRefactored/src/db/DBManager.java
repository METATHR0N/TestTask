package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import db.entity.Department;
import db.entity.Employee;

public class DBManager {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/aimprosoft?serverTimezone=UTC";
	
	private static final String USERNAME = "Metathron";
	
	private static final String PASSWORD = "3555serg";

	private static final String SQL_FIND_ALL_DEPARTMENTS = "SELECT * FROM departments;";

	private static final String SQL_FIND_DEPARTMENT = "SELECT * FROM Departments WHERE name=?;";

	private static final String SQL_FIND_BY_ID = "SELECT * FROM Departments WHERE d_id=?;";

	private static final String SQL_INSERT_DEPARTMENT = "INSERT INTO Departments (name) VALUES (?);";

	private static final String SQL_UPDATE_DEPARTMENT = "UPDATE Departments SET name=? WHERE d_id=?;";

	private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM Departments where d_id=?;";

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private static final String SQL_FIND_ALL_EMPLOYEES = "SELECT * FROM employees;";

	private static final String SQL_FIND_EMPLOYEE = "SELECT * FROM employees where e_id=?;";

	private static final String SQL_FIND_EMPLOYEE_BY_MAIL = "SELECT * FROM employees where mail=?;";

	private static final String SQL_FIND_EMPLOYEES_FROM_DEPARTMENT = "SELECT * FROM employees where Departments_d_id=?;";

	private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employees "
			+ "(`Departments_d_id`, `mail`, `name`, `phone`, `birthday`) VALUES (?, ?, ?, ?, ?);";

	private static final String SQL_UPDATE_EMPLOYEE = "UPDATE employees SET "
			+ "Departments_d_id=?, mail=?, name=?, phone=?, birthday=? WHERE e_id=?;";

	private static final String SQL_DELETE_EMPLOYEE = "DELETE FROM employees where e_id=?;";

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		return con;
	}
	
	private static DBManager instance;

	private DBManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public List<Department> findAllDepartments() throws SQLException {

		List<Department> departments = new ArrayList<>();

		Connection con = null;
		Statement stmt;
		ResultSet rs;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_DEPARTMENTS);

			while (rs.next()) {
				departments.add(extractDepartment(rs));
			}
		} finally {
			//con.close();
		}

		return departments;
	}

	public boolean DepartmentExists(String Name) throws SQLException {

		boolean result = false;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_DEPARTMENT);
			pstmt.setString(1, Name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} finally {
			con.close();
		}

		return result;
	}

	public Department findDepartment(int id) throws SQLException {

		Department department = null;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				department = extractDepartment(rs);
			}
		} finally {
			con.close();
		}

		return department;
	}

	public boolean createDepartment(Department department) throws SQLException {
		boolean res = false;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_DEPARTMENT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, department.getName());

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				res = true;
			}

		} finally {
			con.close();
		}

		return res;
	}

	public boolean updateDepartment(Department department) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_DEPARTMENT);
			int k = 1;
			pstmt.setString(k++, department.getName());
			pstmt.setInt(k++, department.getId());

			return pstmt.executeUpdate() > 0;
		} finally {
			con.close();
		}
	}

	public boolean deleteDepartment(int Id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_DEPARTMENT);
			pstmt.setInt(1, Id);

			return pstmt.executeUpdate() > 0;
		} finally {
			con.close();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public List<Employee> findAllEmployees() throws SQLException {

		List<Employee> employees = new ArrayList<>();

		Connection con = null;
		Statement stmt;
		ResultSet rs;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_EMPLOYEES);

			while (rs.next()) {
				employees.add(extractEmployee(rs));
			}
		} finally {
			con.close();
		}

		return employees;
	}

	public List<Employee> findEmployeesFromDepartment(int id) throws SQLException {

		List<Employee> employees = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_EMPLOYEES_FROM_DEPARTMENT);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				employees.add(extractEmployee(rs));
			}
		} finally {
			con.close();
		}

		return employees;
	}

	public boolean EmployeeExists(String mail) throws SQLException {

		boolean result = false;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_EMPLOYEE_BY_MAIL);
			pstmt.setString(1, mail);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			}
		} finally {
			con.close();
		}

		return result;
	}

	public Employee findEmployee(int id) throws SQLException {
		Employee employee = null;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_EMPLOYEE);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				employee = extractEmployee(rs);
			}
		} finally {
			con.close();
		}

		return employee;
	}

	public boolean createEmployee(Employee employee) throws SQLException {
		boolean res = false;

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			Calendar cal = new GregorianCalendar();
			pstmt.setInt(k++, employee.getD_id());
			pstmt.setString(k++, employee.getMail());
			pstmt.setString(k++, employee.getName());
			pstmt.setString(k++, employee.getPhone());
			pstmt.setDate(k++, employee.getBirthday(), cal);

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					employee.setId(id);
				}
				res = true;
			}

		} finally {
			con.close();
		}

		return res;
	}

	public boolean updateEmployee(Employee employee) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_EMPLOYEE);
			int k = 1;
			Calendar cal = new GregorianCalendar();
			pstmt.setInt(k++, employee.getD_id());
			pstmt.setString(k++, employee.getMail());
			pstmt.setString(k++, employee.getName());
			pstmt.setString(k++, employee.getPhone());
			pstmt.setDate(k++, employee.getBirthday(), cal);
			pstmt.setInt(k++, employee.getId());

			return pstmt.executeUpdate() > 0;
		} finally {
			con.close();
		}
	}

	public boolean deleteEmployee(int Id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_EMPLOYEE);
			pstmt.setInt(1, Id);

			return pstmt.executeUpdate() > 0;
		} finally {
			con.close();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private Department extractDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("d_id")); // <-- Constants.FIELD_USERS_ID
		department.setName(rs.getString("Name"));
		return department;
	}

	private Employee extractEmployee(ResultSet rs) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getInt("e_id"));
		employee.setD_id(rs.getInt("Departments_d_id"));
		employee.setMail(rs.getString("mail"));
		employee.setName(rs.getString("name"));
		employee.setPhone(rs.getString("phone"));
		employee.setBirthday(rs.getDate("birthday"));
		return employee;
	}

}
