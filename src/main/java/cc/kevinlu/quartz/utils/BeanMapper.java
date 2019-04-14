package cc.kevinlu.quartz.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;

public class BeanMapper {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 转换对象的类型
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, destinationClass);
    }

    /**
     * 将对象A的值拷贝到对象B中
     */
    public static <T> T copy(Object source, T destinationObject) {
        dozer.map(source, destinationObject);
        return destinationObject;
    }

    /**
     * 转换Collection中对象的类型
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return destinationList;
        }
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

}
