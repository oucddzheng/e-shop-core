package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.SysCommodityClassificationMapper;


@Service("SysCommodityClassificationService")
public class SysCommodityClassificationService<T> extends BaseService {

	@Autowired
	private SysCommodityClassificationMapper<T> sysCommodityClassificationMapper;
	@Override
	public SysCommodityClassificationMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCommodityClassificationMapper;
	}

}
