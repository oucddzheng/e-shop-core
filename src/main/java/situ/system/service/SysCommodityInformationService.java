package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.CommodityInformationMapper;
@Service("sysCommodityInformationService")  //在这里写名字的时候，一般时与下面的类名相同，只是将首字母改成小写
public class SysCommodityInformationService<T> extends BaseService<T> {

	@Autowired
	private CommodityInformationMapper<T> sysCommodityInformationMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCommodityInformationMapper;
	}

}
