package com.greate.community.controller;

import com.greate.community.dao.DiscussPostMapper;
import com.greate.community.dao.UserMapper;
import com.greate.community.entity.DiscussPost;
import com.greate.community.entity.User;
import com.greate.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 网站数据
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper postMapper;

    /**
     * 进入统计界面
     *
     * @return
     */
    @RequestMapping(value = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataPage() {
        return "/site/admin/data";
    }

    /**
     * 统计网站 uv
     *
     * @param start
     * @param end
     * @return
     */
    @PostMapping("/data/uv")
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                        Model model) {
        long uv = dataService.calculateUV(start, end);
        model.addAttribute("uvResult", uv);
        model.addAttribute("uvStartDate", start);
        model.addAttribute("uvEndDate", end);
        return "forward:/data";
    }

    /**
     * 统计网站 DAU
     *
     * @param start
     * @param end
     * @return
     */
    @PostMapping("/data/dau")
    public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                         Model model) {
        long dau = dataService.calculateDAU(start, end);
        model.addAttribute("dauResult", dau);
        model.addAttribute("dauStartDate", start);
        model.addAttribute("dauEndDate", end);
        return "forward:/data";
    }


    @RequestMapping(value = "/yonghusys", method = {RequestMethod.GET, RequestMethod.POST})
    public String yonghusys(Model model) {
        List<User> list = userMapper.getUserList();
        model.addAttribute("list", list);
        return "/site/admin/yonghusys";
    }


    @RequestMapping(value = "/deleteUser", method = {RequestMethod.GET})
    @ResponseBody
    public String deleteUser(String id) {
        userMapper.deleteUser(id);
        return id;
    }

    @RequestMapping(value = "/tiezisys", method = {RequestMethod.GET, RequestMethod.POST})
    public String tiezisys(Model model) {
        List<DiscussPost> list = postMapper.getList();
        model.addAttribute("list", list);
        return "/site/admin/tiezisys";
    }
    @RequestMapping(value = "/deleteTiezi", method = {RequestMethod.GET})
    @ResponseBody
    public String deleteTiezi(String id) {
        postMapper.deleteTiezi(id);
        return id;
    }


}
