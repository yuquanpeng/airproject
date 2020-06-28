package com.example.demo.mapper;

import com.example.demo.entity.Equipment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EquipMapper {
    List<Equipment> get_equipment_info();

    boolean update_equipment_info(Equipment equipment);

    boolean add_equipment_info(Equipment equipment);

    boolean del_equipment_info(String ep_id);

    Equipment get_one_info(String ep_id);

}
