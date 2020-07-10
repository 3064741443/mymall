package com.james.mall.dao;

import com.james.mall.dto.PmsProductResult;

public interface PmsProductDao {
    PmsProductResult getUpdateInfo(String id);
}
