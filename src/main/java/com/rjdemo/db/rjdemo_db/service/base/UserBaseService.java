/* 
* Generated by
* 
*      _____ _          __  __      _     _
*     / ____| |        / _|/ _|    | |   | |
*    | (___ | | ____ _| |_| |_ ___ | | __| | ___ _ __
*     \___ \| |/ / _` |  _|  _/ _ \| |/ _` |/ _ \ '__|
*     ____) |   < (_| | | | || (_) | | (_| |  __/ |
*    |_____/|_|\_\__,_|_| |_| \___/|_|\__,_|\___|_|
*
* The code generator that works in many programming languages
*
*			https://www.skaffolder.com
*
*
* You can generate the code from the command-line
*       https://npmjs.com/package/skaffolder-cli
*
*       npm install -g skaffodler-cli
*
*   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
*
* To remove this comment please upgrade your plan here: 
*      https://app.skaffolder.com/#!/upgrade
*
* Or get up to 70% discount sharing your unique link:
*       https://beta.skaffolder.com/#!/register?friend=5d37f6cb3581800b3cc97be3
*
* You will get 10% discount for each one of your friends
* 
*/
package com.rjdemo.db.rjdemo_db.service.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.rjdemo.db.rjdemo_db.service.RolesService;

import com.rjdemo.db.rjdemo_db.entity.User;
import com.rjdemo.db.rjdemo_db.service.UserService;

//IMPORT RELATIONS
import com.rjdemo.db.rjdemo_db.entity.User;

@Service
public class UserBaseService {

	private static NamedParameterJdbcTemplate jdbcTemplate;
	
		@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	private static RolesService rolesService = new RolesService();


    //CRUD METHODS
    
    	
    //CRUD - GET ONE
    	
	public User get(Long id) {
	    
		String sql = "select * from `User` where `_id` = :id";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    
	    return (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
	}


    	
    
    
    
    

    
    /*
     * CUSTOM SERVICES
     * 
     *	These services will be overwritten and implemented in UserService.java
     */
    
    
    /*
    
    YOU CAN COPY AND MODIFY THIS METHOD IN UserService.java
    
    Name: changePassword
    Description: Change password of user from admin
    Params: 
    */
	public Object changePassword () {
		
        return null;
        
	}
	
	
    	public User login(String username, String password) {
		String sql = "select * from `user` where `username` = :username AND  `password` = :password";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("username", username)
		.addValue("password", password);
	    
	    try {
		    User user = (User) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(User.class));
		    user = this.addRoles(user);
		    return user;
	    } catch(EmptyResultDataAccessException e){
	    	return null;
	    }
	}
		public boolean changePassword(Long id_user, Map<String, String> params) throws Exception {
		
		//AuthenticationService auth =(AuthenticationService) SecurityContextHolder.getContext().getAuthentication();

		String usernameAdmin = SecurityContextHolder.getContext().getAuthentication().getName();
		String passwordAdmin = params.get("passwordAdmin");
		String passwordNew= params.get("passwordNew");

		// Check admin user
		User admin = this.login(usernameAdmin, passwordAdmin);
		
		if(admin == null)
			throw new Exception("Admin password not valid");
		if (!admin.hasRole("ADMIN"))
			throw new Exception("User is not admin");
		
		return this.updatePassword(id_user, passwordNew);
	}
	

	// UTILS FUNCTION

	public boolean updatePassword(Long id_user, String password) {
		String sql = "UPDATE `user` SET `password` = :password WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id_user)
			.addValue("password", password);
		Integer rowNum = jdbcTemplate.update(sql, parameters);
		return rowNum != null && rowNum > 0;
	}
	
    public User addRoles(User user) {
	    ArrayList<String> roles = rolesService.getRolesAsStringArray(user.get_id());
	    user.setRoles(roles);
	    return user;
	}

	public void updateRoles(Long id_user, ArrayList<String> roles) {

		// Delete not in array
	    rolesService.deleteNotInArray(id_user, roles);
		
		// Get actual    		
	    List<String> actual = rolesService.getRolesAsStringArray(id_user);
	    
		// Insert new
		for (String role : roles) {
			if (actual.indexOf(role) == -1){
				rolesService.insert(id_user, role);
			}
		}
		
	}

}