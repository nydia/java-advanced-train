package com.nydia.demo.redission;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/13 22:46
 * @Description: RedissionDemo
 */
public class RedissionDemo {

    @Override
    public void initCacheTask() {
        boolean lockFlag;
        RLock rLock = null;
        try {
            rLock = redissonClient.getLock(BizConstant.BLACK_LIST_CACHE_TASK_LOCK_NAME);
            // 等待时间为如果没有通过redisKey获取到锁，则等待1s,1s后还没获取到锁，则tryLock返回false，表明有人正在使用.设置了你占有他的时间为10s
            //lockFlag = rLock.tryLock(1000, 10000, TimeUnit.MILLISECONDS);
            lockFlag = rLock.tryLock(1000, TimeUnit.MILLISECONDS);
            if (lockFlag) {
                //LogUtils.logAppReq("获取到分布式锁");
                initBlackListCache();
            } else {
                // 没有获取到锁，进行一些操作
                LogUtils.logAppResp("获取分布式锁失败");
            }
        } catch (Exception e) {
            LogUtils.logError("获取分布式锁失败");
            throw new ServiceException(Messages.getMessage("risk.sys.error"));
        } finally {
            rLock.unlock();
        }
    }

}
