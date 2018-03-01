package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.SysShoppingCarMapper;
@Service("sysShoppingCarService")
public class SysShoppingCarService<T> extends BaseService<T> {

	@Autowired
	private SysShoppingCarMapper<T> sysShoppingCarMapper;
	@Override
	public BaseMapper<T> getMapper() {
	
		// TODO Auto-generated method stub
		return sysShoppingCarMapper;
	}

}
