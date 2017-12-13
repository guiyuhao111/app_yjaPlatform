package cn.net.gxht.app.yjdPlatform.file.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface FileDao {
    Integer insertUserAuthImgInfo(@Param(value = "userId") Integer userId,@Param(value="path") String path,@Param(value="typeId") Integer typeId,@Param(value = "zipImgPath") String zipImgPath);
}
