package com.jobRecomment.controller;


import com.jobRecomment.domain.bean.Result;
import com.jobRecomment.service.IFavoriteTitlesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收藏职位表 前端控制器
 * </p>
 *
 * @author HUANG
 * @since 2024-04-09
 */
@RestController
@Api(tags = "收藏接口")
@RequestMapping("/favorite")
public class FavoriteTitlesController {

    @Autowired
    private IFavoriteTitlesService favoriteTitlesService;

    @PostMapping("/addTitle")
    @ApiOperation("添加收藏")
    public Result addFavoriteTitle(Integer titleId){
//        System.out.println(StudentId + ":" + TitleId);
        if(titleId == null ){
            return Result.error("添加失败");
        }
        favoriteTitlesService.addNewFavorite(titleId);
        return Result.success();
    }

    @DeleteMapping("/deleteTitle")
    @ApiOperation("删除收藏")
    public Result deleteFavoriteTitle(Integer TitleId){
//        System.out.println(StudentId + ":" + TitleId);
        if(TitleId == null ){
            return Result.error("删除失败");
        }
        favoriteTitlesService.deleteFavorite(TitleId);
        return Result.success();
    }

    @DeleteMapping("/deleteAllTitle")
    @ApiOperation("删除所有收藏")
    public Result deleteAllFavoriteTitle(){
        favoriteTitlesService.deleteALlFavorite();
        return Result.success();
    }

    @ApiOperation("查询收藏")
    @PostMapping("/checkFavorite")
    public Result checkFavorite(Integer titleId){
        if(titleId == null){
            return Result.error("查询失败");
        }
        int row = favoriteTitlesService.checkFavorite(titleId);
        if(row != 0) return Result.success(true);
        else return Result.success(false);
    }

    @ApiOperation("获取收藏职位id")
    @GetMapping("/getFavoriteId")
    public Result getFavoriteId(){
        int[] ids = favoriteTitlesService.getAllTitleId();
        return Result.success(ids);
    }
}
