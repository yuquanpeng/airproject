package com.example.demo.mapper;

import com.example.demo.entity.Operator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OperatorMapper {
    Operator wx_login_operator(String phone,String pwd);
    boolean wx_bind(String id,String openid);
    boolean bg_add(Operator operator);
    List<Operator> wx_show_orders(String op_id);
}
