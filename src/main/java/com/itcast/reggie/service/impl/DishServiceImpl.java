package com.itcast.reggie.service.impl;

import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.entity.Dish;
import com.itcast.reggie.entity.DishFlavor;
import com.itcast.reggie.service.DishFlavorService;
import com.itcast.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggie.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDTO);
        //菜品ID
        Long dishId = dishDTO.getId();
        //菜品口味数据
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();
        //将菜品ID：dishId和菜品口味：dishFlavorList合并到一起
        List<DishFlavor> dishFlavorListNew = dishFlavorList.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
//        dishFlavorList.stream().map((item) -> {
//            item.setDishId(dishId);
//            return item;
//        }).collect(Collectors.toList());
        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(dishFlavorList);

    }
}
