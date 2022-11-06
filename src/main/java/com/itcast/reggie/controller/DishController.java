package com.itcast.reggie.controller;

import com.itcast.reggie.common.R;
import com.itcast.reggie.dto.DishDTO;
import com.itcast.reggie.service.DishFlavorService;
import com.itcast.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
