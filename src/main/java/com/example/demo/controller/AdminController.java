package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.tools.MyJsonResult;
import com.example.demo.entity.*;
import com.example.demo.service.MaterialService;
import com.example.demo.service.OperatorService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserInfoService;
import com.example.demo.tools.OrderClassEnum;
import com.example.demo.tools.OrderStateEnum;
import com.example.demo.tools.Tool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/bg/admin")
public class AdminController {
    @Autowired
    private Tool tools;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private EquipService equipService;

    Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping(value = "/getallorderinfo")
    @ResponseBody
    public Map<String,Object> selectAllOrder(@RequestParam(required = false,defaultValue = "1") int page,
                                       @RequestParam(required = false,defaultValue = "10") int limit)
    {
        PageHelper.startPage(page, limit);
        List<Order> orders = orderService.selectAll();

        /*组装响应数据 便于前端显示*/
        List<Operator> op_list = operatorService.all_op_info();
        List<User_info> userInfos = userInfoService.get_all_user_info();

        // 获得Op_id----------->Op_name
        HashMap<String, String> op_map = new HashMap<String,String>();
        for (Operator operator : op_list) {
            op_map.put(operator.getOp_id(), operator.getOp_name());
        }
        HashMap<String, String> user_map = new HashMap<String,String>();
        for (User_info userInfo : userInfos) {
            user_map.put(userInfo.getUser_id(), userInfo.getUser_name());
        }
        for(int i = 0; i < orders.size(); i++){
            Order order = orders.get(i);
            // 组装操作员信息
            String opId = order.getOp_id();
            order.setOp_id(op_map.get(opId));//更换为操作员的名字
            // 组装订单类别信息
            String orderClass = order.getOrder_class();
            order.setOrder_class(OrderClassEnum.getName(orderClass));
            // 组装订单状态信息
            String orderState = order.getOrder_state();
            order.setOrder_state(OrderStateEnum.getName(Integer.parseInt(orderState)));

            String userId = order.getUser_id();
            order.setUser_id(user_map.get(userId));
        }


        PageInfo pageInfo = new PageInfo(orders);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        // return map;


        if (!orders.isEmpty()){
            map.put("msg","操作成功");
            return map;
        }
        map.put("msg","操作失败");
        return map;
    }
    @GetMapping(value = "/getalloperatorinfo")
    @ResponseBody
    public Map<String,Object> selectAllOrder(){
        List<Operator> op_list = operatorService.all_op_info();

        for(int i=0;i<op_list.size();i++){
            Operator operator =op_list.get(i);
            operator.setOp_score(OrderStateEnum.getName(operator.getOp_state()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",op_list.size());
        map.put("data",op_list);
        map.put("msg","ok");
        return map;

    }

    @GetMapping(value = "/getallmaterialinfo")
    @ResponseBody
    public Map<String,Object> selectAllMaterial(@RequestParam(required = false,defaultValue = "1") int page,
                                                @RequestParam(required = false,defaultValue = "10") int limit){
        PageHelper.startPage(page, limit);
        List<Material> materialList = materialService.get_material_info();

        PageInfo pageInfo = new PageInfo(materialList);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        if (!materialList.isEmpty()){
            map.put("msg","操作成功");
            return map;
        }
        map.put("msg","操作失败");
        return map;

    }

    @GetMapping(value = "/getallmaterialbyparams")
    @ResponseBody
    public Map<String,Object> selectAllMaterial(HttpServletRequest request){
        JSONObject searchParams = JSONObject.parseObject(request.getParameter("searchParams"));


        System.out.println(searchParams.toString());
        String material_id = searchParams.getString("material_id");
        String ma_name = searchParams.getString("ma_name");
        System.out.println("material_id:"+material_id);
        System.out.println("ma_name:"+ma_name);

        PageHelper.startPage(1, 10);
        List<Material> materialList = materialService.get_material_by_params(material_id, ma_name);


        PageInfo pageInfo = new PageInfo(materialList);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        if (!materialList.isEmpty()){
            map.put("msg","操作成功");
            return map;
        }
        map.put("msg","操作失败");
        return map;

    }

    @GetMapping(value = "/getallequipmentinfo")
    @ResponseBody
    public Map<String,Object> selectAllEquipment(@RequestParam(required = false,defaultValue = "1") int page,
                                                 @RequestParam(required = false,defaultValue = "10") int limit){
        PageHelper.startPage(page, limit);


        List<Equipment> equipmentList = equipService.get_equipment_info();

        PageInfo pageInfo = new PageInfo(equipmentList);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        if (!equipmentList.isEmpty()){
            map.put("msg","操作成功");
            return map;
        }
        map.put("msg","操作失败");
        return map;

    }

    @GetMapping(value = "/getallequipmentbyparams")
    @ResponseBody
    public Map<String,Object> selectAllEquipment(HttpServletRequest request){
        JSONObject searchParams = JSONObject.parseObject(request.getParameter("searchParams"));


        String equipment_id = searchParams.getString("equipment_id");
        String equipment_name = searchParams.getString("equipment_name");

        PageHelper.startPage(1, 10);
        List<Equipment> equipmentList = equipService.get_equipment_by_params(equipment_id, equipment_name);


        PageInfo pageInfo = new PageInfo(equipmentList);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        if (!equipmentList.isEmpty()){
            map.put("msg","操作成功");
            return map;
        }
        map.put("msg","操作失败");
        return map;

    }
}
