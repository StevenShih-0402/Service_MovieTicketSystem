package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormHistoryEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignFormHistoryRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignFormHistoryDao {
    private final String CLASS_NAME = CampaignFormHistoryDao.class.getSimpleName();
    private final CampaignFormHistoryRepository campaignFormHistoryRepository;

    public CampaignFormHistoryDto saveCampaignFormHistory(CampaignFormHistoryDto campaignFormHistoryDto) {
        CampaignFormHistoryDto reCampaignFormHistoryDto = new CampaignFormHistoryDto();
        CampaignFormHistoryEntity campaignFormHistoryEntity = new CampaignFormHistoryEntity();
        BeanUtils.copyProperties(campaignFormHistoryDto, campaignFormHistoryEntity);
        campaignFormHistoryRepository.save(campaignFormHistoryEntity);
        BeanUtils.copyProperties(campaignFormHistoryEntity, reCampaignFormHistoryDto);
        return reCampaignFormHistoryDto;
    }
}
