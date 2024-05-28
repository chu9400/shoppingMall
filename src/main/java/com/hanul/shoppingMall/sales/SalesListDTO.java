package com.hanul.shoppingMall.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
public class SalesListDTO {

    private List<SalesDTO> salesDTOList;

    public SalesListDTO(List<SalesDTO> salesDTOList) {
        this.salesDTOList = salesDTOList;
    }
}
