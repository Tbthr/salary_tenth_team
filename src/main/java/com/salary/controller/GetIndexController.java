package com.salary.controller;

import com.salary.mapper.MenuMapper;
import com.salary.model.Menu;
import com.salary.model.User;
import com.salary.service.MenuService;
import com.salary.service.indexService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//558d92a54afe05a4
@RestController
public class GetIndexController {
    @Autowired
    private indexService indexService;

    @RequestMapping("/index")
    public JSONObject index(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String menuSet = indexService.getall(user.getId());
        String result ="{\"meta\": \n" +
                "        {\n" +
                "            \"msg\": \"获取菜单列表成功\",\n" +
                "            \"code\": 200\n" +
                "         },\n" +
                "    \"data\":";
        result+=menuSet+"}";
        return JSONObject.fromObject(result);
    }
}
