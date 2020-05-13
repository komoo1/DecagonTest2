package com.decagon.stock.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
public class TransactionDTO implements Serializable {
    @NotBlank
    @NotNull
    private String startDate;
    @NotBlank
    @NotNull
    private String endDate;
    private int pageSize = 10;
    private int pageNumber = 0;
}
