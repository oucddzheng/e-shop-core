package situ.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.service.BaseService;
import situ.system.mapper.SysUserMapper;




@Service("sysUserService")
public class SysUserService<T> extends BaseService<T> {

    @Autowired
    private SysUserMapper<T> SysUserMapper;
    public  SysUserMapper<T> getMapper() {
        return SysUserMapper;
    }

 /*   public T queryLogin(T model) {
        List<T> result = mapper.selectModel(model);
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }*/

}
