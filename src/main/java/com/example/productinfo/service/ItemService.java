package com.example.productinfo.service;

import com.example.productinfo.entity.Item;
import com.example.productinfo.entity.User;
import com.example.productinfo.repo.ItemRepo;
import com.example.productinfo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final UserRepo userRepo;

    public List<Item> findByUserId(Long userId) {
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
