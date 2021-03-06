package com.example.demo.service;

import com.example.demo.entity.Operator;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OperatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OperatorService {
    @Autowired
    OperatorMapper operatorMapper;

    public Operator wx_login_operator(String phone,String pwd){
        return operatorMapper.wx_login_operator(phone,pwd);
    }

    public boolean wx_bind(String id,String openid){
        return operatorMapper.wx_bind(id,openid);
    }

    public boolean bg_add(Operator operator)
    {
        return operatorMapper.bg_add_operator(operator);
    }

    public List<Order> wx_show_orders(String op_id){
        return operatorMapper.wx_show_orders(op_id);
    }

    public List<Operator> all_op_info(){
        return operatorMapper.all_op_info();
    }

    public Operator selectByOpID(String op_id){
        return operatorMapper.selectByOpID(op_id);
    }

    public boolean updateOp(Operator operator){
        return operatorMapper.updateOp(operator);
    }

    public List<Order> show_orders_with_size(int startPosition, int size, String op_id,int order_state) {
        return operatorMapper.show_orders_with_size(startPosition, size, op_id, order_state);
    }
}
