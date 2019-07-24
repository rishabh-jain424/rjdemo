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

import com.rjdemo.db.rjdemo_db.entity.Ssnotes;
import com.rjdemo.db.rjdemo_db.service.SsnotesService;

//IMPORT RELATIONS
import com.rjdemo.db.rjdemo_db.entity.User;

@Service
public class SsnotesBaseService {

	private static NamedParameterJdbcTemplate jdbcTemplate;
	
		@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	


    //CRUD METHODS
    
    //CRUD - CREATE
    	
	public Ssnotes insert(Ssnotes obj) {
		Long id = jdbcTemplate.queryForObject("select max(_id) from `ssnotes`", new MapSqlParameterSource(), Long.class);
		obj.set_id(id == null ? 1 : id + 1);
		String sql = "INSERT INTO `ssnotes` (`_id`, `Author`,`CoverImage`,`DatePublished`,`Description`,`title`,`login`) VALUES (:id,:Author,:CoverImage,:DatePublished,:Description,:title, :login )";
			SqlParameterSource parameters = new MapSqlParameterSource()
		    .addValue("id", obj.get_id())
			.addValue("Author", obj.getAuthor())
			.addValue("CoverImage", obj.getCoverImage())
			.addValue("DatePublished", obj.getDatePublished())
			.addValue("Description", obj.getDescription())
			.addValue("title", obj.getTitle())
			.addValue("login", obj.getLogin());
		
		jdbcTemplate.update(sql, parameters);
		return obj;
	}
	
    	
    //CRUD - REMOVE
    
	public void delete(Long id) {
		String sql = "DELETE FROM `ssnotes` WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		
		jdbcTemplate.update(sql, parameters);
	}

    	
    //CRUD - GET ONE
    	
	public Ssnotes get(Long id) {
	    
		String sql = "select * from `ssnotes` where `_id` = :id";
		
	    SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
	    
	    return (Ssnotes) jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper(Ssnotes.class));
	}


    	
        	
    //CRUD - GET LIST
    	
	public List<Ssnotes> getList() {
	    
		String sql = "select * from `ssnotes`";
		
	    SqlParameterSource parameters = new MapSqlParameterSource();
	    return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper(Ssnotes.class));
	    
	    
	}

    	
    //CRUD - EDIT
    	
	public Ssnotes update(Ssnotes obj, Long id) {

		String sql = "UPDATE `ssnotes` SET `Author` = :Author,`CoverImage` = :CoverImage,`DatePublished` = :DatePublished,`Description` = :Description,`title` = :title , `login` = :login  WHERE `_id`=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("Author", obj.getAuthor())
			.addValue("CoverImage", obj.getCoverImage())
			.addValue("DatePublished", obj.getDatePublished())
			.addValue("Description", obj.getDescription())
			.addValue("title", obj.getTitle())
			.addValue("login", obj.getLogin());
		jdbcTemplate.update(sql, parameters);
	    return obj;
	}
	
    	
    
    
    
    

    
    /*
     * CUSTOM SERVICES
     * 
     *	These services will be overwritten and implemented in ssnotesService.java
     */
    

}