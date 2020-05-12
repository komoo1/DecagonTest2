package com.decagon.stock.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
public class TransactionDTO implements Serializable {
    private String startDate;
    private String endDate;
    private Integer pageSize;
    private Integer pageOffset;
    private Integer pageNumber;
}
