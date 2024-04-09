package com.jobRecomment.service;

import com.jobRecomment.domain.bean.FavoriteTitles;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收藏职位表 服务类
 * </p>
 *
 * @author HUANG
 * @since 2024-04-09
 */
public interface IFavoriteTitlesService extends IService<FavoriteTitles> {

    void addNewFavorite(Integer titleId);

    int checkFavorite(Integer titleId);

    int[] getAllTitleId();

    void deleteFavorite(Integer titleId);

    void deleteALlFavorite();
}
