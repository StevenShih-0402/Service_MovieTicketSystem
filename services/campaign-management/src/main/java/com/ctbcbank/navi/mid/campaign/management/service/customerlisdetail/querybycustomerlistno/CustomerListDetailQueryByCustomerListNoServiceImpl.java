package com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDetailDao;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.query.CampaignFormQueryServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerListDetailQueryByCustomerListNoServiceImpl implements CustomerListDetailQueryByCustomerListNoService {
    private final String CLASS_NAME = CustomerListDetailQueryByCustomerListNoServiceImpl.class.getSimpleName();
    private final CampaignCustomerListDetailDao campaignCustomerListDetailDao;

    public CustomerListDetailQueryByCustomerListNoRsBo queryByCustomerListNo(CustomerListDetailQueryByCustomerListNoRqBo customerListDetailQueryByCustomerListNoRqBo) {
        String customerListNo = customerListDetailQueryByCustomerListNoRqBo.getCustomerListNo();
        int page = customerListDetailQueryByCustomerListNoRqBo.getNumber();
        int size = customerListDetailQueryByCustomerListNoRqBo.getSize();

        Page<CampaignCustomerListDetailDto> queryResult = campaignCustomerListDetailDao.queryCampaignCustomerListDetailByCustomerListNo(customerListNo, page, size, "asc");
        CustomerListDetailQueryByCustomerListNoRsBo customerListDetailQueryByCustomerListNoRsBo = new CustomerListDetailQueryByCustomerListNoRsBo();
        customerListDetailQueryByCustomerListNoRsBo.setTotalElements(queryResult.getTotalElements());
        customerListDetailQueryByCustomerListNoRsBo.setTotalPages(queryResult.getTotalPages());
        customerListDetailQueryByCustomerListNoRsBo.setNumber(queryResult.getNumber());
        customerListDetailQueryByCustomerListNoRsBo.setSize(queryResult.getSize());
        customerListDetailQueryByCustomerListNoRsBo.setCustomerListDetailInfoList(getCustomerListDetailInfoBoList(queryResult.getContent()));
        return customerListDetailQueryByCustomerListNoRsBo;
    }

    private List<CustomerListDetailQueryByCustomerListNoRsBo.CustomerListDetailInfoBo> getCustomerListDetailInfoBoList(List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList) {
        List<CustomerListDetailQueryByCustomerListNoRsBo.CustomerListDetailInfoBo> customerListDetailInfoBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCustomerListDetailDtoList)) {
            return customerListDetailInfoBoList;
        }
        customerListDetailInfoBoList = campaignCustomerListDetailDtoList.stream().map(x -> {
            CustomerListDetailQueryByCustomerListNoRsBo.CustomerListDetailInfoBo customerListDetailInfoBo = new CustomerListDetailQueryByCustomerListNoRsBo.CustomerListDetailInfoBo();
            customerListDetailInfoBo.setCustomerListDetailId(x.getId());
            customerListDetailInfoBo.setCustomerListNo(x.getCustomerListNo());
            customerListDetailInfoBo.setStatus(x.getStatus());
            customerListDetailInfoBo.setIdNo(x.getIdNo());
            customerListDetailInfoBo.setName(x.getName());
            customerListDetailInfoBo.setIpNoList(x.getIpNoList());
            customerListDetailInfoBo.setMessage(x.getMessage());
            customerListDetailInfoBo.setIsSingleIpNo(x.getIsSingleIpNo());
            customerListDetailInfoBo.setChosenIpNo(x.getChosenIpNo());
            customerListDetailInfoBo.setCreateDttm(x.getCreateDttm());
            customerListDetailInfoBo.setUpdateDttm(x.getUpdateDttm());
            return customerListDetailInfoBo;
        }).toList();
        return customerListDetailInfoBoList;
    }
}
