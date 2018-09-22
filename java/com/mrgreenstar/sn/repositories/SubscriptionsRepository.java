package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.Subscriptions;
import com.mrgreenstar.sn.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SubscriptionsRepository extends CrudRepository<Subscriptions, Long> {
    Subscriptions findSubscriptionsByUserAndSubId(User usr, Long subId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Subscriptions WHERE user_id = ?1 AND sub_id = ?2")
    void deleteSubscriptionsByUserAndSubId(Long usrId, Long subId);
}
