package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.SysReceiverAddressMapper;

@Service("sysReceiverAddressService")
public class SysReceiverAddressService<T> extends BaseService<T> {

	@Autowired
	private SysReceiverAddressMapper sysReceiverAddressMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysReceiverAddressMapper;
	}

}
