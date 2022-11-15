package com.itcast.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.entity.Dish;
import com.itcast.reggie.entity.DishFlavor;
import com.itcast.reggie.service.DishFlavorService;
import com.itcast.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggie.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        dishFlavorService.saveBatch(dishFlavorListNew);
    }

    /**
     * 根据ID查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDTO getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish, dishDTO);

        //查询菜品口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);
        dishDTO.setFlavors(flavors);
        return dishDTO;
    }

    /**
     * 更新菜品，同时更新对应的口味数据
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        //更新dish表基本信息
        this.updateById(dishDTO);
        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
        dishFlavorService.remove(lambdaQueryWrapper);
        //添加当前提交过来的口味数据---dish_flavor表insert操作
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDTO.getId());
            return item;
        }).collect((Collectors.toList()));
        dishFlavorService.saveBatch(flavors);
    }
}
