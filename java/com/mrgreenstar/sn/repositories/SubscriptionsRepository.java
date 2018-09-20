package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.Subscriptions;
import com.mrgreenstar.sn.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SubscriptionsRepository extends CrudRepository<Subscriptions, Long> { }
