package cn.net.gxht.app.yjdPlatform.common.entity;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/14.
 */
public class PageUtil {
    /**
     * 分页封装
     */
    public static Map<String,Object> byPage(List<Map<String,Object>> list, Page page){
        List<Map<String,Object>> ObjectByPage=new ArrayList<Map<String, Object>>();
        System.out.println(list.size());
        page.setAmount(list.size());
        page.setAllPage();
        page.setStartIndex(page.getStartIndex());
        page.setEndIndex(page.getEndIndex());
        System.out.println(page);
        int index=0;
        for(Map<String,Object> map:list){
            if(index>=page.getStartIndex()&&index<=page.getEndIndex()){
                ObjectByPage.add(map);
            }
            index++;
        }
        Map<String,Object> map=new HashedMap();
        map.put("pageObject",page);
        map.put("pageInfo",ObjectByPage);
        return map;
    }
    public static Map<String,Object> byPage(Object [] objects,Page page){
        List<Object> ObjectByPage=new ArrayList<Object>();
        System.out.println(objects.length);
        page.setAmount(objects.length);
        page.setAllPage();
        page.setStartIndex(page.getStartIndex());
        page.setEndIndex(page.getEndIndex());
        System.out.println(page);
        int index=0;
        for(Object object:objects){
            if(index>=page.getStartIndex()&&index<=page.getEndIndex()){
                ObjectByPage.add(object);
            }
            index++;
        }
        Map<String,Object> map=new HashedMap();
        map.put("pageObject",page);
        map.put("pageInfo",ObjectByPage);
        return map;
    }
}
