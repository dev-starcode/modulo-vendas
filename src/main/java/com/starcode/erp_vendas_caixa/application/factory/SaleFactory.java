package com.starcode.erp_vendas_caixa.application.factory;

import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.ProductSaleDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SaleFactory {

    public static List<ProductSale> createProductSales(final Sale sale, final List<ProductSaleDTO> products) {
        return products.stream().map(product -> ProductSale.create(
             sale.getSaleId().getValue(),
             product.productId(),
             product.productName(),
             product.priceSale(),
             product.priceSale(),
             product.quantity())).collect(Collectors.toList());
    }
}