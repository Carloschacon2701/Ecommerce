package com.ecommerce.ecommerce.Purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PucharseService {

        private final PurchaseRepository purchaseRepository;

        public PucharseService(PurchaseRepository purchaseRepository) {
            this.purchaseRepository = purchaseRepository;
        }

        public Purchase savePurchase(Purchase purchase){
            return this.purchaseRepository.save(purchase);
        }
}
