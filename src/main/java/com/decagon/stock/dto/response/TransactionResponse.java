package com.decagon.stock.dto.response;

import com.decagon.stock.repository.data.TransactionSearchData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Victor.Komolafe
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse extends ApiResponse {
    List<TransactionSearchData> data;
}
