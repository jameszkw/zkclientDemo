/**
 * 
 */
package com.james.zookeeper.zkclient.loadbalanceother.algorithm;

/**
 * @ClassName: LoadBlance
 * @Description: TODO(describe what to do this class)
 * @author James.zhang
 * @date 2015年8月13日 下午6:45:23
 *
 */
public interface LoadBlance {
    String select(String zkServer);
}
