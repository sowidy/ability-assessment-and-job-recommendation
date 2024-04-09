package com.jobRecomment.service.impl;

import com.jobRecomment.domain.bean.FavoriteTitles;
import com.jobRecomment.mapper.FavoriteTitlesMapper;
import com.jobRecomment.service.IFavoriteTitlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏职位表 服务实现类
 * </p>
 *
 * @author HUANG
 * @since 2024-04-09
 */
@Service
public class FavoriteTitlesServiceImpl extends ServiceImpl<FavoriteTitlesMapper, FavoriteTitles> implements IFavoriteTitlesService {

    @Override
    public void addNewFavorite(Integer titleId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer studentId = (Integer) map.get("id");
        FavoriteTitles favoriteTitles = new FavoriteTitles(studentId,titleId);
        save(favoriteTitles);
    }

    @Override
    public int checkFavorite(Integer titleId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer studentId = (Integer) map.get("id");
        return baseMapper.checkFavorite(studentId,titleId);
    }

    @Override
    public int[] getAllTitleId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer studentId = (Integer) map.get("id");
        List<Integer> titleIds = baseMapper.getAllTitleId(studentId);
        return titleIds.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public void deleteFavorite(Integer titleId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer studentId = (Integer) map.get("id");
        baseMapper.deleteFavorite(studentId,titleId);
    }

    @Override
    public void deleteALlFavorite() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer studentId = (Integer) map.get("id");
        baseMapper.deleteAllFavorite(studentId);
    }
}
