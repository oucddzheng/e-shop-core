package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.SysCommodityOrderMapper;
@Service
public class SysCommodityOrderService<T> extends BaseService<T> {

	@Autowired
	private SysCommodityOrderMapper sysCommodityOrderMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCommodityOrderMapper;
	}

}
