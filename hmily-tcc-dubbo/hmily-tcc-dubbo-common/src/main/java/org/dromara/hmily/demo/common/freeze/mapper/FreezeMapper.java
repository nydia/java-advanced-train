/*
 * Copyright 2017-2021 Dromara.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.demo.common.freeze.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.hmily.demo.common.freeze.dto.FreezeDTO;
import org.dromara.hmily.demo.common.freeze.entity.FreezeDO;

/**
 * The interface Inventory mapper.
 *
 * @author xiaoyu
 */
public interface FreezeMapper {
    
    /**
     * Decrease int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId} and total_inventory > 0  ")
    int update(FreezeDTO freezeDTO);
    
    /**
     * Confirm int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set " +
            " lock_inventory = lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory > 0 ")
    int confirm(FreezeDTO freezeDTO);
    
    /**
     * Cancel int.
     *
     * @param freezeDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory > 0 ")
    int cancel(FreezeDTO freezeDTO);
    
    /**
     * Find by product id inventory do.
     *
     * @param productId the product id
     * @return the inventory do
     */
    @Select("select id,product_id,total_inventory ,lock_inventory from inventory where product_id =#{productId}")
    FreezeDO findByProductId(String productId);
}
