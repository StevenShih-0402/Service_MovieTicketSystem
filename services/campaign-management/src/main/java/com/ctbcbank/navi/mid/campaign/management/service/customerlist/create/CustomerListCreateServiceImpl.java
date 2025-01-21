package com.ctbcbank.navi.mid.campaign.management.service.customerlist.create;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDetailDao;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.CampaignCustomerListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.csvHeader.CustomerListDetailCsvHeader;
import com.ctbcbank.navi.mid.campaign.management.utils.CsvUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerListCreateServiceImpl implements CustomerListCreateService {
    private final String CLASS_NAME = CustomerListCreateServiceImpl.class.getSimpleName();
    private final CampaignCustomerListDao campaignCustomerListDao;
    private final CampaignCustomerListDetailDao campaignCustomerListDetailDao;
    private final CustomerListProcessDataService customerListProcessDataService;

    @Override
    public CustomerListCreateRsBo create(CustomerListCreateRqBo customerListCreateRqBo) {
        CampaignCustomerListDto campaignCustomerListDto = new CampaignCustomerListDto();
        campaignCustomerListDto.setCustomerListNo(UUIDUtils.getUUID());
        campaignCustomerListDto.setStatus(CampaignCustomerListStatusEnum.NOT_QUERIED);
        campaignCustomerListDto.setCreateEmployeeNo(customerListCreateRqBo.getCreateEmployeeNo());
        campaignCustomerListDto.setName(customerListCreateRqBo.getName());
        campaignCustomerListDto.setTotalCount(BigInteger.ZERO);
        CampaignCustomerListDto saveCampaignCustomerListDto = campaignCustomerListDao.saveCampaignCustomerList(campaignCustomerListDto);
        String customerListNo = saveCampaignCustomerListDto.getCustomerListNo();
        List<MultipartFile> fileList = customerListCreateRqBo.getFileList();
        CustomerListCreateRsBo customerListCreateRsBo = new CustomerListCreateRsBo();
        customerListCreateRsBo.setCustomerListNo(customerListNo);

        processFile(customerListNo, fileList);
        log.info("[{}][create][create success.]", CLASS_NAME);
        // 異步打CIM取IP NO
        customerListProcessDataService.processData(customerListNo);
        return customerListCreateRsBo;
    }

    /**
     * 處理檔案
     *
     * @param customerListNo
     * @param fileList
     */
    public void processFile(String customerListNo, List<MultipartFile> fileList) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(CsvUtils.getCsvHeaderNames(CustomerListDetailCsvHeader.class, false))
                .setSkipHeaderRecord(true)
                .build();
        List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            try {

                Iterable<CSVRecord> csvRecords = csvFormat.parse(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                for (CSVRecord csvRecord : csvRecords) {
                    campaignCustomerListDetailDtoList.add(covertToDto(csvRecord, customerListNo));
                }
                // 每100筆存一次
                if (campaignCustomerListDetailDtoList.size() % 100 == 0) {
                    // 新增資料至資料庫
                    log.info("[{}][processFile][insert campaignCustomerListDetailDtoList: {}]", CLASS_NAME, campaignCustomerListDetailDtoList);
                    campaignCustomerListDetailDao.saveCampaignCustomerListDetail(campaignCustomerListDetailDtoList, 100);
                    campaignCustomerListDetailDtoList.clear();
                }
            } catch (Exception ex) {
                log.error("[{}][processFile][parsing fail...{}]", CLASS_NAME, ex);
            }
        }
        // 最後未滿100筆也需要存到資料庫
        if (!CollectionUtils.isEmpty(campaignCustomerListDetailDtoList)) {
            // 新增資料至資料庫
            log.info("[{}][processFile][insert campaignCustomerListDetailDtoList: {}]", CLASS_NAME, campaignCustomerListDetailDtoList);
            campaignCustomerListDetailDao.saveCampaignCustomerListDetail(campaignCustomerListDetailDtoList, 100);
        }


    }

    private CampaignCustomerListDetailDto covertToDto(CSVRecord csvRecord, String customerListNo) {
        CampaignCustomerListDetailDto campaignCustomerListDetailDto = new CampaignCustomerListDetailDto();
        campaignCustomerListDetailDto.setIdNo(csvRecord.get(0));
        campaignCustomerListDetailDto.setName(csvRecord.get(1));
        campaignCustomerListDetailDto.setStatus(CampaignCustomerListStatusEnum.NOT_QUERIED);
        campaignCustomerListDetailDto.setCustomerListNo(customerListNo);
        campaignCustomerListDetailDto.setIpNoList(new ArrayList<>());
        campaignCustomerListDetailDto.setIsSingleIpNo(false);
        campaignCustomerListDetailDto.setChosenIpNo(BigInteger.ZERO);
        return campaignCustomerListDetailDto;
    }
}
