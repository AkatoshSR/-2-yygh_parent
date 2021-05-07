package com.sqx.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.sqx.yygh.common.result.Result;
import com.sqx.yygh.common.utils.MD5;
import com.sqx.yygh.hosp.service.HospitalSetService;
import com.sqx.yygh.model.hosp.HospitalSet;
import com.sqx.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    // 注入service
    @Autowired
    private HospitalSetService hospitalSetService;

    // 1.查询医院设置表所有信息
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("/findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    // 2.删除医院设置
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("/{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 3.条件查询带分页
    @PostMapping("/findPageHospitalSet/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalQueryVo hospitalQueryVo) {
        // 创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalQueryVo.getHosname();
        String hoscode = hospitalQueryVo.getHoscode();
        if (!StringUtils.isNullOrEmpty(hosname)) {
            wrapper.like("hosname", hospitalQueryVo.getHosname());
        }
        if (!StringUtils.isNullOrEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalQueryVo.getHoscode());
        }

        // 调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);

        // 返回结果
        return Result.ok(pageHospitalSet);
    }

    // 4.添加医院设置
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        // 设置状态 1 使用，0 不能使用
        hospitalSet.setStatus(1);

        // 添加秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));

        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 5. 根据id获取医院设置
    @GetMapping("/getHospitalSet/{id}")
    public Result getHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);

        return Result.ok(hospitalSet);
    }

    // 6.修改医院设置6
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 7.批量删除医院设置
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    // 8.医院设置锁定和解锁
    @PutMapping("localHospitalSet/{id}/{status}")
    public Result localHospitalSet(@PathVariable Long id,
                                   @PathVariable Integer status) {

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);

        hospitalSetService.updateById(hospitalSet);

        return Result.ok();
    }


    // 9.发送签名秘钥
    @PutMapping("senKey/{id}/{status}")
    public Result localHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();

        // TODO 发送短信
        return Result.ok();
    }


}
