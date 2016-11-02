package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.UserDTO;

/**
 * @author ognjen
 *
 */
public class LoginDAO {

//	static Map <String, UserDTO> usersMap = new HashMap<>();
//	static{
//		UserDTO userTemp = null;
//		
//		userTemp = new UserDTO();
//		userTemp.setFirstName("Administrator");
//		userTemp.setLastName("Admin");
//		userTemp.setSocialNo("1234");
//		userTemp.setEmail("ognjenkl@gmail.com");
//		userTemp.setPrivilege(1);
//		userTemp.setPicture("n/a");
//		userTemp.setUsername("a");
//		userTemp.setPassword("a");
//		userTemp.setActive(true);
//		usersMap.put(userTemp.getUsername(),userTemp);
//
//		userTemp = new UserDTO();
//		userTemp.setFirstName("Superuser");
//		userTemp.setLastName("Super");
//		userTemp.setSocialNo("2222");
//		userTemp.setEmail("ognjenkl@gmail.com");
//		userTemp.setPrivilege(2);
//		userTemp.setPicture("n/a");
//		userTemp.setUsername("s");
//		userTemp.setPassword("s");
//		userTemp.setActive(true);
//		usersMap.put(userTemp.getUsername(),userTemp);
//
//		userTemp = new UserDTO();
//		userTemp.setFirstName("User");
//		userTemp.setLastName("User");
//		userTemp.setSocialNo("3333");
//		userTemp.setEmail("ognjenkl@gmail.com");
//		userTemp.setPrivilege(3);
//		userTemp.setPicture("n/a");
//		userTemp.setUsername("u");
//		userTemp.setPassword("u");
//		userTemp.setActive(true);
//		usersMap.put(userTemp.getUsername(),userTemp);
//
//	}
	
	public static UserDTO login(String username, String password){
		//UserDTO user = usersMap.get(username);
		UserDTO user = getUser(username);
		if(user != null && user.getPassword().equals(password) && user.getUsername().equals(username) && user.isActive()){
			System.out.println("login user:  " + user.toString());
			return user;
		}
		else
			return null;

	}
	
