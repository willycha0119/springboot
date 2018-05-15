package wala.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/hello")
	public @ResponseBody String index() {
		String sql = "select * from \"RO_ITEMS\" where \"ITEMNAME\" like ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] {"%森林%"});
        
		//return obj.selectQuery("select * from \"RO_ITEMS\" limit 2").toString();
		return list.toString();
	}
	
}
