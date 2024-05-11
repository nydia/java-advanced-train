package com.nydia.demo.zk;

@Slf4j
public class DistributedLock implements Lock, Watcher{
     /**
      * zk客户端
      */
      private ZooKeeper zk;
     /**
     * 根目录
     */
     private final String root = "/locks";
     /**
     * 锁名称
     */
     private final String lockName;

     /**
     * 等待前一个锁
     */
     private String waitNode;

     /**
     * 当前锁
     */
     private String myZnode;
     /**
     * 计数器
     */
     private CountDownLatch latch;
     /**
     * 会话超时时间
     */
     private final int sessionTimeout = 30000;
     /**
     * 异常列表
     */
     private final List<Exception> exception = new ArrayList<>();

     /**
     * 创建分布式锁
     * @param config 服务器配置
     * @param lockName 竞争资源标志,lockName中不能包含单词lock
     */
     public DistributedLock(String config, String lockName){
         this.lockName = lockName;
         // 创建与服务器的连接
         try {
             zk = new ZooKeeper(config, sessionTimeout, this);
             Stat stat = zk.exists(root, false);
             if(stat == null){
                 // 创建根节点
                 zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
             }
         } catch (IOException | KeeperException | InterruptedException e) {
              exception.add(e);
         }
     }

    /**
     * zookeeper节点的监视器
     */
     @Override
     public void process(WatchedEvent event) {
         if(this.latch != null) {
             this.latch.countDown();
         }
     }

     @Override
     public void lock() {
         if(!exception.isEmpty()){
              throw new LockException(exception.get(0));
         }
         try {
             if(this.tryLock()){
                 log.info("Thread " + Thread.currentThread().getId() + " " +myZnode + " get lock true");
             } else{
                 //等待锁
                 waitForLock(waitNode, sessionTimeout);
             }
         } catch (KeeperException | InterruptedException e) {
             throw new LockException(e);
         }
     }

     @Override
     public boolean tryLock() {
         try {
             String splitStr = "_lock_";
             if(lockName.contains(splitStr)) {
                 throw new LockException("lockName can not contains \u000B");
         }
         //创建临时有序子节点
         myZnode = zk.create(root + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
         log.info(myZnode + " is created ");
         //取出所有子节点
         List<String> subNodes = zk.getChildren(root, false);
         //取出所有lockName的锁
         List<String> lockObjNodes = new ArrayList<String>();
         for (String node : subNodes) {
             String _node = node.split(splitStr)[0];
             if(_node.equals(lockName)){
                 lockObjNodes.add(node);
             }
         }
         Collections.sort(lockObjNodes);
         log.info("myZnode={} minZnode={}", myZnode, lockObjNodes.get(0));
         if(myZnode.equals(root+"/"+lockObjNodes.get(0))){
             //如果是最小的节点,则表示取得锁
             return true;
         }
         //如果不是最小的节点，找到比自己小1的节点
         String subMyZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
         waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, subMyZnode) - 1);
         } catch (KeeperException | InterruptedException e) {
             throw new LockException(e);
         }
         return false;
     }

     @Override
     public boolean tryLock(long time,@NonNull TimeUnit unit) {
         try {
             if(this.tryLock()){
                 return true;
             }
             return waitForLock(waitNode,time);
         } catch (Exception e) {
             log.error("tryLock exception:", e);
         }
         return false;
     }

     /**
     * @param lower 监视节点
     * @param waitTime 等待超时时间
     * @return 是否获得锁
     * @throws InterruptedException
     * @throws KeeperException
     */
      private boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
          Stat stat = zk.exists(root + "/" + lower,true);
          //判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
          if(stat != null){
             log.info("Thread " + Thread.currentThread().getId() + " waiting for " + root + "/" + lower);
             this.latch = new CountDownLatch(1);
             this.latch.await(waitTime, TimeUnit.MILLISECONDS);
             this.latch = null;
          }
          return true;
     }


      /**
      * 解锁方法
      * @throws InterruptedException 线程中断异常
      * @throws KeeperException ZooKeeper异常
      */
      @Override
      public void unlock() {
          try {
              log.info("unlock " + myZnode);
              zk.delete(myZnode,-1);
              myZnode = null;
              zk.close();
         } catch (InterruptedException | KeeperException e) {
             log.error("unlock exception:", e);
         }
     }

     @Override
     public void lockInterruptibly() throws InterruptedException {
          this.lock();
     }

     @Override
     public Condition newCondition() {
         return null;
     }

     /**
     * 自定义锁异常
     */
     public static class LockException extends RuntimeException {
         private static final long serialVersionUID = 1L;

         /**
         * @param e 异常
         */
         public LockException(String e){
             super(e);
         }

         /**
         * @param e 异常
         */
         public LockException(Exception e){
             super(e);
         }
     }
}