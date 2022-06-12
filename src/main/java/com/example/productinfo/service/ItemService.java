package com.example.productinfo.service;

import com.example.productinfo.entity.Item;
import com.example.productinfo.entity.Promotion;
import com.example.productinfo.entity.PromotionItem;
import com.example.productinfo.entity.User;
import com.example.productinfo.repo.ItemRepo;
import com.example.productinfo.repo.PromotionItemRepo;
import com.example.productinfo.repo.PromotionRepo;
import com.example.productinfo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;
    private final PromotionRepo promotionRepo;
    private final PromotionItemRepo promotionItemRepo;

    public Map<String, Object> findItemWithPromotionByItemId(Long itemId) {
        Optional<Item> item = itemRepo.findById(itemId);
        List<Promotion> promotionList = promotionRepo.findAll();
        List<PromotionItem> promotionItemList = promotionItemRepo.findByItemId(itemId);
        Map<String, Object> returnMap = new HashMap<>();

        if(item.isPresent()) {
            int itemPrice = item.get().getItemPrice();
            double discountPrice = Double.MAX_VALUE;
            int promotionIdx = -1;

            for(int i = 0; i < promotionList.size(); i++) {
                boolean isInRelation = false;
                for(PromotionItem pi : promotionItemList) {
                    if(pi.getPromotionId() == promotionList.get(i).getPromotionId()) {
                        isInRelation = true;
                        break;
                    }
                }
                if(!isInRelation) continue;

                Date promotionStartDate = promotionList.get(i).getPromotionStartDate();
                Date promotionEndStartDate = promotionList.get(i).getPromotionEndDate();
                Date curDate = Date.valueOf(LocalDate.now());
                if(curDate.before(promotionStartDate) || curDate.after(promotionEndStartDate)) continue;

                int discountAmount = promotionList.get(i).getDiscountAmount();
                double discountRate = promotionList.get(i).getDiscountRate();
                if(discountAmount > 0 && (itemPrice - discountAmount) > 0 && discountPrice > itemPrice - discountAmount) {
                    discountPrice = itemPrice - discountAmount;
                    promotionIdx = i;
                }
                if(discountRate > 0 && (itemPrice * (1 - discountRate)) > 0 && discountPrice > itemPrice * (1 - discountRate)) {
                    discountPrice = itemPrice * (1 - discountRate);
                    promotionIdx = i;
                }
            }

            if(promotionIdx != -1) {
                item.get().setDiscountPrice(discountPrice);
                returnMap.put("promotion", promotionList.get(promotionIdx));
            }
            returnMap.put("item", item.get());
        }

        return returnMap;
    }

    public List<Item> findItemByUserId(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        List<Item> itemList = itemRepo.findAll();
        List<Item> resultItemList = new ArrayList<>();

        if(user.isPresent() && user.get().getUserStat().equals("정상")) {
            String userType = user.get().getUserType();

            for(Item item : itemList) {
                String itemType = item.getItemType();
                Date itemDisplayStartDate = item.getItemDisplayStartDate();
                Date itemDisplayEndStartDate = item.getItemDisplayEndDate();
                Date curDate = Date.valueOf(LocalDate.now());

                if(itemType.equals("기업회원상품") && userType.equals("일반")) continue;
                if(curDate.before(itemDisplayStartDate) || curDate.after(itemDisplayEndStartDate)) continue;

                resultItemList.add(item);
            }
        }

        return resultItemList;
   }
}
