package gloryzyf.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁 使用AQS实现
 * @author YU
 *
 */
public class MutextLock implements Lock{

	private final Sync sync=new Sync();
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return sync.tryAcquireNanos(1,unit.toNanos(time));
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		sync.tryRelease(1);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return sync.newCondition();
	}
	public boolean hasQueuedThreads(){
		return sync.hasQueuedThreads();
	}
	static class Sync extends AbstractQueuedSynchronizer{
		//是否处于占用状态
		protected boolean isHeldExclusively(){
			return getState()==1;
		}
		//状态为0独占获取锁
		public boolean tryAcquire(int acquires){
			if(compareAndSetState(0,1)){
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		//释放锁,状态设置为0
		public boolean tryRelease(int release){
			if(getState()==0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
		Condition newCondition(){
			return new ConditionObject();
		}
	}
	
}
