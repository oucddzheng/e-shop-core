package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.SysBasicDataMapper;


@Service("sysBasicDataService")
public class SysBasicDataService<T> extends BaseService<T> {
	
	@Autowired
	private SysBasicDataMapper<T> sysBasicDataMapper;

	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysBasicDataMapper;
	}

}
