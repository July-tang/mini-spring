package com.july.minispring.context;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.Aware;

/**
 * 实现该接口，能感知所属ApplicationContext
 *
 * @author july
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
