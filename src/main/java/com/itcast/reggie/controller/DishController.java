package com.itcast.reggie.controller;

import com.itcast.reggie.service.DishFlavorService;
import com.itcast.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜品Dish和菜品口味DishFlavor统一放到DishController里面
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
}
