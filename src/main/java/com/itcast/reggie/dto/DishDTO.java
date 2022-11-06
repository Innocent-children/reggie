package com.itcast.reggie.dto;

import com.itcast.reggie.entity.Dish;
import com.itcast.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据传输对象，接收web传来的参数
 */
@Data
public class DishDTO extends Dish {
    /**
     * 接收页面传来的flavors对象
     * 数组中的每一个元素都是一个key-value对，value有多个值
     */
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
