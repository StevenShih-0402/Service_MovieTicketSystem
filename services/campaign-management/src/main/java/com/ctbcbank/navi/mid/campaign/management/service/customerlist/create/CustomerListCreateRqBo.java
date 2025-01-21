package com.ctbcbank.navi.mid.campaign.management.service.customerlist.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListCreateRqBo {
    private String name;
    List<MultipartFile> fileList;
    private String createEmployeeNo;
}
