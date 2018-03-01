package situ.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import situ.core.mapper.BaseMapper;
import situ.core.service.BaseService;
import situ.system.mapper.CommodityInformationMapper;
@Service("sysCommodityInformationService")  //������д���ֵ�ʱ��һ��ʱ�������������ͬ��ֻ�ǽ�����ĸ�ĳ�Сд
public class SysCommodityInformationService<T> extends BaseService<T> {

	@Autowired
	private CommodityInformationMapper<T> sysCommodityInformationMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCommodityInformationMapper;
	}

}