	public static boolean register(UserDTO userRegister){
		userRegister.setPrivilege(3);
		//usersMap.put(userRegister.getUsername(), userRegister);
		boolean retVal = false;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ppst = null;
		String sql = "INSERT INTO users ( username, password, first_name, last_name, social_no, email, privilege, picture, active, editable) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			ppst = conn.prepareStatement(sql);
			ppst.setString(1, userRegister.getUsername());
			ppst.setString(2, userRegister.getPassword());
			ppst.setString(3, userRegister.getFirstName());
			ppst.setString(4, userRegister.getLastName());
			ppst.setString(5, userRegister.getSocialNo());
			ppst.setString(6, userRegister.getEmail());
			ppst.setInt(7, userRegister.getPrivilege());
			ppst.setString(8, userRegister.getPicture());
			ppst.setBoolean(9, userRegister.isActive());
			ppst.setBoolean(10, userRegister.isEditable());

			if(ppst.executeUpdate() > 0);
				retVal = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (ppst != null)
				try {
					ppst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
		
		return retVal;
	}
	
	public static UserDTO getUser(String username){
		UserDTO retUser = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			String sql = "SELECT * FROM zndfilm.users where username = ?;";
			ppst = conn.prepareStatement(sql);
			ppst.setString(1, username);
			rs = ppst.executeQuery();
		
			if(rs.next()){
				retUser = new UserDTO(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("social_no"),
									rs.getString("email"), rs.getString("picture"), rs.getString("username"), rs.getString("password"),
									rs.getInt("privilege"), rs.getBoolean("active"), rs.getBoolean("editable"));
				System.out.println("get user:  " + retUser.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (ppst != null)
				try {
					ppst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
		
		
		return retUser;
		
		//return usersMap.get(username);
	}
	
//	public static Map<String, UserDTO> getAllUsersMap(){
//		return usersMap;
//	}
	
	public static List<UserDTO> getAllUsersList(){
		//List<UserDTO> list = new ArrayList<UserDTO>(usersMap.values());
		
		Connection conn = null;
		ResultSet rs = null;
		List<UserDTO> users = new ArrayList<>();
		PreparedStatement ppst = null;
		
		try {
			conn = ConnectionPool.getConnectionPool().checkOut();
			String sql = "SELECT * FROM zndfilm.users;";
			ppst = conn.prepareStatement(sql);
			rs = ppst.executeQuery();

			while(rs.next())
				users.add( new UserDTO(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("social_no"),
									rs.getString("email"), rs.getString("picture"), rs.getString("username"), rs.getString("password"),
									rs.getInt("privilege"), rs.getBoolean("active"), rs.getBoolean("editable")));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ppst != null)
				try {
					ppst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ConnectionPool.getConnectionPool().checkIn(conn);
		}
	
		return users;
//		return list;
	}
	
	/**
	 * Universal update user method.  
	 * Privilege argument should be cast to String. 
	 * 
	 * @param firstName
	 * @param lastName
	 * @param socialNo
	 * @param email
	 * @param picture
	 * @param newUsername
	 * @param password
	 * @param privilege
	 * @return
	 */
	public static boolean updateUser(String username, String firstName, String lastName, String socialNo, String email, 
			String newUsername, String password, String privilege, String picture, String active, String editable) {
		

		boolean retVal = false;
		Connection conn = null;
		PreparedStatement ppst = null;
		String sql = "UPDATE users SET username=?, password=?, first_name=?, last_name=?, social_no=?, "
				+ "email=?, privilege=?, picture=?, active=?, editable=? WHERE username=?;";

		if(username != null && !username.equals("")){
			try{
				conn = ConnectionPool.getConnectionPool().checkOut();
				ppst = conn.prepareStatement(sql);
				ppst.setString(1, newUsername != null ? newUsername : null);
				ppst.setString(2, password != null ? password : null);
				ppst.setString(3, firstName != null ? firstName : null);
				ppst.setString(4, lastName != null ? lastName : null);
				ppst.setString(5, socialNo != null ? socialNo : null);
				ppst.setString(6, email != null ? email : null);
				ppst.setInt(7, privilege != null ? Integer.valueOf(privilege) : 30);
				ppst.setString(8, picture != null ? picture : null);
				ppst.setBoolean(9, active != null ? Boolean.valueOf(active) : null);
				ppst.setBoolean(10, editable != null ? Boolean.valueOf(editable) : null);
				
				ppst.setString(11, username);
				
				if(ppst.executeUpdate() > 0);
					retVal = true;
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				if (ppst != null)
					try {
						ppst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				ConnectionPool.getConnectionPool().checkIn(conn);
			}
			
		} else {
			retVal = false;
		}
	
		return retVal;
	}
	
	/**
	 * User update but without password and privilege active etc.
	 * 
	 * 
	 * @param firstName
	 * @param lastName
	 * @param socialNo
	 * @param email
	 * @param picture
	 * @return
	 */
	public static boolean updateUserWithoutPasswordAndPrivilege(String username, String firstName, String lastName, String socialNo, String email, 
			String picture) {
		

		boolean retVal = false;
		Connection conn = null;
		PreparedStatement ppst = null;
		String sql = "UPDATE users SET first_name=?, last_name=?, social_no=?, "
				+ "email=?, picture=? WHERE username=?;";

		if(username != null && !username.equals("")){
			try{
				conn = ConnectionPool.getConnectionPool().checkOut();
				ppst = conn.prepareStatement(sql);
				ppst.setString(1, firstName != null ? firstName : null);
				ppst.setString(2, lastName != null ? lastName : null);
				ppst.setString(3, socialNo != null ? socialNo : null);
				ppst.setString(4, email != null ? email : null);
				ppst.setString(5, picture != null ? picture : null);
				
				ppst.setString(6, username);
				
				if(ppst.executeUpdate() > 0);
					retVal = true;
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				if (ppst != null)
					try {
						ppst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				ConnectionPool.getConnectionPool().checkIn(conn);
			}
			
		} else {
			retVal = false;
		}
	
		return retVal;
	}
	
	
	/**
	 * Update user without privilege and active.
	 *  
	 * 
	 * @param firstName
	 * @param lastName
	 * @param socialNo
	 * @param email
	 * @param picture
	 * @param newUsername
	 * @param password
	 * @return
	 */
	public static boolean updateUserWithoutPrivilege(String username, String firstName, String lastName, String socialNo, String email, 
			String password, String picture) {
		

		boolean retVal = false;
		Connection conn = null;
		PreparedStatement ppst = null;
		String sql = "UPDATE users SET password=?, first_name=?, last_name=?, social_no=?, "
				+ "email=?, picture=? WHERE username=?;";

		if(username != null && !username.equals("")){
			try{
				conn = ConnectionPool.getConnectionPool().checkOut();
				ppst = conn.prepareStatement(sql);
				ppst.setString(1, password != null ? password : null);
				ppst.setString(2, firstName != null ? firstName : null);
				ppst.setString(3, lastName != null ? lastName : null);
				ppst.setString(4, socialNo != null ? socialNo : null);
				ppst.setString(5, email != null ? email : null);
				ppst.setString(6, picture != null ? picture : null);
				
				ppst.setString(7, username);
				
				if(ppst.executeUpdate() > 0);
					retVal = true;
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				if (ppst != null)
					try {
						ppst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				ConnectionPool.getConnectionPool().checkIn(conn);
			}
			
		} else {
			retVal = false;
		}
	
		return retVal;
	}
	
	public static boolean updateUser(UserDTO user){
		return updateUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), 
			user.getEmail(), user.getUsername(), user.getPassword(), new Integer(user.getPrivilege()).toString(), 
			user.getPicture(), new Boolean(user.isActive()).toString(), new Boolean(user.isEditable()).toString());	
	}
	
	public static boolean updateUserWithoutPasswordAndPrivilege(UserDTO user){
		return updateUserWithoutPasswordAndPrivilege(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), user.getEmail(), 
				user.getPicture());
	}
	
	public static boolean updateUserWithoutPrivilege(UserDTO user){
		return updateUserWithoutPrivilege(user.getUsername(), user.getFirstName(), user.getLastName(), user.getSocialNo(), user.getEmail(), 
				user.getPassword(), user.getPicture());
	}
	
	
}
