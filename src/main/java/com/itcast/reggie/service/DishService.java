package com.itcast.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作dish表以及dish_flavor表
    public void saveWithFlavor(DishDTO dishDTO);
}
