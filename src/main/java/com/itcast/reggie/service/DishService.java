package com.itcast.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作dish表以及dish_flavor表
    public void saveWithFlavor(DishDTO dishDTO);

    //根据ID查询菜品信息和对应的口味信息
    public DishDTO getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新口味信息
    public void updateWithFlavor(DishDTO dishDTO);
}
