package com.edgarsannic.dealergestor.controller.ViewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyConfigurationPostViewModel {

    private String companyName;
    private String primaryColor;
    private String secondaryColor;
    private String whatsappApiKey;
}