package io.seata.sample.service;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.sample.action.TccActionOne;
import io.seata.sample.entity.Storage;
import io.seata.sample.repository.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019-04-04
 */
@Service
public class StorageService {

    @Autowired
    private StorageDAO storageDAO;

    @Autowired
    private TccActionOne tccActionOne;
    
    @Transactional
    public void deduct(String commodityCode, int count) {
    	System.out.println(RootContext.getXID());
    	
        Storage storage = storageDAO.findByCommodityCode(commodityCode);
        storage.setCount(storage.getCount() - count);

        storageDAO.save(storage);
        
        tccActionOne.prepare(null, 1);
    }
}
