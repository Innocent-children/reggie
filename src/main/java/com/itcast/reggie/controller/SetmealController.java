package com.itcast.reggie.controller;

import com.itcast.reggie.common.R;
import com.itcast.reggie.dto.SetmealDTO;
import com.itcast.reggie.service.SetmealDishService;
import com.itcast.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("套餐信息：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return R.success("新增套餐成功！");
    }
}
