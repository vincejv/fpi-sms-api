/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

package com.abavilla.fpi.config;

import com.dtone.dvs.dto.Amount;
import com.dtone.dvs.dto.ApiRequest;
import com.dtone.dvs.dto.ApiResponse;
import com.dtone.dvs.dto.Balance;
import com.dtone.dvs.dto.BalanceFilter;
import com.dtone.dvs.dto.Benefit;
import com.dtone.dvs.dto.BenefitType;
import com.dtone.dvs.dto.BenefitTypes;
import com.dtone.dvs.dto.CalculationMode;
import com.dtone.dvs.dto.Country;
import com.dtone.dvs.dto.Dates;
import com.dtone.dvs.dto.Error;
import com.dtone.dvs.dto.ErrorResponse;
import com.dtone.dvs.dto.Operator;
import com.dtone.dvs.dto.PageAsync;
import com.dtone.dvs.dto.Party;
import com.dtone.dvs.dto.PartyIdentifier;
import com.dtone.dvs.dto.Pin;
import com.dtone.dvs.dto.Price;
import com.dtone.dvs.dto.Prices;
import com.dtone.dvs.dto.Product;
import com.dtone.dvs.dto.ProductFilter;
import com.dtone.dvs.dto.ProductType;
import com.dtone.dvs.dto.Promotion;
import com.dtone.dvs.dto.PromotionFilter;
import com.dtone.dvs.dto.Rates;
import com.dtone.dvs.dto.Region;
import com.dtone.dvs.dto.Service;
import com.dtone.dvs.dto.Source;
import com.dtone.dvs.dto.StatementDetail;
import com.dtone.dvs.dto.StatementFilter;
import com.dtone.dvs.dto.Status;
import com.dtone.dvs.dto.StatusClass;
import com.dtone.dvs.dto.TransactionFilter;
import com.dtone.dvs.dto.TransactionRequest;
import com.dtone.dvs.dto.TransactionResponse;
import com.dtone.dvs.dto.UnitTypes;
import com.dtone.dvs.dto.Validity;
import com.dtone.dvs.dto.Values;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
    Amount.class,
    ApiRequest.class,
    ApiResponse.class,
    Balance.class,
    BalanceFilter.class,
    Benefit.class,
    BenefitType.class,
    BenefitTypes.class,
    CalculationMode.class,
    Country.class,
    Dates.class,
    Error.class,
    ErrorResponse.class,
    Operator.class,
    PageAsync.class,
    Party.class,
    PartyIdentifier.class,
    Pin.class,
    Price.class,
    Prices.class,
    Product.class,
    ProductFilter.class,
    ProductType.class,
    Promotion.class,
    PromotionFilter.class,
    Rates.class,
    Region.class,
    Service.class,
    Source.class,
    StatementDetail.class,
    StatementFilter.class,
    Status.class,
    StatusClass.class,
    TransactionFilter.class,
    TransactionRequest.class,
    TransactionResponse.class,
    UnitTypes.class,
    Validity.class,
    Values.class,
})
public class ReflectionConfig {
}
