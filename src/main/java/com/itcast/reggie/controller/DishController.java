package com.itcast.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itcast.reggie.common.R;
import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.entity.Category;
import com.itcast.reggie.entity.Dish;
import com.itcast.reggie.service.CategoryService;
import com.itcast.reggie.service.DishFlavorService;
import com.itcast.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 菜品Dish和菜品口味DishFlavor统一放到DishController里面
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDTO dishDTO) {
        log.info("dishDTO:{}", dishDTO.toString());
        dishService.saveWithFlavor(dishDTO);
        return R.success("新增菜品成功！");
    }

    /**
     * 菜品信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDTO> dishDTOPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo, lambdaQueryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDTOPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDTO> list = records.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO);
            Long categoryId = item.getCategoryId();//分类ID
            Category category = categoryService.getById(categoryId);//根据ID查询分类对象
            String categoryName = category.getName();
            dishDTO.setCategoryName(categoryName);
            return dishDTO;
        }).collect(Collectors.toList());

        dishDTOPage.setRecords(list);
        return R.success(dishDTOPage);
    }

    /**
     * 根据ID查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDTO> get(@PathVariable Long id) {
        DishDTO dishDTO = dishService.getByIdWithFlavor(id);
        return R.success(dishDTO);
    }
    /**
     * 更新菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDTO dishDTO) {
        log.info("dishDTO:{}", dishDTO.toString());
        dishService.updateWithFlavor(dishDTO);
        return R.success("新增菜品成功！");
    }
}
