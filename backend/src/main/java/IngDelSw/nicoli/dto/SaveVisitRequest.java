package IngDelSw.nicoli.dto;

import IngDelSw.nicoli.model.Product;
import IngDelSw.nicoli.model.Visit;

import java.util.List;

public class SaveVisitRequest extends Visit {
    private List<Integer> productIds;

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }


}
