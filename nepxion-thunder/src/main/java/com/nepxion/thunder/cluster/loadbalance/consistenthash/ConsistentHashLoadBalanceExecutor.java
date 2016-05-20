package com.nepxion.thunder.cluster.loadbalance.consistenthash;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;

import com.nepxion.thunder.cluster.loadbalance.AbstractLoadBalanceExecutor;
import com.nepxion.thunder.cluster.loadbalance.consistenthash.ketama.DefaultHashAlgorithm;
import com.nepxion.thunder.cluster.loadbalance.consistenthash.ketama.KetamaNodeLocator;
import com.nepxion.thunder.cluster.loadbalance.consistenthash.ketama.NodeLocator;
import com.nepxion.thunder.common.entity.ConnectionEntity;

public class ConsistentHashLoadBalanceExecutor extends AbstractLoadBalanceExecutor {
    private NodeLocator<ConnectionEntity> locator = new KetamaNodeLocator<ConnectionEntity>(new ArrayList<ConnectionEntity>(), DefaultHashAlgorithm.KETAMA_HASH);

    @Override
    protected ConnectionEntity loadBalance(String interfaze, List<ConnectionEntity> connectionEntityList) throws Exception {        
        return locator.getPrimary(UUID.randomUUID().toString());
    }

    @Override
    protected void cacheConnectionEntityList(List<ConnectionEntity> connectionEntityList) {
        super.cacheConnectionEntityList(connectionEntityList);
        
        List<ConnectionEntity> entityList = locator.getAll();
        if (CollectionUtils.isNotEmpty(connectionEntityList)) {
            if (!CollectionUtils.isEqualCollection(entityList, connectionEntityList)) {
                locator.updateLocator(new ArrayList<ConnectionEntity>(connectionEntityList));
            }
        } else {
            if (CollectionUtils.isNotEmpty(entityList)) {
                locator.updateLocator(new ArrayList<ConnectionEntity>());
            }
        }
    }
}