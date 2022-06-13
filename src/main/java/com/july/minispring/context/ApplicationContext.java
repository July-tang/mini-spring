package com.july.minispring.context;

import com.july.minispring.beans.factory.HierarchicalBeanFactory;
import com.july.minispring.beans.factory.ListableBeanFactory;
import com.july.minispring.core.io.ResourceLoader;

/**
 * 应用上下文
 *
 *
 * @author july
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
