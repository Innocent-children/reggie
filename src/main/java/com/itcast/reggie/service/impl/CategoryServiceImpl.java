package com.itcast.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcast.reggie.common.CustomException;
import com.itcast.reggie.common.R;
import com.itcast.reggie.entity.Category;
import com.itcast.reggie.entity.Dish;
import com.itcast.reggie.entity.Setmeal;
import com.itcast.reggie.mapper.CategoryMapper;
import com.itcast.reggie.service.CategoryService;
import com.itcast.reggie.service.DishService;
import com.itcast.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据ID删除分类，删除之前需要判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        //添加查询条件，根据分类ID进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);

        if (count > 0) {
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类关联了菜品，无法删除！");
        }
        //查询当前分类是否关联套餐，如果已经关联，抛出一个业务异常
        //添加查询条件，根据分类ID进行查询
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);

        if (count1 > 0) {
            //已经关联套餐，跑出业务异常
            throw new CustomException("当前分类关联了套餐，无法删除！");
        }
        //正常删除分类
        super.removeById(id);
    }
}
